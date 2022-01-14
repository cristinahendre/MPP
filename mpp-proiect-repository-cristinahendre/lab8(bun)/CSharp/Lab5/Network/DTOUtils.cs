using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Network;

namespace Cerinta1.networking
{
	using static Voluntar;
	using  static Donatie;

	[Serializable]
	public class DTOUtils
	{
		public static Voluntar getFromDTO(VoluntarDTO usdto)
		{
			string email = usdto.Email;
			string pass = usdto.Passwd;
			string nume = usdto.Nume;
			string prenume = usdto.Prenume;
			return new Voluntar(nume,prenume,email, pass);

		}
		public static VoluntarDTO getDTO(Voluntar user)
		{
			string email = user.Email;
			string pass = user.Parola;
			return new VoluntarDTO(email, pass);
		}

		


		public static VoluntarDTO createDTO(string e ,string p)
        {
			return new VoluntarDTO(e, p);
        }

		public static CazCaritabil[] createCazDTO(IEnumerable<CazCaritabil> cazuri)
        {
			CazCaritabil[] deReturnat = new CazCaritabil[cazuri.Count()];
			foreach(CazCaritabil caz in cazuri){
				deReturnat.Append(caz);
            }
            Console.WriteLine("caz dto: returnez "+deReturnat);
			return deReturnat;
        }

		public static IEnumerable<CazCaritabil> getCazDTO(CazCaritabil[] cazuri)
        {
			IList<CazCaritabil> deReturnat = new List<CazCaritabil>();
			foreach(CazCaritabil caz in cazuri)
            {
				deReturnat.Add(caz);
            }
			IEnumerable<CazCaritabil> calculat = deReturnat;
            Console.WriteLine("caz dto: am calculat in enumerable "+calculat);
			return calculat;

        }

		public static CazCaritabilDTO getOneCaztoDTO(CazCaritabil c)
        {
			return new CazCaritabilDTO(c.Id, c.Nume, c.Suma_donata);
        }

		public static CazCaritabil getOneCazfromDTO(CazCaritabilDTO c)
        {
			CazCaritabil caz = new CazCaritabil(c.Nume, c.Suma);
			caz.Id = c.Id;
			return caz;

        }

		public static CazCaritabilDTO[] createCazDTO2(CazCaritabil[] cazuri)
		{
			
			CazCaritabilDTO[] deReturnat =new CazCaritabilDTO[cazuri.Count()];
			int i = 0;
			foreach (CazCaritabil caz in cazuri)
			{
				deReturnat[i] =getOneCaztoDTO(caz);
				i++;
			}
			
			Console.WriteLine("caz dto: returnez " + deReturnat);
			return deReturnat;
			
		}

		public static CazCaritabil[] getCazDTO2(CazCaritabilDTO[] cazuri)
		{
			CazCaritabil[] deReturnat = new CazCaritabil[cazuri.Length];
			int i = 0;
			foreach (CazCaritabilDTO caz in cazuri)
			{
				deReturnat[i]=getOneCazfromDTO(caz);
				i++;
			}
			
			Console.WriteLine("caz dto: am calculat in enumerable " + deReturnat);
			return deReturnat;

		}

	}	
}
