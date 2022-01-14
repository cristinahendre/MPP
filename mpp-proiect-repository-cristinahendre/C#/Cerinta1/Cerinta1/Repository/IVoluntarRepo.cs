using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;

namespace Cerinta1.Repository
{
    interface IVoluntarRepo: IRepository<int ,Voluntar>
    {

    /*
     * Se cauta voluntarul ce are acel email si parola
     * @param email ->adresa de email a voluntarului cautat
     * @param parola ->parola voluntarului cautat
     * @return voluntarul gasit (ori null)
     */
        Voluntar getVoluntarDupaEmail(string email, string parola);
    }
}
