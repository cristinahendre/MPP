package Repository;

import Domain.Donatie;
import Domain.Entity;

public interface DonatieRepoI
        extends RepositoryH<Integer, Donatie> {

        Donatie getDonatieDupaDate(Donatie don);}