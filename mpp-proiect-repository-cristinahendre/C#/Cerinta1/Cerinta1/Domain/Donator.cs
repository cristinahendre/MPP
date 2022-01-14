using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain
{
    class Donator: Entity<int>
    {
        string nume;
        string prenume;
        string adresa;
        long nrTelefon;

        public Donator(string nume, string prenume, string adresa, long nrTelefon)
        {
            this.nume = nume;
            this.prenume = prenume;
            this.adresa = adresa;
            this.nrTelefon = nrTelefon;
        }

        public string Nume { get => nume; set => nume = value; }
        public string Prenume { get => prenume; set => prenume = value; }
        public string Adresa { get => adresa; set => adresa = value; }
        public long NrTelefon { get => nrTelefon; set => nrTelefon = value; }

        public override bool Equals(object obj)
        {
            return obj is Donator donator &&
                   nume == donator.nume &&
                   prenume == donator.prenume &&
                   adresa == donator.adresa &&
                   nrTelefon == donator.nrTelefon;
        }

        public override int GetHashCode()
        {
            int hashCode = -2092692272;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(nume);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(prenume);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(adresa);
            hashCode = hashCode * -1521134295 + nrTelefon.GetHashCode();
            return hashCode;
        }

        public override string ToString()
        {
            return "Id = " + this.Id + "  ,nume = " + this.GetFullName() + " , adresa = " +
                this.Adresa + "  , nr. telefon = " + this.NrTelefon;
        }

        public String GetFullName()
        {
            return this.Nume + " " + this.Prenume ;
        }
    }
}
