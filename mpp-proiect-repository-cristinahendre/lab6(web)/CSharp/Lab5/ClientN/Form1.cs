using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

using System.Configuration;

using Cerinta1.Domain;
using ClientN;

namespace Cerinta1
{
    public partial class Form1 : Form
    {
       // string connectionString = ConfigurationManager.ConnectionStrings["teledonDB"].ConnectionString;

       
        ChatClientCtrl ctr;
        Voluntar actual;
        LoginForm formLogin;
        private readonly IList<String> data = new List<String>();

        public Form1(ChatClientCtrl ctr)
        {
            this.ctr = ctr;
            ctr.setUser(actual);
            InitializeComponent();
        }

        public void setVoluntar(Voluntar ctr, LoginForm login)
        {
            this.actual = ctr;
            this.formLogin = login;
        }


        public void userUpdate(object sender, UserEventArg e)
        {
            if (e.UserEventType == ChatUserEvent.Refresh)
            {
                
                dataGridView2.BeginInvoke(new UpdateDataGrid(this.updateGrid) , new Object[] {dataGridView2 });
                //data.BeginInvoke((Action) delegate { friendList.DataSource = friendsData; });
                //dataGridView2.Invoke(new UpdateListBoxCallback(this.updateListBox), getCazuri());

            }
        }


        public IEnumerable<CazCaritabil> getCazuri()
        {
            return ctr.getCazuri();
        }



            private void Form1_Load(object sender, EventArgs e)
        {
            
            this.Width = 810;
            this.Text = actual.Email;
            dataGridView2.SelectionMode = DataGridViewSelectionMode.FullRowSelect;




            dataGridView2.RowHeadersVisible = false;
            dataGridView1.RowHeadersVisible = false;
            this.Height = 660;

            initializare();









        }

        

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            string nume = dataGridView1.CurrentRow.Cells[0].Value.ToString();
            string prenume= dataGridView1.CurrentRow.Cells[1].Value.ToString();
            string addr= dataGridView1.CurrentRow.Cells[2].Value.ToString();
            string telefon = dataGridView1.CurrentRow.Cells[3].Value.ToString();

            numeDonator.Text = nume;
            prenumeDonator.Text = prenume;
            adresaDonator.Text = addr;
            nrDonator.Text = telefon;
        }

        private void addDonatie_Click(object sender, EventArgs e)
        {
            if (numeDonator.Text == "" || prenumeDonator.Text == "" || adresaDonator.Text == ""
               || nrDonator.Text == "")
            {
                MessageBox.Show("Date invalide.");
                return;

            }
            string nume = numeDonator.Text;
            string prenume = prenumeDonator.Text;
            string adresa = adresaDonator.Text;
            string telefon = nrDonator.Text;
            if (sumaDonata.Text == "")
            {
                MessageBox.Show("Nu ati tastat suma donata.");
                return;
            }
            string suma = sumaDonata.Text;

            if (dataGridView2.CurrentRow.Cells[2].Value == null)
            {
                MessageBox.Show("Nu ati selectat un caz.");
                return;
            }
            var selectedRows = dataGridView2.SelectedRows
            .OfType<DataGridViewRow>()
            .ToArray();




            //int id_caz =int.Parse( dataGridView2.CurrentRow.Cells[2].Value.ToString());
            //CazCaritabil caz = service.findCaz(id_caz);


            long tel = long.Parse(telefon);
            int sumadonata = int.Parse(suma);

            IList<int> listaIds = new List<int>();
            foreach (var row in selectedRows)
            {
                int id_caz = int.Parse(row.Cells[2].Value.ToString());
                listaIds.Add(id_caz);

            }
            ctr.addDonatie(nume, prenume, adresa, tel, sumadonata, listaIds);
            //initializare();


        }

        public void setData()
        {
            
            IEnumerable<CazCaritabil> cazuri = ctr.getCazuri();
            foreach (CazCaritabil c in cazuri)
            {
                data.Add(c.ToString());
            }
            
        }

        private void initializare()
        {
            IEnumerable<CazCaritabil> cazuri = ctr.getCazuri();
            dataGridView2.DataSource = cazuri;
            setData();
            ctr.updateEvent += userUpdate;

            dataGridView2.Columns["id"].Visible = false;
            dataGridView2.Columns["nume"].DisplayIndex = 0;
            dataGridView2.Columns["nume"].Width = 160;
            dataGridView2.Columns["suma_donata"].DisplayIndex = 1;
            dataGridView1.DataSource = ctr.getDonatori();
            dataGridView1.Columns["id"].Visible = false;

        }

        private void logoutButton_Click(object sender, EventArgs e)
        {

            MessageBox.Show("La revedere,  "+actual.Email);
            ctr.setUser(actual);
            ctr.logout();
            ctr.updateEvent -= userUpdate;
            //formLogin.Show();
            Application.Exit();


            //   ctr.afisareLogin();

        }

        private void cautaDonator_KeyPress(object sender, KeyPressEventArgs e)
        {
            

        }

        private void cautaDonator_TextChanged(object sender, EventArgs e)
        {
            string text = cautaDonator.Text;
            dataGridView1.DataSource = ctr.getDonatordupaNume(text);
        }


        //1. define a method for updating the ListBox
        private void updateListBox(ListBox listBox, IList<String> newData)
        {
            listBox.DataSource = null;
            listBox.DataSource = newData;
        }

        private void updateGrid(DataGridView listBox)
        {
            IEnumerable<CazCaritabil> newData = getCazuri();
            listBox.DataSource = null;
         
            listBox.DataSource = newData;
            listBox.Columns["id"].Visible = false;
            listBox.Columns["nume"].DisplayIndex = 0;
            listBox.Columns["nume"].Width = 160;
            listBox.Columns["suma_donata"].DisplayIndex = 1;
      
        }

        //2. define a delegate to be called back by the GUI Thread
        public delegate void UpdateListBoxCallback(ListBox list, IList<String> data);

        public delegate void UpdateDataGrid(DataGridView list);



        //3. in the other thread call like this:
        /*
         * list.Invoke(new UpdateListBoxCallback(this.updateListBox), new Object[]{list, data});
         * 
         * */



    }
}
