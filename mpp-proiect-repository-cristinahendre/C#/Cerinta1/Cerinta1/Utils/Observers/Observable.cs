using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Utils.Observers
{
    interface IObservable<E> where E:Event
    {

       /*
     * Adauga un nou observer
     * @param e ->observer de adaugat
         */
        void AddObserver(IObserver<E> e);


     /*
     * Sterge un observer existent
     * @param e ->observer de sters
     */
        void RemoveObserver(IObserver<E> e);


    /*
    * Notifica toti observantii la efectuarea unui eveniment
    * @param t -> evenimentul
    */
        void NotifyObservers(E e);
    }
}
