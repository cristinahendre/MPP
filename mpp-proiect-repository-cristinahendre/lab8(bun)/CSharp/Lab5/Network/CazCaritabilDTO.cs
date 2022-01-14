using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Network
{
    [Serializable]
    public class CazCaritabilDTO
    {
        private int id;
        private string nume;
        private int suma;

        public CazCaritabilDTO(int id)
        {
            this.id = id;
        }

        public CazCaritabilDTO(int id, string nume, int suma)
        {
            this.id = id;
            this.Nume = nume;
            this.suma = suma;
        }

        public virtual int Suma { get => suma; set => suma = value; }
        public virtual string Nume { get => nume; set => nume = value; }
        public virtual int Id { get => id; set => id = value; }

        public override bool Equals(object obj)
        {
            return base.Equals(obj);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override string ToString()
        {
            return "id = " + this.Id + "  nume = " + this.Nume + "  suma = " + this.suma;
        }
    }
}
