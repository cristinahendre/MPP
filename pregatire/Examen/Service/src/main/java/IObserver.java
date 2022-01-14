import Domain.Jucator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void refresh(Jucator don )throws ServiceException, RemoteException;
    void putemIncepe() throws RemoteException;
    void trimitCuvant(String cuv) throws RemoteException;

    void afiseazaPuncte(String s, String cuvantNou) throws RemoteException;

    void sfarsitJoc(String msg) throws RemoteException;
}
