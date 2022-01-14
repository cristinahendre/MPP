using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Cerinta1.Controller
{
    public partial class LoginView : Form, ILoginCtr
    {
        string email,parola;

        public string getEmail()
        {
            return " ";
        }

        public string getParola()
        {
            return this.Parola;
        }

        public string Username
        {
            get { return getEmail(); }
            set { this.Username = Text; }
        }
        public string Parola
        {
            get { return this.Parola; }
            set { this.Parola = Text; }
        }

        public void autentifica(string email, string parola)
        {
            throw new NotImplementedException();
        }

        public void ClearGrid()
        {
            throw new NotImplementedException();
        }

        public void SetController(LoginController controller)
        {
            throw new NotImplementedException();
        }
    }
}
