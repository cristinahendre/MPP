import Domain.Persoana;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void refresh(Persoana don )throws ServiceException, RemoteException;
    void pozitieInamic(Persoana p ,int pozitie) throws  RemoteException;

    void castigator(Persoana p ) throws  RemoteException;
}
