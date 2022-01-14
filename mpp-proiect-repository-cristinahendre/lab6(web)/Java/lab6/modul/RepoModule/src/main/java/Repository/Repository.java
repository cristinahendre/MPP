package Repository;

import Domain.Entity;

public interface Repository<ID, E extends Entity<ID>> {


    /**
     * Se cauta si returneaza entitatea ce are un anumit id
     * @param id ->id-ul entitatii
     * @return entitatea gasita sau null
     */
    E findOne(ID id);


    /**
     *
     * @return toate entitatile salvate
     */
    Iterable<E> findAll();


    /**
     * Se salveaza o noua entitate
     * @param entity ->entitatea data
     * @return entitatea salvata (sau null daca nu se poate salva)
     */
    E save(E entity);


    /**
     * Se sterge entitatea marcata de id
     * @param id ->id-ul dat

     */
    void delete(ID id);


    /**
     * Se modifica o enitate data astfel: id-ul ramane acelasi, insa restul atributelor
     * au valorile modificate
     * @param entity ->enitatea data
     * @return noua entitate
     */
    E update(E entity);


    /**
     *
     * @return numarul de entitati salvate
     */
    int nrElem();


}

