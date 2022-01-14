using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1.Domain;
using Cerinta1.Service;

namespace Cerinta1.Controller
{
    public class LoginController
    {
        
        SuperService Service;
        LoginForm Logform;
     
      
        
        
        public LoginController( SuperService service  )
        {

      
            this.Service = service;
          
           
        }

        public void setLogin(LoginForm log)
        {
            this.Logform = log;
        }

        public void autentifica(string nume, string parola)
        {
           
                
                // MessageBox.Show(pass);
                // MessageBox.Show(email);
                Voluntar vol = Service.getVoluntarDupaDate(nume, parola);

                if (vol == null)
                {
                    MessageBox.Show("Date invalide.");
                }
                else
                {
                    MainController controller = new MainController(Service,Logform);
                    Form1 form = new Form1();
                    form.setController(controller);
                    

                    form.ShowDialog();
                    
                
                




                }
           
        }

      
    }
}
