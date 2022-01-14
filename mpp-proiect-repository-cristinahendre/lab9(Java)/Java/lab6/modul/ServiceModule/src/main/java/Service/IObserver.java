package Service;


import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;

public interface IObserver {
    void refreshCazuri(Donatie don )throws ServiceException;
}
