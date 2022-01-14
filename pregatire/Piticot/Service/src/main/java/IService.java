import Domain.Persoana;

import java.util.List;

public interface IService {
    Persoana getJucator(String e, String p) throws  ServiceException;

    void login(Persoana j,  IObserver client);
    void logout(Persoana user, IObserver client) throws ServiceException;
    Persoana save(String nume, String em,  IObserver client);
    void delete(Persoana p);
    void update(Persoana p);

    List<Persoana> getPeople();

    Persoana getOne(int id);
    Persoana getPersoanaDupaDate(String n, String e);
    void inainteaza(int n, Persoana p);

    void startGame(Persoana p);


}
