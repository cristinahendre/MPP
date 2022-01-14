using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain
{
    [Serializable]
    public class CazCaritabil : Entity<int>
    {
        string nume;
        int suma_donata;

        public CazCaritabil(string nume, int suma_donata)
        {
            this.nume = nume;
            this.suma_donata = suma_donata;
        }

        public virtual  int Suma_donata { get => suma_donata; set => suma_donata = value; }
        public virtual string Nume { get => nume; set => nume = value; }

        public override bool Equals(object obj)
        {
            return obj is CazCaritabil caritabil &&
                   nume == caritabil.nume &&
                   suma_donata == caritabil.suma_donata;
        }

        public override int GetHashCode()
        {
            int hashCode = -1494288119;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(nume);
            hashCode = hashCode * -1521134295 + suma_donata.GetHashCode();
            return hashCode;
        }

        public override string ToString()
        {
            return "Id = " + this.Id + " ,nume = " + Nume + " " + " ,suma donata = " + Suma_donata;
        }
    }
}
