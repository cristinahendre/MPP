package Repository;

import Domain.Donator;
import Domain.Entity;

public interface DonatorRepoI
        extends RepositoryH<Integer, Donator> {

    /**
     * Cauta donatorii, date fiind cateva litere din numele lor
     * @param nume -> un nume de donator(poate incomplet)
     * @return toti donatorii ce incep cu literele din nume
     */
    Iterable<Donator> getDonatorDupaNume(String nume);


    /**
     * Se cauta daca exista un donator cu aceste date in baza de date
     * @param nume ->nume dat
     * @param prenume ->prenume dat
     * @param adresa ->adresa data
     * @param telefon ->nr_telefon
     * @return donatorul cu aceste date(ori null)
     */
    Donator getDonatorDupaDate(String nume, String prenume, String adresa, long telefon);
}