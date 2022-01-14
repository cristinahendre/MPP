
import Domain.Joc;
import Domain.Persoana;
import Repo.IJocRepository;
import Repo.IPersoanaRepository;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperService implements IService {


    private IPersoanaRepository persoanaRepository;
    private IJocRepository jocRepo;
    private Map<Persoana, IObserver> users;
    private Persoana start;
    private Persoana next;



    public SuperService(IPersoanaRepository persoanaRepository, IJocRepository jocRepo
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
    public synchronized Iterable<Persoana> getPeople(){
        return persoanaRepository.findAll();
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
        try {
            notifyUpdate(acesta);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return acesta;
    }

    @Override
    public void delete(Persoana p) {
        Persoana actuala =persoanaRepository.getPersDupaDate(p.getParola(),p.getEmail());
        persoanaRepository.delete(actuala.getId());
        users.remove(actuala.getId());

        try {
            notifyUpdate(actuala);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Persoana p) {

        persoanaRepository.update(p);
        IObserver obs=users.get(p);
        users.remove(p);
        users.put(p,obs);

        try {
            notifyUpdate(p);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Persoana getPersoanaDupaDate(String n, String e){
        return persoanaRepository.getPersDupaDate(n,e);
    }

    @Override
    public Persoana alegeUnParticipant(Persoana p) {

        System.out.println("\n\nService: aleg intre: ");
        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {

            Persoana curenta =entry.getKey();
            System.out.println(curenta);
            if(curenta.getId()!=p.getId() && curenta.isParticipa()){
                return curenta;
            }

        }
        return null;
    }

    @Override
    public void incepeJoc(Joc c) {

        System.out.println("Service. Incepe jocul....");
        jocRepo.save(c);



    }

    @Override
    public List<Persoana> getPersoaneParticipante() {
        return persoanaRepository.getParticipanti();
    }

    @Override
    public boolean participaLaJocPersoana(Persoana p) {
        return jocRepo.participaLaJocPersoana(p.getId());
    }

    @Override
    public void ghicire(Persoana p, int poz) {

        if(start.equals(p)) {
            if (jocRepo.getPozitiePersoana(p) != poz) {

                try {
                    notifyPozitie(p, poz);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    notifyCastig(p);
                    updateJoc(p.getId(), p.getId());
                } catch (ServiceException e) {
                    e.printStackTrace();
                }

            }
            Persoana  a= start;
            start=next;
            next=a;
        }

    }


    @Override
    public synchronized Persoana login(String e, String p, IObserver client) throws ServiceException {
        System.out.println("sunt in metoda login(super service)!!!!");
        Persoana acesta = persoanaRepository.getPersDupaDate(e,p);
        if(acesta == null){
            return  null;
        }
        if(!check(acesta)){
            users.put(acesta, client);
            System.out.println("super service: found person: "+acesta);
        }

        if(users.size() == 2){
            repartizare();
        }
        return acesta;

    }

    public void repartizare(){

        int nr =0;
        for (Map.Entry<Persoana,IObserver> entry : users.entrySet()) {
            if (nr == 0) start = entry.getKey();
            else next= entry.getKey();
            nr++;
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
    public synchronized void logout(Persoana user, IObserver client) throws ServiceException {
        Persoana gasit= persoanaRepository.getPersDupaDate(user.getParola(), user.getEmail());
        if(gasit == null) return;
        users.forEach((x,y) -> System.out.println("Client "+x+ " si obs: "+y));
        System.out.println("in users(am gasit la logout): "+users.get(gasit));

        users.remove(gasit);
        users.forEach((x,y) -> System.out.println("AFTER: Client "+x+ " si obs: "+y));




    }




    private void notifyUpdate(Persoana dn) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        System.out.println("am gasit persoana:   "+dn);
        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();
            System.out.println("observer isss..."+observer);
            executor.execute(() -> {
                try {
                    System.out.println("apelez refresh!-(dupa update)");
                    observer.refresh(dn);
                } catch (ServiceException| RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }

    private void updateJoc(int idP, int idW){
        jocRepo.seteazaWinner(idW,idP);
    }


    private void notifyCastig(Persoana dn) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();
            System.out.println("observer isss..."+observer);
            executor.execute(() -> {
                try {
                    observer.castigator(dn);
                } catch (RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }




    private void notifyPozitie(Persoana dn, int poz) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(Map.Entry<Persoana,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();
            System.out.println("observer isss..."+observer);
            executor.execute(() -> {
                try {
                    observer.pozitieInamic(dn,poz);
                } catch (RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }





}
