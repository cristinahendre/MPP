using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Cerinta1.Service;
using Network;

namespace Cerinta1.networking.obj
{
    public class ChatServerObjectProxy: IService
    {
		private string host;
		private int port;

		private IObserver client;

		private NetworkStream stream;

		private IFormatter formatter;
		private TcpClient connection;

		private Queue<Response> responses;
		private volatile bool finished;
		private EventWaitHandle _waitHandle;
		public ChatServerObjectProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses = new Queue<Response>();
		}

		public virtual Voluntar login(String n, String p, IObserver client)
		{
			initializeConnection();

            Console.WriteLine("proxy:login" );
			VoluntarDTO vl = DTOUtils.createDTO(n, p);
			sendRequest(new LoginRequestObject(vl));
            Console.WriteLine("proxy sent request login "+vl);
			
				Response resp = readResponse();
                Console.WriteLine("proxy: read request");
				if (resp is LoginResponse)
				{
					this.client = client;
					LoginResponse response = (LoginResponse)resp;
                   // Console.WriteLine("proxy: return voluntar: "+response.getVoluntar);
					return response.getVoluntar;
				}
				if(resp is ErrorResponse)
                {
					ErrorResponse err = (ErrorResponse)resp;
					throw new ServiceException(err.Message);
				}
			return null;
		}
		

			
		
		

	
		public virtual void logout(Voluntar user, IObserver client)
		{
            Console.WriteLine("send to logout user: "+user);
			sendRequest(new LogoutRequest(user));
			Response response = readResponse();
			closeConnection();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new ServiceException(err.Message);
			}
		}

		public virtual CazCaritabil findCaz(int id)
		{

			CazCaritabilDTO caz = new CazCaritabilDTO(id);
			sendRequest(new findCazRequest(caz));
			Response response = readResponse();
		
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new ServiceException(err.Message);
			}
			findCazResponse resp = (findCazResponse)response;
			CazCaritabil gasit = resp.getCaz;
			return gasit;
		}





		private void closeConnection()
		{
			finished = true;
			try
			{
				stream.Close();
				//output.close();
				connection.Close();
				_waitHandle.Close();
				client = null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
				formatter.Serialize(stream, request);
				stream.Flush();
			}
			catch (Exception e)
			{

				throw new ServiceException("Error sending object " + e.Message);
			}

		}

		private Response readResponse()
		{
			Response response = null;
			try
			{
				_waitHandle.WaitOne();
				lock (responses)
				{
					//Monitor.Wait(responses); 
					response = responses.Dequeue();

				}


			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		private void initializeConnection()
		{
			try
			{
				connection = new TcpClient(host, port);
				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				finished = false;
				_waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw = new Thread(run);
			tw.Start();
		}


		private void handleUpdate(UpdateResponse update)
		{
			if (update is Refresh)
			{


				Refresh frUpd = (Refresh)update;
				Donatie friend = frUpd.getRefresh;
				Console.WriteLine("proxy update " + friend);
				try
				{
					client.refreshCazuri(friend);
				}
				catch (ServiceException e)
				{
					Console.WriteLine(e.StackTrace);
				}
			}
			

			
		}
		public virtual void run()
		{
			while (!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("response received " + response);
					if (response is UpdateResponse)
					{
						handleUpdate((UpdateResponse)response);
					}
					else
					{

						lock (responses)
						{


							responses.Enqueue((Response)response);

						}
						_waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error " + e);
				}

			}
		}

     

        public Voluntar getVoluntarDupaDate(string e, string p)
        {
            throw new NotImplementedException();
        }

        public virtual  void saveDonatie(Donatie don, IObserver client)
        {
			
			sendRequest(new saveDonatieRequest(don));
			Response response = readResponse();

			if (response is NewDonatieResponse)
			{
				NewDonatieResponse resp = (NewDonatieResponse)response;
				
			}
		
		}

        public virtual Donator getDonatorDupaDate(string n, string p, string ad, long tel)
        {
			DonatorDTO dto = new DonatorDTO(n,p,ad,tel);
			sendRequest(new getDateDonRequest(dto));
			Response response = readResponse();

			if (response is GetDateDonResponse)
			{
				GetDateDonResponse resp = (GetDateDonResponse)response;
				Donator don = resp.getDonator;
				return don;

			}
			return null;
		}

        public virtual void saveDonator(string n, string p, string ad, long tel)
        {
			DonatorDTO dto = new DonatorDTO(n, p, ad, tel);
			sendRequest(new saveDonatorRequest(dto));
			Response response = readResponse();

			if (response is SaveDonatorResponse)
			{
				SaveDonatorResponse resp = (SaveDonatorResponse)response;
				

			}
			
		}

        public virtual IEnumerable<CazCaritabil>  getCazuri()
        {
			sendRequest(new getCazuriRequest());
			Response response = readResponse();
            Console.WriteLine("(proxy-getCazuri) -read ");
			
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new ServiceException(err.Message);
			}
		
			if(response is GetCazuriResponse)
            {
				GetCazuriResponse resp = (GetCazuriResponse)response;
				CazCaritabilDTO[] caz_aux = resp.getCazuri;
				CazCaritabil[] cazuri = DTOUtils.getCazDTO2(caz_aux);
				Console.WriteLine("get cazuri :" + cazuri);
				return cazuri;
			}
			
			return null;
			
		}

        public virtual IEnumerable<Donator> getDonatori()
        {
			sendRequest(new getDonatoriRequest());
			Response response = readResponse();
		
			if (response is GetDonatoriResponse)
			{
				GetDonatoriResponse resp = (GetDonatoriResponse)response;
				Donator[] cazuri = resp.getDonatori;
				return cazuri;

			}
			return null;
		}

        public virtual IEnumerable<Donator> getDonatorDupaNume(string n)
        {
			DonatorDTO dto = new DonatorDTO(n);
			sendRequest(new getNumeDonatorRequest(dto));
			Response response = readResponse();

			if (response is GetNumeDonResponse)
			{
				GetNumeDonResponse resp = (GetNumeDonResponse)response;
				Donator[] cazuri = resp.getDonatori;
				return cazuri;

			}
			return null;
		}
        //}
    }

}
