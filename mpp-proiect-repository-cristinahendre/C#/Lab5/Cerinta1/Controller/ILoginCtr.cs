using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Controller
{
    public interface ILoginCtr
    {
        void SetController(LoginController controller);
        void ClearGrid();

        void autentifica(String email, string parola);
        String getEmail();
        string getParola();
        

        string Username { get; set; }
        string Parola { get; set; }
        
    }
}
