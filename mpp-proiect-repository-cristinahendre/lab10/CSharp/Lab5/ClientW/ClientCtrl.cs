using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Cerinta1.Service;

namespace ClientW
{
    class ClientCtrl: IObserver
    {
        public event EventHandler<UserEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly IService server;
        private Voluntar voluntar;
        public ClientCtrl(IService server)
        {
            this.server = server;
            voluntar = null;
        }

        public void login(String userId, String pass)
        {
            
            server.login(userId,pass, this);
            Console.WriteLine("Login succeeded ....");
           
        }
       

        public void logout()
        {
            Console.WriteLine("Ctrl logout");
            server.logout(voluntar, this);
            voluntar = null;
        }

        protected virtual void OnUserEvent(UserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
      
        public void refreshCazuri(Donatie don)
        {
            throw new NotImplementedException();
        }
    }
}

