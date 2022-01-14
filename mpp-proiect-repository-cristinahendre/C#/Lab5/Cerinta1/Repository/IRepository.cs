using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;

namespace Cerinta1.Repository
{
    public interface IRepository<ID, E> where E : Entity<ID>
    { 
        /*
        * Se cauta si returneaza entitatea ce are un anumit id
        * @param id ->id-ul entitatii
        * @return entitatea gasita sau null
        */
        E FindOne(ID id);


         /*
         * 
         * @return toate entitatile salvate
         */
        IEnumerable<E> FindAll();

        /*
        * Se salveaza o noua entitate
        * @param entity ->entitatea data
        * @return entitatea salvata (sau null daca nu se poate salva)
         */
        E Save(E entity);

        /*
        * Se sterge entitatea marcata de id
        * @param id ->id-ul dat
        * @return enitatea ce are acel id (daca s-a sters) sau null
        */
        E Delete(ID id);



        /*
        * Se modifica o enitate data astfel: id-ul ramane acelasi, insa restul atributelor
        * au valorile modificate
        * @param entity ->enitatea data
        * @return noua entitate
        */
        E Update(E entity);

    /*
    * 
    * @return numarul de entitati salvate
    */
        int nrElem();
    }
}
