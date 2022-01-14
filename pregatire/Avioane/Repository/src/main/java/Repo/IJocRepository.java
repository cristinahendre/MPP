package Repo;

import Domain.Joc;
import Domain.Persoana;

import java.util.List;

public interface IJocRepository extends Repository<Integer, Joc>{

    boolean participaLaJocPersoana(int p );

    int getPozitiePersoana(Persoana p );

    void seteazaWinner(int idPersoana, int idWinner);

    List<Joc> getJocuriPersoana(int id);
}
