using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1;
using Cerinta1.networking.obj;
using Cerinta1.Service;

namespace ClientN
{

    static class StartChatClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        public static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);


            //IChatServer server=new ChatServerMock();          
            IService server = new ChatServerObjectProxy("127.0.0.1", 55555);
            ChatClientCtrl ctrl = new ChatClientCtrl(server);
            LoginForm win = new LoginForm(ctrl);
            Application.Run(win);
        }
    }
}
