
namespace Cerinta1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.cautaDonator = new System.Windows.Forms.TextBox();
            this.numeDonator = new System.Windows.Forms.TextBox();
            this.prenumeDonator = new System.Windows.Forms.TextBox();
            this.adresaDonator = new System.Windows.Forms.RichTextBox();
            this.nrDonator = new System.Windows.Forms.TextBox();
            this.sumaDonata = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.addDonatie = new System.Windows.Forms.Button();
            this.logoutButton = new System.Windows.Forms.Button();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.BackgroundColor = System.Drawing.SystemColors.ActiveCaption;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(12, 49);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 62;
            this.dataGridView1.RowTemplate.Height = 28;
            this.dataGridView1.Size = new System.Drawing.Size(626, 369);
            this.dataGridView1.TabIndex = 0;
            this.dataGridView1.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
            this.dataGridView1.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label1.ForeColor = System.Drawing.Color.DeepSkyBlue;
            this.label1.Location = new System.Drawing.Point(23, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(161, 25);
            this.label1.TabIndex = 1;
            this.label1.Text = "Donatori existenti";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label2.ForeColor = System.Drawing.Color.DeepSkyBlue;
            this.label2.Location = new System.Drawing.Point(23, 451);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(125, 25);
            this.label2.TabIndex = 2;
            this.label2.Text = "Cazuri active";
            // 
            // dataGridView2
            // 
            this.dataGridView2.BackgroundColor = System.Drawing.SystemColors.ActiveCaption;
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(28, 502);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.RowHeadersWidth = 62;
            this.dataGridView2.RowTemplate.Height = 28;
            this.dataGridView2.Size = new System.Drawing.Size(408, 341);
            this.dataGridView2.TabIndex = 3;
            // 
            // cautaDonator
            // 
            this.cautaDonator.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.Suggest;
            this.cautaDonator.Font = new System.Drawing.Font("Microsoft YaHei", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.cautaDonator.ForeColor = System.Drawing.SystemColors.MenuHighlight;
            this.cautaDonator.Location = new System.Drawing.Point(823, 49);
            this.cautaDonator.Name = "cautaDonator";
            this.cautaDonator.Size = new System.Drawing.Size(298, 34);
            this.cautaDonator.TabIndex = 4;
            this.cautaDonator.TextChanged += new System.EventHandler(this.cautaDonator_TextChanged);
            this.cautaDonator.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.cautaDonator_KeyPress);
            // 
            // numeDonator
            // 
            this.numeDonator.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.Suggest;
            this.numeDonator.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.numeDonator.ForeColor = System.Drawing.SystemColors.MenuHighlight;
            this.numeDonator.Location = new System.Drawing.Point(871, 134);
            this.numeDonator.Name = "numeDonator";
            this.numeDonator.Size = new System.Drawing.Size(193, 28);
            this.numeDonator.TabIndex = 5;
            // 
            // prenumeDonator
            // 
            this.prenumeDonator.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.Suggest;
            this.prenumeDonator.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.prenumeDonator.ForeColor = System.Drawing.SystemColors.MenuHighlight;
            this.prenumeDonator.Location = new System.Drawing.Point(871, 192);
            this.prenumeDonator.Name = "prenumeDonator";
            this.prenumeDonator.Size = new System.Drawing.Size(193, 28);
            this.prenumeDonator.TabIndex = 6;
            // 
            // adresaDonator
            // 
            this.adresaDonator.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.adresaDonator.ForeColor = System.Drawing.SystemColors.MenuHighlight;
            this.adresaDonator.Location = new System.Drawing.Point(871, 259);
            this.adresaDonator.Name = "adresaDonator";
            this.adresaDonator.Size = new System.Drawing.Size(250, 96);
            this.adresaDonator.TabIndex = 7;
            this.adresaDonator.Text = "";
            // 
            // nrDonator
            // 
            this.nrDonator.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.Suggest;
            this.nrDonator.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.nrDonator.ForeColor = System.Drawing.SystemColors.MenuHighlight;
            this.nrDonator.Location = new System.Drawing.Point(871, 390);
            this.nrDonator.Name = "nrDonator";
            this.nrDonator.Size = new System.Drawing.Size(193, 28);
            this.nrDonator.TabIndex = 8;
            // 
            // sumaDonata
            // 
            this.sumaDonata.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.sumaDonata.ForeColor = System.Drawing.SystemColors.MenuHighlight;
            this.sumaDonata.Location = new System.Drawing.Point(663, 532);
            this.sumaDonata.Name = "sumaDonata";
            this.sumaDonata.Size = new System.Drawing.Size(208, 30);
            this.sumaDonata.TabIndex = 9;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label3.ForeColor = System.Drawing.Color.SteelBlue;
            this.label3.Location = new System.Drawing.Point(710, 137);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(126, 22);
            this.label3.TabIndex = 10;
            this.label3.Text = "Nume Donator";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label4.ForeColor = System.Drawing.Color.SteelBlue;
            this.label4.Location = new System.Drawing.Point(701, 192);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(151, 22);
            this.label4.TabIndex = 11;
            this.label4.Text = "Prenume Donator";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label5.ForeColor = System.Drawing.Color.SteelBlue;
            this.label5.Location = new System.Drawing.Point(701, 390);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(159, 22);
            this.label5.TabIndex = 12;
            this.label5.Text = "Nr. telefon donator";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label6.ForeColor = System.Drawing.Color.SteelBlue;
            this.label6.Location = new System.Drawing.Point(710, 273);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(136, 22);
            this.label6.TabIndex = 13;
            this.label6.Text = "Adresa Donator";
            // 
            // addDonatie
            // 
            this.addDonatie.BackColor = System.Drawing.Color.Transparent;
            this.addDonatie.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.addDonatie.ForeColor = System.Drawing.Color.DarkBlue;
            this.addDonatie.Location = new System.Drawing.Point(690, 620);
            this.addDonatie.Name = "addDonatie";
            this.addDonatie.Size = new System.Drawing.Size(162, 72);
            this.addDonatie.TabIndex = 14;
            this.addDonatie.Text = "Adauga Donatia";
            this.addDonatie.UseVisualStyleBackColor = false;
            this.addDonatie.Click += new System.EventHandler(this.addDonatie_Click);
            // 
            // logoutButton
            // 
            this.logoutButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.logoutButton.ForeColor = System.Drawing.Color.DarkBlue;
            this.logoutButton.Location = new System.Drawing.Point(690, 733);
            this.logoutButton.Name = "logoutButton";
            this.logoutButton.Size = new System.Drawing.Size(118, 60);
            this.logoutButton.TabIndex = 15;
            this.logoutButton.Text = "Log out";
            this.logoutButton.UseVisualStyleBackColor = true;
            this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label7.ForeColor = System.Drawing.Color.SteelBlue;
            this.label7.Location = new System.Drawing.Point(681, 49);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(127, 22);
            this.label7.TabIndex = 16;
            this.label7.Text = "Cauta Donator";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label8.ForeColor = System.Drawing.Color.SteelBlue;
            this.label8.Location = new System.Drawing.Point(522, 532);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(116, 22);
            this.label8.TabIndex = 17;
            this.label8.Text = "Suma donata";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.LightCyan;
            this.ClientSize = new System.Drawing.Size(1163, 885);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.logoutButton);
            this.Controls.Add(this.addDonatie);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.sumaDonata);
            this.Controls.Add(this.nrDonator);
            this.Controls.Add(this.adresaDonator);
            this.Controls.Add(this.prenumeDonator);
            this.Controls.Add(this.numeDonator);
            this.Controls.Add(this.cautaDonator);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dataGridView1);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.TextBox cautaDonator;
        private System.Windows.Forms.TextBox numeDonator;
        private System.Windows.Forms.TextBox prenumeDonator;
        private System.Windows.Forms.RichTextBox adresaDonator;
        private System.Windows.Forms.TextBox nrDonator;
        private System.Windows.Forms.TextBox sumaDonata;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Button addDonatie;
        private System.Windows.Forms.Button logoutButton;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
    }
}

