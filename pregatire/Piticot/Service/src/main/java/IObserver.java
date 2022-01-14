import Domain.Persoana;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void refresh(String don )throws ServiceException, RemoteException;
    void incepe() throws  RemoteException;
    void notificaDate(String data, String traseu) throws RemoteException;
}
