using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;

namespace Cerinta1.Service
{
    public interface IService
    {
        Voluntar login(String e, String p, IObserver obs);
        void logout(Voluntar user, IObserver client);

        CazCaritabil findCaz(int id);

        Voluntar getVoluntarDupaDate(String e, String p);
        void saveDonatie(Donatie don, IObserver client);
        Donator getDonatorDupaDate(String n, String p, String ad, long tel);
        void saveDonator(String n, String p, String ad, long tel);

        IEnumerable<CazCaritabil> getCazuri();

        IEnumerable<Donator> getDonatori();

        IEnumerable<Donator> getDonatorDupaNume(String n);
    }
}
