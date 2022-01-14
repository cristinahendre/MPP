package Repository;

import Domain.CazCaritabil;
import Domain.Entity;

public interface CazRepoI
        extends Repository<Integer, CazCaritabil> {

    int getIdCaz(CazCaritabil c);
}