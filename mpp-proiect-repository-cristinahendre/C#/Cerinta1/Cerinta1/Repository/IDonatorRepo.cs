using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;

namespace Cerinta1.Repository
{
    interface IDonatorRepo: IRepository<int ,Donator>
    {

    /*
    * Cauta donatorii, date fiind cateva litere din numele lor
    * @param nume -> un nume de donator(poate incomplet)
    * @return toti donatorii ce incep cu literele din nume
    */
        IEnumerable<Donator> getDonatorDupaNume(string nume);
    }
}
