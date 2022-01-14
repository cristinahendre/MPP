package Repo;

import Domain.Persoana;

import java.util.List;

public interface IPersoanaRepository extends Repository<Integer, Persoana> {

    Persoana getPersDupaDate(String nume, String em);

    List<Persoana> getParticipanti();
}
