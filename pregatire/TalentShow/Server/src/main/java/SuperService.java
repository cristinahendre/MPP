
import Domain.Nota;
import Domain.Participant;
import Domain.Persoana;
import Repo.INoteRepository;
import Repo.IParticipantRepository;
import Repo.IPersoanaRepository;
import org.springframework.beans.factory.BeanIsNotAFactoryException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperService implements IService {


    private IPersoanaRepository persoanaRepository;
    private IParticipantRepository partRepo;
    private INoteRepository notaRepo;
    private Map<Integer, IObserver> users;



    public SuperService(IPersoanaRepository persoanaRepository,
                        IParticipantRepository partRepo, INoteRepository notaRepo
    ) {
        this.persoanaRepository=persoanaRepository;
        this.partRepo=partRepo;
        this.notaRepo=notaRepo;
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
            notifyUpdate();
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
            notifyUpdate();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Persoana p) {

        persoanaRepository.update(p);

        try {
            notifyUpdate();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Persoana getPersoanaDupaDate(String n, String e){
        return persoanaRepository.getPersDupaDate(n,e);
    }

    @Override
    public List<Participant> getParticipanti() {
        return (List<Participant>) partRepo.findAll();
    }


    @Override
    public void puncteaza(Participant p, int nota, Persoana juriu) throws ServiceException {

        Nota n=notaRepo.findOne(p.getId());
        System.out.println("[superservice]\n");
        if(n==null){
            System.out.println("nota vida.add");
            Nota nou =new Nota(p.getId());
            nou.setNota1(nota);
            nou.setJuriu1(juriu.getEmail());

            nou.setNota2(0);
            nou.setNota3(0);

            nou.setJuriu2("");nou.setJuriu3("");
            notaRepo.save(nou);
            p.setStatus("Pending");
            partRepo.update(p);

            try {
                notifyUpdate();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return;
        }
        if(n.getJuriu1().equals(juriu.getEmail()) || n.getJuriu2().equals(juriu.getEmail()) ||
                n.getJuriu3().equals(juriu.getEmail())){
            throw new ServiceException("Ati votat deja.");
        }
        int rez=notaRepo.votare(n,juriu,nota);
        if(rez == 0){
            System.out.println("votare in repo: rez= "+rez);
            Participant iar = partRepo.findOne(p.getId());
            System.out.println("participant= "+iar);
            iar.setStatus("Pending");
            partRepo.update(iar);
            try {
                notifyUpdate();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return;
        }
        if(rez!=0){
            Participant iar = partRepo.findOne(p.getId());
            iar.setStatus(String.valueOf(rez));

            partRepo.update(iar);
            try {
                notifyUpdate();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return;
        }

    }


    @Override
    public synchronized Persoana login(String e, String p, IObserver client) throws ServiceException {
        System.out.println("sunt in metoda login(super service)!!!!");
        Persoana acesta = persoanaRepository.getPersDupaDate(e,p);
        if(acesta == null){
            return  null;
        }
        if(users.size()==3){
            throw new ServiceException("Nu sunteti din juriu.");
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




    private void notifyUpdate() throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(Map.Entry<Integer,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();
            System.out.println("observer isss..."+observer);
            executor.execute(() -> {
                try {
                    System.out.println("apelez refresh!-");
                    observer.refresh();
                } catch (ServiceException| RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }





}
