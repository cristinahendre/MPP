using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Network;

namespace Cerinta1.networking
{

	public interface Response
	{
	}

	public interface UpdateResponse : Response
	{
	}

	[Serializable]
	public class OkResponse : Response
	{

	}

	[Serializable]
	public class ErrorResponse : Response
	{
		private string message;

		public ErrorResponse(string message)
		{
			this.message = message;
		}

		public virtual string Message
		{
			get
			{
				return message;
			}
		}
	}



	[Serializable]
	public class findCazResponse : Response
	{
		private CazCaritabil caz;

		public findCazResponse(CazCaritabil c)
		{
			this.caz = c;
		}

		public virtual CazCaritabil getCaz
		{
			get
			{
				return caz;
			}
		}
	}



	[Serializable]
	public class NewDonatieResponse : Response
	{
		

		public NewDonatieResponse()
		{
		
		}

	
	}


	[Serializable]
	public class LoginResponse : Response
	{

		private Voluntar v;

		public LoginResponse(Voluntar don)
		{

			this.v = don;
			Console.WriteLine("login response: " + v);
		}

		public virtual Voluntar getVoluntar
		{
			get
			{
				Console.WriteLine("la getter(login response)   " + v);
				return v;
			}
		}
	}


		[Serializable]
		public class GetDateDonResponse : Response
		{

			private Donator v;

			public GetDateDonResponse(Donator don)
			{

				this.v = don;
				Console.WriteLine("login response: " + v);
			}

			public virtual Donator getDonator
			{
				get
				{
					
					return v;
				}
			}

			public virtual String toString()
        {
			return v.ToString();
        }
	}


	[Serializable]
	public class GetNumeDonResponse : Response
	{
		private Donator[] message;

		public GetNumeDonResponse(Donator[] message)
		{
			this.message = message;
		}

		public virtual Donator[] getDonatori
		{
			get
			{
				return message;
			}
		}
	}



	[Serializable]
	public class SaveDonatorResponse : Response
	{
		

		public SaveDonatorResponse()
		{
		}

		
	}

	[Serializable]
	public class GetCazuriResponse : Response
	{
		private CazCaritabilDTO[] cazuri;

		public GetCazuriResponse(CazCaritabilDTO[] friend)
		{
			this.cazuri = friend;
		}

		public virtual CazCaritabilDTO[] getCazuri
		{
			get
			{
				return cazuri;
			}
		}
	}

	[Serializable]
	public class Refresh : UpdateResponse
	{
		private Donatie don;

		public Refresh(Donatie message)
		{
			this.don = message;
		}

		public virtual Donatie getRefresh
		{
			get
			{
				return don;
			}
		}
	}


	[Serializable]
	public class GetDonatoriResponse : Response
	{
		private Donator[] message;

		public GetDonatoriResponse(Donator[]  message)
		{
			this.message = message;
		}

		public virtual Donator[] getDonatori
		{
			get
			{
				return message;
			}
		}
	}
}
