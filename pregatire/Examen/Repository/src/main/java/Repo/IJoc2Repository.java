package Repo;

import Domain.Joc;

import java.util.List;

public interface IJoc2Repository extends Repository<Integer, Joc>{

    List<Joc> getJocuriDinRunda(int id, int rundaId);
    Joc getJocParticipant(int id, int runda, int user);
    List<Joc> getToateJocurile(int id);
}
