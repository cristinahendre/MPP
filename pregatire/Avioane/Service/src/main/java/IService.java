import Domain.Joc;
import Domain.Persoana;

import java.util.List;

public interface IService {
    Persoana login(String e, String p, IObserver client) throws  ServiceException;

    void logout(Persoana user, IObserver client) throws ServiceException;
    Persoana save(String nume, String em,  IObserver client);
    void delete(Persoana p);
    void update(Persoana p);

    Iterable<Persoana> getPeople();

    Persoana getOne(int id);
    Persoana getPersoanaDupaDate(String n, String e);

    Persoana alegeUnParticipant(Persoana p );

    void incepeJoc(Joc c);

    List<Persoana> getPersoaneParticipante();

    boolean participaLaJocPersoana(Persoana p );

    void ghicire(Persoana p , int poz);


}
