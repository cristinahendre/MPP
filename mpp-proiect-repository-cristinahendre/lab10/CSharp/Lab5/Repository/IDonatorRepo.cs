using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;

namespace Cerinta1.Repository
{
   public  interface IDonatorRepo: IRepository<int ,Donator>
    {

    /*
    * Cauta donatorii, date fiind cateva litere din numele lor
    * @param nume -> un nume de donator(poate incomplet)
    * @return toti donatorii ce incep cu literele din nume
    */
        IEnumerable<Donator> getDonatorDupaNume(string nume);



        /**
        * Se cauta daca exista un donator cu aceste date in baza de date
        * @param nume ->nume dat
        * @param prenume ->prenume dat
        * @param adresa ->adresa data
        * @param telefon ->nr_telefon
        * @return donatorul cu aceste date(ori null)
        */
        Donator getDonatorDupaDate(string nume, string prenume, string addr, long nr);
    }
}
