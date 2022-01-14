using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain
{
    class Donatie: Entity<int>
    {
        CazCaritabil caz;
        int suma_donata;
        Donator donator;

        public Donatie(CazCaritabil caz, int suma_donata, Donator donator)
        {
            this.caz = caz;
            this.suma_donata = suma_donata;
            this.donator = donator;
        }

        public int Suma_donata { get => suma_donata; set => suma_donata = value; }
        internal CazCaritabil Caz { get => caz; set => caz = value; }
        internal Donator Donator { get => donator; set => donator = value; }

        public override bool Equals(object obj)
        {
            return obj is Donatie donatie &&
                   EqualityComparer<CazCaritabil>.Default.Equals(caz, donatie.caz) &&
                   suma_donata == donatie.suma_donata &&
                   EqualityComparer<Donator>.Default.Equals(donator, donatie.donator);
        }

        public override int GetHashCode()
        {
            int hashCode = -1413849246;
            hashCode = hashCode * -1521134295 + EqualityComparer<CazCaritabil>.Default.GetHashCode(caz);
            hashCode = hashCode * -1521134295 + suma_donata.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<Donator>.Default.GetHashCode(donator);
            return hashCode;
        }

        public override string ToString()
        {
            return "Donatia cu id = " + this.Id + " , la cazul cu numele  = " + this.Caz.Nume +
                "  , donator = " + this.Donator.GetFullName() + " , suma donata = " + this.Suma_donata;
        }
    }
}
