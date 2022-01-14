using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using Cerinta1.Repository;

namespace Cerinta1.Service
{
    public class SuperService: IService
    {
        private ICazRepo cazRepo;
        private IDonatieRepo donatieRepo;
        private IDonatorRepo donatorRepo;
        private IVoluntarRepo voluntarRepo;

        private IValidator<Donator> donatorV;
        private IValidator<Donatie> donatieV;
        private IDictionary<int, IObserver> loggedClients;

        public SuperService(ICazRepo cazRepo, IDonatieRepo donatieRepo, IDonatorRepo donatorRepo,
            IVoluntarRepo voluntarRepo, IValidator<Donator> donatorV, 
            IValidator<Donatie> donatieV)
        {
            this.cazRepo = cazRepo;
            this.donatieRepo = donatieRepo;
            this.donatorRepo = donatorRepo;
            this.voluntarRepo = voluntarRepo;
            this.donatorV = donatorV;
            this.donatieV = donatieV;
            loggedClients= new Dictionary<int, IObserver>();
        }


        //cauta cazul cu acest id
        public CazCaritabil findCaz(int id)
        {
            return cazRepo.FindOne(id);
        }


        //returneaza toate cazurile caritabile
        public IEnumerable<CazCaritabil> getCazuri()
        {
            Console.WriteLine("super service: get cazuri");
          
            IEnumerable<CazCaritabil> cazs =cazRepo.FindAll();
           /* Console.WriteLine("am gasit cazuri in super service de la bd: "+cazs);
            CazCaritabil[] cazuri = new CazCaritabil[cazs.Count()];
            int i = 0;
            foreach(CazCaritabil caz in cazs)
            {
                cazuri[i] = caz;
                i++;
             
            }*/
            
            return cazs;
        }


        //returneaza toti donatorii
        public IEnumerable<Donator> getDonatori()
        {
            IEnumerable<Donator> cazs = donatorRepo.FindAll();
            /*Donator[] cazuri = new Donator[cazs.Count()];
            int i = 0;
            foreach (Donator caz in cazs)
            {
                cazuri[i] = caz;
                i++;
            }*/
            return cazs;
        }


      



       



        //se cauta voluntarul ce are acest email si aceasta parola
        public Voluntar getVoluntarDupaDate(string email, string parola)
        {
            return voluntarRepo.getVoluntarDupaEmail(email, parola);
        }


        //se dau aceste date despre donator si se cauta id-ul sau
        public Donator getDonatorDupaDate(string nume, string prenume, string addr, long nr)
        {
            return donatorRepo.getDonatorDupaDate(nume, prenume, addr, nr);
        }

        public void closeCon()
        {
            voluntarRepo.closeConnection();
        }

        public Voluntar login(string e, string p, IObserver obs)
        {
            Console.WriteLine("super service: login...");
            Voluntar vol = voluntarRepo.getVoluntarDupaEmail(e, p);
            Console.WriteLine("super service: found vol: "+vol);
           
           /* if (vol != null)
            {
                if (loggedClients.ContainsKey(vol.Id))
                    throw new ServiceException("User already logged in.");
                loggedClients[vol.Id] = obs;
               
            }
            else
                throw new ServiceException("Authentication failed.");*/
            Console.WriteLine("super service: response with vol");
            loggedClients.Add(new KeyValuePair<int, IObserver>(vol.Id, obs));
            return vol;
        }

        public void logout(Voluntar user, IObserver client)
        {
            Console.WriteLine("super service logout");
            Console.WriteLine("received user = "+user);
            if (client == null)
                throw new ServiceException("User " + user.Id + " is not logged in.");
            loggedClients.Remove(user.Id);
            
        }

        private void notifyAll(Donatie d)
        {

            Console.WriteLine("super service: notify..");
            foreach (int  us in loggedClients.Keys)
            {
                Console.WriteLine("cheie: "+us);
               
                IObserver chatClient = loggedClients[us];
                Task.Run(()=>chatClient.refreshCazuri(d));
                
            }
        }


        public void saveDonatie(Donatie don, IObserver client)
        {
            Console.WriteLine("super service: salvez o donatie");
            donatieRepo.Save(don);
            notifyAll(don);
        }

        

        public IEnumerable<Donator> getDonatorDupaNume(string n)
        {
            Console.WriteLine("am intrat: super service; caut nume "+n);

            IEnumerable<Donator> cazs = donatorRepo.getDonatorDupaNume(n);
            /*Donator[] cazuri = new Donator[cazs.Count()];
            int i = 0;
            foreach (Donator caz in cazs)
            {
                cazuri[i] = caz;
                i++;
            }
            Console.WriteLine("super service: i offer: ");
            foreach(Donator don in cazuri)
                Console.WriteLine(don);*/
            return cazs;
        }

        public void saveDonator(string nume, string prenume, string addr, long nr)
        {
            Donator don = new Donator(nume, prenume, addr, nr);
            donatorV.Validate(don);
            donatorRepo.Save(don);
        }
    }
}
