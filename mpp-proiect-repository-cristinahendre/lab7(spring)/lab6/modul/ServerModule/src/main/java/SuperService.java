import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Validators.ValidationException;
import Domain.Validators.Validator;
import Domain.Voluntar;
import Repository.CazRepoI;
import Repository.DonatieRepoI;
import Repository.DonatorRepoI;
import Repository.VoluntarRepoI;
import Service.IObserver;
import Service.IService;
import Service.ServiceException;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperService implements IService {
//modul de server
    private CazRepoI cazRepo;
    private DonatorRepoI donatorRepo;
    private DonatieRepoI donatieRepo;
    private VoluntarRepoI voluntarRepo;


    Validator<Donator> donatorValidator;
    Validator<Donatie> donatieValidator;
    private Map<Integer, IObserver> donatii;
    private Map<Integer, IObserver> users;



    public SuperService(CazRepoI cazRepo, DonatorRepoI donatorRepo, DonatieRepoI donatieRepo, VoluntarRepoI voluntarRepo
                       ) {
        this.cazRepo = cazRepo;
        this.donatorRepo = donatorRepo;
        this.donatieRepo = donatieRepo;
        this.voluntarRepo = voluntarRepo;
        donatii=new ConcurrentHashMap<>();
        users=new ConcurrentHashMap<>();




    }

    /**
     * Cauta cazul cu id-ul dat
     * @param id -> id de cautat
     * @return cazul gasit (ori null)
     */
    public CazCaritabil findOneCaz(int id){
        return cazRepo.findOne(id);
    }


    /**
     *
     * @return toate cazurile caritabile
     */
    public synchronized Iterable<CazCaritabil> getCazuri(){
        return cazRepo.findAll();
    }


    /**
     * Salveaza o noua donatie
     * @param caz ->cazul referit
     * @param donator ->cel ce doneaza
     * @param suma ->suma donata de donator
     * @return donatia salvata
     */
    public synchronized Donatie saveDonatie(CazCaritabil caz, Donator donator, int suma){
        Donatie don = new Donatie(donator,caz,suma);
       // donatieValidator.validate(don);
        donatieRepo.save(don);

        return don;
    }


    /**
     * Salveaza un nou donator
     * @param nume ->numele donatorului
     * @param prenume ->prenumele donatorului
     * @param addr ->adresa donatorului
     * @param telefon ->nr de telefon al donatorului
     * @return donatorul salvat
     */
    public synchronized void saveDonator(String nume, String prenume, String addr, long telefon){
        Donator dn = new Donator(nume,prenume,addr,telefon);
        System.out.println("se vrea salva un donator(super service)");
      //  donatorValidator.validate(dn);
        donatorRepo.save(dn);
        System.out.println("succes. am salvat(super service)");
    }


    /**
     * Cauta un donator dupa nume
     * @param nume ->numele dat
     * @return lista cu donatorii gasiti
     */
    public  synchronized  Iterable<Donator> getDonatorDupaNume(String nume){
        Iterable<Donator> rez= donatorRepo.getDonatorDupaNume(nume);
        return  rez;
    }


    @Override
    public synchronized Voluntar login(String e, String p, IObserver client) throws ServiceException {
        System.out.println("sunt in metoda login(super service)!!!!");
        Voluntar acesta = voluntarRepo.getVoluntarDupaDate(e,p);
        users.put(acesta.getId(), client);
        System.out.println("super service: found voluntar: "+acesta);
        return acesta;


    }



    @Override
    public synchronized void logout(Voluntar user, IObserver client) throws ServiceException {
        Voluntar gasit= voluntarRepo.getVoluntarDupaDate(user.getEmail(),user.getParola());
        users.forEach((x,y) -> System.out.println("Client "+x+ " si obs: "+y));
        System.out.println("in users(am gasit la logout): "+users.get(gasit.getId()));

        users.remove(gasit.getId());
        users.forEach((x,y) -> System.out.println("AFTER: Client "+x+ " si obs: "+y));




    }

    /**
     * Cauta voluntarul ce are un email si o parola date
     * @param email ->adresa de email
     * @param parola ->parola corespunzatoare
     * @return voluntarul cu aceste date
     */
    public synchronized Voluntar getVoluntarDupaDate(String email,String parola){
        System.out.println("am ajuns in super service (cauta voluntar)");
        Voluntar v= voluntarRepo.getVoluntarDupaDate(email,parola);

        return v;
    }

    @Override
    public synchronized  void saveDonatie(Donatie don, IObserver client) {
        System.out.println("SAVE DONATIE: PRINTEZ USERII:");
        users.forEach((x,y)-> System.out.println(x+" "+y));
      //  donatieValidator.validate(don);
        donatieRepo.save(don);
        Donatie aceeasi =donatieRepo.getDonatieDupaDate(don);
        donatii.put(aceeasi.getId(), client);
      //  IObserver observer= donatii.get(aceeasi.getId());
//        try {
//            observer.refreshCazuri(aceeasi);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        //System.out.println("am gasit donatia : "+aceeasi);
        //donatii.put(aceeasi.getId(), client);
        try {
            notifyUpdate(aceeasi);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }


    /**
     * Se cauta daca exista un donator cu aceste date in baza de date
     * @param nume ->nume dat
     * @param prenume ->prenume dat
     * @param adrr ->adresa data
     * @param telefon ->nr_telefon
     * @return donatorul cu aceste date(ori null)
     */
    public synchronized Donator getDonatorDupaDate(String nume ,String prenume,
                                      String adrr, long telefon){
        return donatorRepo.getDonatorDupaDate(nume,prenume,adrr,telefon);
    }


    /**
     *
     * @return toti donatorii din baza de date
     */
    public Iterable<Donator> getDonatori(){
        return  donatorRepo.findAll();
    }


    private void notifyUpdate(Donatie dn) throws ServiceException {


        ExecutorService executor= Executors.newFixedThreadPool(5);

        System.out.println("am gasit donatia:   "+dn);
        for(Map.Entry<Integer,IObserver> entry: users.entrySet()) {

            IObserver observer = entry.getValue();
            System.out.println("observer isss..."+observer);
            executor.execute(() -> {
                try {
                    System.out.println("apelez refresh!-");
                    observer.refreshCazuri(dn);
                } catch (ServiceException| RemoteException e) {
                    System.err.println("Error notifying  " + e);
                }
            });



        }
        executor.shutdown();
    }





}
