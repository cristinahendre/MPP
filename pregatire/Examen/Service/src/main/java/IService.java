import Domain.Jucator;

import java.util.List;

public interface IService {
    void login(Jucator p, IObserver client) throws  ServiceException;

    Jucator getDateJucator(String e, String p);
    void logout(Jucator user, IObserver client) throws ServiceException;
    Jucator save(String nume, String em, IObserver client);
    void delete(Jucator p);
    void update(Jucator p);

    List<Jucator> getPeople();

    Jucator getOne(int id);
    Jucator getPersoanaDupaDate(String n, String e);

    void startJoc(Jucator j);

    void procesezCaracteristici(Jucator j, String car1 ,String car2,String cuv);

}
