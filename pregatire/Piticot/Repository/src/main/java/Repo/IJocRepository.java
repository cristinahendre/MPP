package Repo;

import Domain.Joc;

public interface IJocRepository extends Repository<Integer, Joc>{
    Joc getJocDupaDate(int id, String user);
}
