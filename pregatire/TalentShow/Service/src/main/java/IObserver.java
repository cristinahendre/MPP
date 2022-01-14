import Domain.Persoana;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void refresh( )throws ServiceException, RemoteException;
}
