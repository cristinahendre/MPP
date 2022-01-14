using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Network;

namespace Cerinta1.networking
{

	
	public interface Request
	{
	}


	[Serializable]
	public class LoginRequestObject : Request
	{
		private VoluntarDTO vol;

		public LoginRequestObject(VoluntarDTO v)
		{
			this.vol = v;
		}

		public virtual VoluntarDTO Data
		{
			get
			{
				return vol;
			}
		}

		
	}

	[Serializable]
	public class LogoutRequest : Request
	{
		private Voluntar user;

		public LogoutRequest(Voluntar user)
		{
			this.user = user;
		}

		public virtual Voluntar User
		{
			get
			{
				return user;
			}
		}
	}

	[Serializable]
	public class saveDonatorRequest : Request
	{
		private DonatorDTO don;

		public saveDonatorRequest(DonatorDTO message)
		{
			this.don = message;
		}

		public virtual DonatorDTO getDonator
		{
			get
			{
				return don;
			}
		}
	}

	[Serializable]
	public class saveDonatieRequest : Request
	{
		private Donatie donatie;

		public saveDonatieRequest(Donatie user)
		{
			this.donatie = user;
		}

		public virtual Donatie User
		{
			get
			{
				return donatie;
			}
		}
	}

	[Serializable]
	public class getCazuriRequest : Request
	{
		public getCazuriRequest()
        {
		
        }
		
		

		
	}

	[Serializable]
	public class getDonatoriRequest : Request
	{

		


	}

	[Serializable]
	public class getNumeDonatorRequest: Request
    {
		private DonatorDTO vol;

		public getNumeDonatorRequest(DonatorDTO v)
		{
			this.vol = v;
		}

		public virtual DonatorDTO Data
		{
			get
			{
				return vol;
			}
		}
	}



	[Serializable]
	public class findCazRequest : Request
	{
		private CazCaritabilDTO id;

		public findCazRequest(CazCaritabilDTO v)
		{
			this.id = v;
		}

		public virtual CazCaritabilDTO getDTO
		{
			get
			{
				return id;
			}
		}
	}





	[Serializable]
	public class getDateDonRequest : Request
	{
		private DonatorDTO vol;

		public getDateDonRequest(DonatorDTO v)
		{
			this.vol = v;
		}

		public virtual DonatorDTO Data
		{
			get
			{
				return vol;
			}
		}
	}

}
