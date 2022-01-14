using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain
{
    class Voluntar : Entity<int>
    {
        string nume;
        string prenume;
        string email;
        string parola;

        public Voluntar(string nume, string prenume, string email, string parola)
        {
            this.nume = nume;
            this.prenume = prenume;
            this.email = email;
            this.parola = parola;
        }

        public string Nume { get => nume; set => nume = value; }
        public string Prenume { get => prenume; set => prenume = value; }
        public string Email { get => email; set => email = value; }
        public string Parola { get => parola; set => parola = value; }

        public override bool Equals(object obj)
        {
            return obj is Voluntar voluntar &&
                   nume == voluntar.nume &&
                   prenume == voluntar.prenume &&
                   email == voluntar.email &&
                   parola == voluntar.parola;
        }

        public override int GetHashCode()
        {
            int hashCode = 1063577040;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(nume);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(prenume);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(email);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(parola);
            return hashCode;
        }

        public override string ToString()
        {
            return "Id = " + this.Id + " , nume = " + this.GetFullName() + "  ,email = " +
                this.Email + "  ,parola = " + this.Parola;
        }

        public String GetFullName()
        {
            return this.Nume + " " + this.Prenume;

        }
    }
}
