using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1.Controller;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using Cerinta1.Repository;
using Cerinta1.Service;

namespace Cerinta1
{

    public partial class LoginForm : Form
    {
        LoginController ctr;
       

        public LoginForm()
        {
            InitializeComponent();
        }

        public void setController(LoginController ctr)
        {
            this.ctr = ctr;
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
                this.Visible = false;
                ctr.autentifica(email, pass);
                usernameField.Text = "";
                parolaField.Text = "";
                
              
                
               
            }
            else
            {
                MessageBox.Show("Introduceti datele.");
            }
        }

      
    }
}
