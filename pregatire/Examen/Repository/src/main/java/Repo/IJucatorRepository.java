package Repo;

import Domain.Jucator;

public interface IJucatorRepository extends Repository<Integer, Jucator> {

    Jucator getPersDupaDate(String nume, String em);
}
