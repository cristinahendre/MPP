package utils;

public interface Observable<E extends Event> {

    /**
     * Adauga un nou observer
     * @param e ->observer de adaugat
     */
    void addObserver(Observer<E> e);

    /**
     * Sterge un observer existent
     * @param e ->observer de sters
     */
    void removeObserver(Observer<E> e);


    /**
     * Notifica toti observantii la efectuarea unui eveniment
     * @param t -> evenimentul
     */
    void notifyObservers(E t);
}
