
import Domain.Joc;
import Domain.Persoana;
import Repo.IJocRepository;
import Repo.IPersoanaRepository;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperService implements IService {


    private IPersoanaRepository persoanaRepository;
    private IJocRepository jocRepo;
    private Map<Persoana, IObserver> users;
    int id=1;



    public SuperService(IPersoanaRepository persoanaRepository,IJocRepository jocRepo
    ) {
        this.persoanaRepository=persoanaRepository;
        this.jocRepo=jocRepo;
        users=new ConcurrentHashMap<>();




    }

    /**
     * Cauta persoana cu id-ul dat
     * @param id -> id de cautat
     * @return cazul gasit (ori null)
     */
    public Persoana getOne(int id){
        return persoanaRepository.findOne(id);
    }


    /**
     *
     * @return toate persoanele
     */
    public synchronized List<Persoana> getPeople(){
        List<Persoana> lisa=new ArrayList<>();
        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {
                lisa.add(entry.getKey());
        }
        return lisa;

    }


    /**
     * Salveaza o noua persoana

     * @return persoana salvata
     */
    public synchronized Persoana save(String nume, String email,IObserver obs){
        Persoana pers=new Persoana(nume, email);
        persoanaRepository.save(pers);
        Persoana acesta= getPersoanaDupaDate(nume,email);
        //users.put(acesta.getId(), obs);
//        try {
//            notifyUpdate(acesta);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }

        return acesta;
    }

    @Override
    public void delete(Persoana p) {
        Persoana actuala =persoanaRepository.getPersDupaDate(p.getParola(),p.getEmail());
        persoanaRepository.delete(actuala.getId());
        users.remove(actuala);

//        try {
//            notifyUpdate(actuala);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void update(Persoana p) {

        persoanaRepository.update(p);

//        try {
//            notifyUpdate(p);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Persoana getPersoanaDupaDate(String n, String e){
        return persoanaRepository.getPersDupaDate(n,e);
    }

    @Override
    public void inainteaza(int n, Persoana p) {
        Joc j= jocRepo.getJocDupaDate(id,p.getEmail());
        if(j==null) {
            System.out.println("[superservice]joc null");
            return;
        }
        if(j.getTraseu().equals("---------")){
            System.out.println("prima mutare");
            String t="";
            if(n==1){
                t=p.getEmail()+"--------";
                j.setPozitii("0");
            }
            if(n==2){
                t="-"+p.getEmail()+"-------";
                j.setPozitii("1");

            }
            if(n==3){
                t="--"+p.getEmail()+"------";
                j.setPozitii("2");

            }
            j.setTraseu(t);
            j.setNumere(String.valueOf(n));
            jocRepo.update(j);
            String msg="Jucatorul "+p.getId()+" a generat nr: "+n;
            try {
                notifyApare(msg,j.getTraseu());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return;

        }
        int poz=getLatestPosition(j.getTraseu(),p.getEmail().charAt(0));
        if(poz!=-1){
            poz+=n;
            String aux=replaceChar(j.getTraseu(),p.getEmail().charAt(0),poz);

        }


    }

    private String replaceChar(String s, char chr,int poz){
        int i;
        String nou="";
        for(i=0;i<s.length();i++){
            if(s.charAt(i)==chr){
                nou+="-";
            }
            else if(i==poz){
                if(s.charAt(i)=='X') {
                    poz--;
                    i--;
                }
                else if(s.charAt(i)!='-'){
                    nou+=chr;
                    char fost=s.charAt(i);
                    int p=poz-1;
                    for(int j=poz;j>=0;j--){
                    }

                }
                else nou+=chr;
            }
            else nou+=s.charAt(i);
        }
        System.out.println("s-a creat string nou: "+nou);
        return nou;
    }

    private int getLatestPosition(String text,char c){
        for(int i =0;i<text.length();i++){
            if(text.charAt(i)==c){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void startGame(Persoana p) {

        if(users.size()==2){
            try {
                notifyUpdate(p);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public synchronized void login(Persoana acesta, IObserver client)  {

        if(users.size()==2) return;
        if(!check(acesta)){
            users.put(acesta, client);
            System.out.println("super service: found person: "+acesta);
        }

        if(users.size()==2){
            try {
              notifyIncepe();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }


    }

    public boolean check(Persoana p){
        for (Map.Entry<Persoana,IObserver> entry : users.entrySet())
            if(entry.getKey().getId()==p.getId()) {
                return true;
            }

        return false;
    }


    @Override
    public Persoana getJucator(String e, String p) throws ServiceException {
        System.out.println("sunt in metoda login(super service)!!!!");
        Persoana acesta = persoanaRepository.getPersDupaDate(e,p);
        if(acesta == null){
            return  null;
        }
        return acesta;
    }

    @Override
    public synchronized void logout(Persoana user, IObserver client) throws ServiceException {
        Persoana gasit= persoanaRepository.getPersDupaDate(user.getParola(), user.getEmail());
        if(gasit == null) return;
        users.forEach((x,y) -> System.out.println("Client "+x+ " si obs: "+y));
        System.out.println("in users(am gasit la logout): "+users.get(gasit));

        users.remove(gasit);
        users.forEach((x,y) -> System.out.println("AFTER: Client "+x+ " si obs: "+y));
        notifyIncepe();



    }

    private void notifyApare(String d, String r) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);


        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();

            executor.execute(() -> {
                try {
                    System.out.println("apelez refresh de inceput");
                    observer.notificaDate(d,r);
                } catch (RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }



    private void notifyIncepe() throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);


        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();

            executor.execute(() -> {
                try {
                    System.out.println("apelez refresh de inceput");
                    observer.incepe();
                } catch (RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }




    private void notifyUpdate(Persoana p) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);


        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {

            if(entry.getKey().equals(p)) {
                IObserver observer = entry.getValue();
                Joc joc = new Joc(entry.getKey().getEmail(), "---------", "", "");
                joc.setId(id);
                System.out.println("id= "+id);
                jocRepo.save(joc);
                executor.execute(() -> {
                    try {
                        System.out.println("apelez refresh!-");
                        observer.refresh(joc.getTraseu());
                    } catch (ServiceException | RemoteException e) {
                        System.err.println("Error notifying  " + e);
                    }
                });

            }

        }
        executor.shutdown();
    }





}
