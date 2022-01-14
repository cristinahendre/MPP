package Service;


import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void refreshCazuri(Donatie don )throws ServiceException, RemoteException;
}
