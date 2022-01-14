package Repo;

import Domain.Cuvant;

public interface ICuvantRepository extends Repository<Integer, Cuvant>{
    Cuvant getCuvantDupaLitere(String litere);
}
