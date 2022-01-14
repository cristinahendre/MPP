package Service;

import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Validators.ValidationException;
import Domain.Validators.Validator;
import Domain.Voluntar;
import Repository.*;

public class SuperService {

    private CazRepoI cazRepo;
    private DonatorRepoI donatorRepo;
    private DonatieRepoI donatieRepo;
    private VoluntarRepoI voluntarRepo;


    Validator<Donator> donatorValidator;
    Validator<Donatie> donatieValidator;


    public SuperService(CazRepoI cazRepo, DonatorRepoI donatorRepo, DonatieRepoI donatieRepo, VoluntarRepoI voluntarRepo,
                        Validator<Donator> donatorValidator,
                        Validator<Donatie> donatieValidator) {
        this.cazRepo = cazRepo;
        this.donatorRepo = donatorRepo;
        this.donatieRepo = donatieRepo;
        this.voluntarRepo = voluntarRepo;



        this.donatorValidator=donatorValidator;
        this.donatieValidator=donatieValidator;
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
    public Iterable<CazCaritabil> getCazuri(){
        return cazRepo.findAll();
    }


    /**
     * Salveaza o noua donatie
     * @param caz ->cazul referit
     * @param donator ->cel ce doneaza
     * @param suma ->suma donata de donator
     * @return donatia salvata
     */
    public Donatie saveDonatie(CazCaritabil caz, Donator donator, int suma){
        Donatie don = new Donatie(donator,caz,suma);
        donatieValidator.validate(don);
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
    public Donator saveDonator(String nume, String prenume, String addr, long telefon){
        Donator dn = new Donator(nume,prenume,addr,telefon);
        donatorValidator.validate(dn);
        donatorRepo.save(dn);
        return dn;
    }


    /**
     * Cauta un donator dupa nume
     * @param nume ->numele dat
     * @return lista cu donatorii gasiti
     */
    public Iterable<Donator> getDonatorDupaNume(String nume){
        Iterable<Donator> rez= donatorRepo.getDonatorDupaNume(nume);
        return  rez;
    }


    /**
     * Cauta voluntarul ce are un email si o parola date
     * @param email ->adresa de email
     * @param parola ->parola corespunzatoare
     * @return voluntarul cu aceste date
     */
    public Voluntar getVoluntarDupaDate(String email,String parola){
        Voluntar v= voluntarRepo.getVoluntarDupaDate(email,parola);
        return v;
    }


    /**
     * Se cauta daca exista un donator cu aceste date in baza de date
     * @param nume ->nume dat
     * @param prenume ->prenume dat
     * @param adrr ->adresa data
     * @param telefon ->nr_telefon
     * @return donatorul cu aceste date(ori null)
     */
    public Donator getDonatorDupaDate(String nume ,String prenume,
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





}
