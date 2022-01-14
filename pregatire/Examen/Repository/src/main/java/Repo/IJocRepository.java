package Repo;

import Domain.Joc;

import javax.persistence.criteria.CriteriaBuilder;

public interface IJocRepository extends Repository<Integer, Joc> {

    String getCuvinteRunde(int id);
    String getCaracteristiciPeJoc(int id, int idUser);
}
