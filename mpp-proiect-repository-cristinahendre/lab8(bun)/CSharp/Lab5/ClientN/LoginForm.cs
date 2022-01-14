using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

using Cerinta1.Domain;
using Cerinta1.Domain.Validator;

using Cerinta1.Service;
using ClientN;

namespace Cerinta1
{

    public partial class LoginForm : Form
    {
        private ChatClientCtrl ctr;


        public LoginForm( ChatClientCtrl  ctr)
        {
            this.ctr = ctr;
            InitializeComponent();
        }

     
    

        private void LoginForm_Load(object sender, EventArgs e)
        {
         
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            
            if(usernameField !=null && parolaField!= null &&
                usernameField.Text!="" && parolaField.Text!="")
            {
                string email = usernameField.Text;
                string pass = parolaField.Text;
                Voluntar rez= ctr.login(email, pass);
                //MessageBox.Show("voluntar rez: "+rez);
                //MessageBox.Show("succcessss."+rez.ToString());
                //MessageBox.Show("Login succeded");
                Form1 chatWin = new Form1(ctr);
                chatWin.Text = "Chat window for " + email;
                chatWin.setVoluntar(rez, this);
                chatWin.Show();
               
                usernameField.Text = "";
                parolaField.Text = "";
                this.Hide();




            }
            else
            {
                MessageBox.Show("Introduceti datele.");
            }
        }

      
    }
}
