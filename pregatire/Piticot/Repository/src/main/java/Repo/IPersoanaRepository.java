package Repo;

import Domain.Persoana;

public interface IPersoanaRepository extends Repository<Integer, Persoana> {

    Persoana getPersDupaDate(String nume, String em);
}
