
import Domain.Persoana;
import Repo.IPersoanaRepository;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperService implements IService {


    private IPersoanaRepository persoanaRepository;
    private Map<Integer, IObserver> users;



    public SuperService(IPersoanaRepository persoanaRepository
    ) {
        this.persoanaRepository=persoanaRepository;
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
    public synchronized Persoana login(String e, String p, IObserver client) throws ServiceException {
        System.out.println("sunt in metoda login(super service)!!!!");
        Persoana acesta = persoanaRepository.getPersDupaDate(e,p);
        if(acesta == null){
            return  null;
        }
        if(!check(acesta)){
            users.put(acesta.getId(), client);
            System.out.println("super service: found person: "+acesta);
        }

        return acesta;

    }

    public boolean check(Persoana p){
        for (Map.Entry<Integer,IObserver> entry : users.entrySet())
            if(entry.getKey()==p.getId()) {
                return true;
            }

        return false;
    }



    @Override
    public synchronized void logout(Persoana user, IObserver client) throws ServiceException {
        Persoana gasit= persoanaRepository.getPersDupaDate(user.getParola(), user.getEmail());
        if(gasit == null) return;
        users.forEach((x,y) -> System.out.println("Client "+x+ " si obs: "+y));
        System.out.println("in users(am gasit la logout): "+users.get(gasit.getId()));

        users.remove(gasit.getId());
        users.forEach((x,y) -> System.out.println("AFTER: Client "+x+ " si obs: "+y));




    }




    private void notifyUpdate(Persoana dn) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        System.out.println("am gasit persoana:   "+dn);
        for(Map.Entry<Integer,IObserver> entry: users.entrySet()) {

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





}
