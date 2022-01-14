package Repo;

import Domain.Persoana;

public interface Repository<ID, E > {

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


}
