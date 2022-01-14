using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SQLite;
using Cerinta1.Repository;

namespace Cerinta1
{
    public partial class Form1 : Form
    {
        string connectionString = "Data Source=C:\\Users\\crist\\sqllite\\databases\\teledon.db;Version=3;";

        DataSet ds = new DataSet();
        SQLiteDataAdapter adapter = new SQLiteDataAdapter();
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                
                using (SQLiteConnection connection = new SQLiteConnection(connectionString))
                {
                    connection.Open();

                    adapter.SelectCommand = new SQLiteCommand("select * from voluntar",connection);
                    adapter.Fill(ds, "voluntar");
                    dataGridView1.DataSource = ds.Tables["voluntar"];

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
           
           
          

        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
