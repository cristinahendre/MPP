package Service;


import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Voluntar;

public interface IService {
    Voluntar login(String e, String p, IObserver client) throws  ServiceException;

    void logout(Voluntar user, IObserver client) throws ServiceException;
    Voluntar getVoluntarDupaDate(String e, String p);
    void saveDonatie(Donatie don,  IObserver client);
    Donator getDonatorDupaDate(String n, String p, String ad, long tel);
    void saveDonator(String n, String p, String ad, long tel);

    Iterable<CazCaritabil> getCazuri();

    Iterable<Donator> getDonatori();

    Iterable<Donator> getDonatorDupaNume(String n);


}
