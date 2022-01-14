using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1.Domain;
using Cerinta1.Service;

namespace Cerinta1.Controller
{
    public class MainController
    {

        Form1 form;
        SuperService service;
        LoginForm loginF;


        public void afisareLogin()
        {
            loginF.Show();
        }


        public MainController(SuperService service, LoginForm loginF)
        {
            this.service = service;
            this.loginF = loginF;
        }
        

        public void addDonatie(string nume, string prenume, string adresa,
            long tel, int suma, IList<int> id_caz)
        {

            Donator don = service.getDonatorDupaDate(nume, prenume, adresa, tel);
            if (don == null)
            {
                service.saveDonator(nume, prenume, adresa, tel);
                Donator salvat = service.getDonatorDupaDate(nume, prenume, adresa, tel);
                foreach(var id in id_caz)
                {
                    CazCaritabil caz = service.findCaz(id);
                    service.saveDonatie(caz, salvat, suma);
                }

            }

            else
            {
                foreach (var row in id_caz)
                {
                    
                    CazCaritabil caz = service.findCaz(row);

                    service.saveDonatie(caz, don, suma);

                }

            }
      

        }
        

        public IEnumerable<CazCaritabil> getCazuri()
        {
            return service.getCazuri();
        }

        public IEnumerable<Donator> getDonatori()
        {
            return service.getDonatori();
        }


        public IEnumerable<Donator> getDonatordupaNume(string nume)
        {
            return service.getDonatordupaNume(nume);
        }
    }
}
