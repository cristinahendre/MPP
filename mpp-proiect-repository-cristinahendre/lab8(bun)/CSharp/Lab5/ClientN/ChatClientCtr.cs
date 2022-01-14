using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Cerinta1.Repository;
using Cerinta1.Service;


namespace ClientN
{

    public class ChatClientCtrl : IObserver
    {

        public event EventHandler<UserEventArg> updateEvent; //ctrl calls it when it has received an update
        private readonly IService server;
        private Voluntar currentUser;
        public ChatClientCtrl(IService server)
        {
            this.server = server;
          
        }

        public void setUser(Voluntar vol)
        {
            this.currentUser = vol;
        }

        public Voluntar login(String userId, String pass)
        {
            Console.WriteLine("client ctr: login sent");
            return server.login(userId, pass, this);
            
          
        }
    
        public void logout()
        {
            Console.WriteLine("Ctrl logout");
            server.logout(currentUser, this);
            currentUser = null;
        }

        protected virtual void OnUserEvent(UserEventArg e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }

        public void addDonatie(string n, string p, string a, long tel, int su, IList<int> ids)
        {
           
            Donator don = server.getDonatorDupaDate(n, p, a, tel);
            if(don == null)
            {
                //adaug donatorul
                server.saveDonator(n,p,a,tel);
                Donator nou = server.getDonatorDupaDate(n, p, a, tel);
                foreach (int el in ids)
                {
                    CazCaritabil caz = server.findCaz(el);
                    Donatie noua = new Donatie(caz, su, nou);
                    UserEventArg userArgs = new UserEventArg(ChatUserEvent.Refresh, noua);
                    Console.WriteLine("Message received");
                    OnUserEvent(userArgs);
                    server.saveDonatie(noua, this);
                }
            }
            else
            {
                foreach(int el in ids)
                {
                    CazCaritabil caz = server.findCaz(el);
                    Donatie noua = new Donatie(caz, su, don);
                    UserEventArg userArgs = new UserEventArg(ChatUserEvent.Refresh, noua);
                    Console.WriteLine("Message received");
                    OnUserEvent(userArgs);
                    server.saveDonatie(noua, this);
                }
            }

        }

        public IEnumerable<CazCaritabil> getCazuri()
        {

            IList<CazCaritabil> cazuri = new List<CazCaritabil>();
            foreach(CazCaritabil caz in server.getCazuri())
            {
                cazuri.Add(caz);
            }
            return cazuri;
        }


        public IEnumerable<Donator> getDonatori()
        {
            IEnumerable<Donator> don =server.getDonatori();
            return don;

        }

        public IEnumerable<Donator> getDonatordupaNume(string nume)
        {
            IEnumerable<Donator> don = server.getDonatorDupaNume(nume);
            return don;
        }


        public void refreshCazuri(Donatie don)
        {

            UserEventArg userArgs = new UserEventArg(ChatUserEvent.Refresh, don);
            Console.WriteLine("refresssh");
            OnUserEvent(userArgs);
        }
    }
}
