using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ClientW
{
    internal class StartClient

    {
        public static void Main(string[] args)
        {
            Application.Init();
            IService server = new ChatServerProxy("127.0.0.1", 55555);
            GTKClientCtrl ctrl = new GTKClientCtrl(server);
            Window w = new LoginWindow(ctrl);
            // Window w = new ChatWindow();
            w.ShowAll();
            Application.Run();

        }
    }
}
