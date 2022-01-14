using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Network
{
	[Serializable]
	public class VoluntarDTO
	{
		private string email;
		private string passwd;
		private string nume;
		private string prenume;

		

		public VoluntarDTO(string email, string passwd)
		{
			this.email = email;
			this.passwd = passwd;
		}

		public VoluntarDTO(string nume ,string prenume, string email, string passwd)
		{
			this.email = email;
			this.passwd = passwd;
			this.nume = nume;
			this.prenume = prenume;
		}

		public virtual string Email
		{
			get
			{
				return email;
			}
			set
			{
				this.email = value;
			}
		}


		public virtual string Nume
		{
			get
			{
				return nume;
			}
			set
			{
				this.nume = value;
			}
		}

		public virtual string Prenume
		{
			get
			{
				return prenume;
			}
			set
			{
				this.prenume = value;
			}
		}



		public virtual string Passwd
		{
			get
			{
				return passwd;
			}
            set
            {
				this.passwd = value;
            }
		}
	}
}
