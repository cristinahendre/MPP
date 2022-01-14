package Repository;

import Domain.Entity;
import Domain.Voluntar;

public interface VoluntarRepoI
        extends RepositoryH<Integer, Voluntar> {

    /**
     * Se cauta voluntarul ce are acel email si parola
     * @param email ->adresa de email a voluntarului cautat
     * @param parola ->parola voluntarului cautat
     * @return voluntarul gasit (ori null)
     */
    Voluntar getVoluntarDupaDate(String email, String parola);

}