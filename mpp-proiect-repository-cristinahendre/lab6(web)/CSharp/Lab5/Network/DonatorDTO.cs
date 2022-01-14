using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Network
{
    [Serializable]
    public class DonatorDTO
    {
        string nume;
        string pre;
        string ad;
        long tel;

        public DonatorDTO(string n, string p, string a, long t)
        {
            this.nume = n;
            this.pre = p;
            this.ad = a;
            this.tel = t;
        }

        public DonatorDTO(string nume)
        {
            this.nume = nume;
        }

        public string Nume { get => nume; set => nume = value; }
        public string Pre { get => pre; set => pre = value; }
        public string Adresa { get => ad; set => ad = value; }
        public long Tel { get => tel; set => tel = value; }

        public override string ToString()
        {
            return "Nume = " + this.nume;
        }
    }
}
