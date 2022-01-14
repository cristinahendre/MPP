package Service;

import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Voluntar;

public interface IServiceAM {
    Voluntar login(String e, String p) throws  ServiceException;

    void logout(Voluntar user) throws ServiceException;
    Voluntar getVoluntarDupaDate(String e, String p);
    void saveDonatie(Donatie don);
    Donator getDonatorDupaDate(String n, String p, String ad, long tel);
    void saveDonator(String n, String p, String ad, long tel);

    Iterable<CazCaritabil> getCazuri();

    Iterable<Donator> getDonatori();

    Iterable<Donator> getDonatorDupaNume(String n);
}
