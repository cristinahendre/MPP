
import Domain.Cuvant;
import Domain.Joc;
import Domain.Jucator;
import Repo.ICuvantRepository;
import Repo.IJoc2Repository;
import Repo.IJucatorRepository;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperService implements IService {


    private IJucatorRepository persoanaRepository;
    private ICuvantRepository cuvantRepo;
    private IJoc2Repository jocRepo;
    private Map<Jucator, IObserver> users;
    int poz=1;
    Cuvant ales=null;
    int id=1;
    int intrat=0;




    public SuperService(IJucatorRepository persoanaRepository,
                        ICuvantRepository cuvantRepo, IJoc2Repository jocRepo
    ) {
        this.persoanaRepository=persoanaRepository;
        this.cuvantRepo=cuvantRepo;
        this.jocRepo=jocRepo;
        users=new ConcurrentHashMap<>();
        ales= cuvantRepo.findOne(poz);





    }

    /**
     * Cauta persoana cu id-ul dat
     * @param id -> id de cautat
     * @return cazul gasit (ori null)
     */
    public Jucator getOne(int id){
        return persoanaRepository.findOne(id);
    }


    /**
     *
     * @return toate persoanele
     */
    public synchronized List<Jucator> getPeople()
    {
        List<Jucator> rez=new ArrayList<>();
        for(Map.Entry<Jucator,IObserver> entry: users.entrySet()) {
            rez.add(entry.getKey());
        }
        return rez;
    }


    /**
     * Salveaza o noua persoana

     * @return persoana salvata
     */
    public synchronized Jucator save(String nume, String email, IObserver obs){
        Jucator pers=new Jucator(nume, email);
        persoanaRepository.save(pers);
        Jucator acesta= getPersoanaDupaDate(nume,email);
        //users.put(acesta.getId(), obs);
        try {
            notifyUpdate(acesta);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return acesta;
    }

    @Override
    public void delete(Jucator p) {
        Jucator actuala =persoanaRepository.getPersDupaDate(p.getParola(),p.getEmail());
        persoanaRepository.delete(actuala.getId());
        users.remove(actuala.getId());

        try {
            notifyUpdate(actuala);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Jucator p) {

        persoanaRepository.update(p);

        try {
            notifyUpdate(p);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Jucator getPersoanaDupaDate(String n, String e){
        return persoanaRepository.getPersDupaDate(n,e);
    }

    @Override
    public void startJoc(Jucator j) {
        System.out.println("\n[super service]s-a ales cuvantul "+ales);
        try {
            notifyCuvant(ales.getLitere(),j);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void procesezCaracteristici(Jucator j, String car1, String car2,String cuv) {

        intrat++;
        int runda=0;
        Cuvant initial=cuvantRepo.getCuvantDupaLitere(cuv);
        System.out.println("cuvantul initial = "+ales);
        String[] values=ales.getCaracteristici().split(" ");
        String cor1=values[0];
        String cor2=values[1];
        System.out.println("[super service] caracteristici = ");
        System.out.println("\nc1= "+cor1+" c2= "+cor2);


        Joc joc =new Joc(j.getId(),ales.getLitere(),car1+" "+car2);
        joc.setId(id);
        if(intrat%3==0){
            runda=intrat/3;
        }
        else runda=intrat/3+1;
        joc.setRunda(runda);
        jocRepo.save(joc);

        List<Joc> all=jocRepo.getJocuriDinRunda(id,runda);
        int numar1=0;int numar2=0;
        int puncte=0;
        if(all.size()==3){
            System.out.println("super service.am primit 3");
            for(Joc jocul:all){
                System.out.println(jocul);
                String[] va=jocul.getCaracteristici().split(" ");
                String c1=va[0];
                String c2=va[1];
                if(c1.equals(cor1) || c2.equals(cor1)){
                    numar1++;
                }
                if(c2.equals(cor2) || c1.equals(cor2)){
                    numar2++;
                }

            }
            if(numar1==3){
                System.out.println("numar1==3");
                puncte=1;
                for(Joc jocul:all){
                    jocul.setPuncte(joc.getPuncte()+puncte);
                    System.out.println("joc de updatat "+jocul  );
                    jocRepo.update(jocul);
                    System.out.println("update ended.");
                }
            }
            if(numar2==3){
                System.out.println("numar2==3");

                puncte=1;
                for(Joc jocul:all){
                    jocul.setPuncte(joc.getPuncte()+puncte);
                    jocRepo.update(jocul);
                }
            }
            if(numar1==2){
                puncte=3;

                for(Joc jocul:all){
                    String[] va=jocul.getCaracteristici().split(" ");
                    String c1=va[0];
                    String c2=va[1];
                    if(c1.equals(cor1) || c2.equals(cor1)){
                        jocul.setPuncte(joc.getPuncte()+puncte);
                        jocRepo.update(jocul);
                    }

                }
            }
            if(numar2==2){
                puncte=3;

                for(Joc jocul:all){
                    String[] va=jocul.getCaracteristici().split(" ");
                    String c1=va[0];
                    String c2=va[1];
                    if(c1.equals(cor2) || c2.equals(cor2)){
                        jocul.setPuncte(joc.getPuncte()+puncte);
                        jocRepo.update(jocul);
                    }

                }
            }
            if(numar1==1){
                puncte=5;
                System.out.println("un raspuns corect");
                for(Joc jocul:all){
                    String[] va=jocul.getCaracteristici().split(" ");
                    String c1=va[0];
                    String c2=va[1];
                    if(c1.equals(cor1) || c2.equals(cor1)){
                        jocul.setPuncte(joc.getPuncte()+puncte);
                        jocRepo.update(jocul);
                    }

                }
            }

            if(numar2==1){
                puncte=5;
                System.out.println("un raspuns corect");
                for(Joc jocul:all){
                    String[] va=jocul.getCaracteristici().split(" ");
                    String c1=va[0];
                    String c2=va[1];
                    if(c1.equals(cor2) || c2.equals(cor2)){
                        jocul.setPuncte(joc.getPuncte()+puncte);
                        jocRepo.update(jocul);
                    }

                }
            }
            if(numar1==0 && numar2==0){
                puncte=0;
                for(Joc jocul:all){


                    jocul.setPuncte(joc.getPuncte()+puncte);
                    jocRepo.update(jocul);


                }
            }

            try {
                notifyPuncte();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            if(runda==3){
                //sfarsit
                try {
                    notifyEnd();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }



    }


    @Override
    public synchronized void login(Jucator acesta, IObserver client) throws ServiceException {

        if(!check(acesta)){
            users.put(acesta, client);
            System.out.println("super service: found person: "+acesta);
        }
        if(users.size()==3){
            notifyStart(acesta);
        }


    }

    public boolean check(Jucator p){
        for (Map.Entry<Jucator,IObserver> entry : users.entrySet())
            if(entry.getKey().getId()==p.getId()) {
                return true;
            }

        return false;
    }


    @Override
    public Jucator getDateJucator(String e, String p) {
        System.out.println("sunt in metoda login(super service)!!!!");
        Jucator acesta = persoanaRepository.getPersDupaDate(e,p);
        if(acesta == null){
            return  null;
        }
        return acesta;
    }

    @Override
    public synchronized void logout(Jucator user, IObserver client) throws ServiceException {
        Jucator gasit= persoanaRepository.getPersDupaDate(user.getParola(), user.getEmail());
        if(gasit == null) return;
        users.forEach((x,y) -> System.out.println("Client "+x+ " si obs: "+y));
        System.out.println("in users(am gasit la logout): "+users.get(gasit.getId()));

        users.remove(gasit);
        users.forEach((x,y) -> System.out.println("AFTER: Client "+x+ " si obs: "+y));




    }

    private void notifyStart(Jucator dn) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(Map.Entry<Jucator,IObserver> entry: users.entrySet()) {


                IObserver observer = entry.getValue();
                System.out.println("observer isss..." + observer);
                executor.execute(() -> {
                    try {
                        System.out.println("apelez refresh de start!-");
                        observer.putemIncepe();
                    } catch (RemoteException e) {
                        System.err.println("Error notifying  " + e);
                    }
                });




        }
        executor.shutdown();
    }





    private void notifyUpdate(Jucator dn) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        System.out.println("am gasit persoana:   "+dn);
        for(Map.Entry<Jucator,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();
            System.out.println("observer isss..."+observer);
            executor.execute(() -> {
                try {
                    System.out.println("apelez refresh!-");
                    observer.refresh(dn);
                } catch (ServiceException| RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }

    private void notifyPuncte() throws ServiceException {


        int runda;
        if(intrat%3==0){
            runda=intrat/3;
        }
        else runda=intrat/3+1;
        List<Joc> all=jocRepo.getJocuriDinRunda(id,runda);
        String mesaj="";
        for(Joc joc:all){
            mesaj+="Jucatorul "+joc.getJucator()+" a trimis caracteristici "+
               joc.getCaracteristici()+" si a obtinut "+joc.getPuncte()+
             " puncte.\n";
        }
        ExecutorService executor= Executors.newFixedThreadPool(5);
        poz=runda+1;
        System.out.println("poz = "+poz);
        ales=cuvantRepo.findOne(poz);
        for(Map.Entry<Jucator,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();
            String finalMesaj = mesaj;
            executor.execute(() -> {
                try {
                    System.out.println("apelez refresh de puncte");
                    observer.afiseazaPuncte(finalMesaj,ales.getLitere());
                } catch (RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }

    private String getWinners() {
        Map<Integer, Integer> clasament = new HashMap<>();
        List<Joc> all = jocRepo.getToateJocurile(id);
        for (Joc j : all) {
            if (clasament.containsKey(j.getJucator())) {
                int pcte = clasament.get(j.getJucator());
                clasament.remove(j.getJucator());
                clasament.put(j.getJucator(), pcte + j.getPuncte());
            } else {
                clasament.put(j.getJucator(), j.getPuncte());
            }
        }
        int max1 = 0, max2 = 0;
        int winner=-1, winner2=-1;
        for (Map.Entry<Integer, Integer> entry : clasament.entrySet()) {
            if(entry.getValue()>max1){
                max1=entry.getValue();
                winner=entry.getKey();
            }
            else if(entry.getKey()>max2){
                max2=entry.getValue();
                winner2=entry.getKey();
            }
        }
        String msg="";
        msg+="Loc1 = "+winner+" cu "+max1+" puncte\n";
        msg+="Loc2 = "+winner2+" cu "+max2+" puncte\n";
        for (Map.Entry<Integer, Integer> entry : clasament.entrySet()) {
            if(entry.getKey()!=winner && entry.getKey()!=winner2){
                msg+="Loc3 = "+entry.getKey()+" cu "+entry.getValue()+" puncte";
                break;
            }
        }
        System.out.println("castigatori: "+msg);
        return msg;

    }

    private void notifyEnd() throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);
        String msg="";


        for(Map.Entry<Jucator,IObserver> entry: users.entrySet()) {

                IObserver observer = entry.getValue();
                executor.execute(() -> {
                    try {
                        System.out.println("apelez refresh end");
                        observer.sfarsitJoc(getWinners());
                    } catch (RemoteException e) {
                        System.err.println("Error notifying  " + e);
                    }
                });
            }


        executor.shutdown();
    }



    private void notifyCuvant(String c,Jucator j) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);


        for(Map.Entry<Jucator,IObserver> entry: users.entrySet()) {

            if(entry.getKey().equals(j)) {
                IObserver observer = entry.getValue();
                executor.execute(() -> {
                    try {
                        System.out.println("apelez refresh cuvant");
                        observer.trimitCuvant(c);
                    } catch (RemoteException e) {
                        System.err.println("Error notifying  " + e);
                    }
                });
            }

        }
        executor.shutdown();
    }





}
