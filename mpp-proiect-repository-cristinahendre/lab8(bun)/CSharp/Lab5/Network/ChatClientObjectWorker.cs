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

namespace Cerinta1.networking
{
    public class ChatClientWorker: IObserver
    {
		private IService server;
		private TcpClient connection;

		private NetworkStream stream;
		private IFormatter formatter;
		private volatile bool connected;
		public ChatClientWorker(IService server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{

				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				connected = true;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void run()
		{
			while (connected)
			{
				try
				{
					object request = formatter.Deserialize(stream);
					object response = handleRequest((Request)request);
					if (response != null)
					{
						sendResponse((Response)response);
					}
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}

				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}
			}
			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				Console.WriteLine("Error " + e);
			}
		}
		public virtual void refreshCazuri(Donatie don)
		{

			
			Console.WriteLine("Donatie received  " + don);
			try
			{
				sendResponse(new Refresh(don));
			}
			catch (Exception e)
			{
				throw new ServiceException("Sending error: " + e);
			}
		}

		

		private Response handleRequest(Request request)
		{
			Response response = null;
			if (request is LoginRequestObject)
			{
				Console.WriteLine("Login request ...");
				LoginRequestObject logReq = (LoginRequestObject)request;
				VoluntarDTO dto = logReq.Data;
                Console.WriteLine("obj worker: received: "+dto);
				
				
				Voluntar vol;
				lock (server)
					{
                        Console.WriteLine("server locked in worker.");

					    vol = server.login(dto.Email, dto.Passwd, this);
				}
				
				
				return new LoginResponse(vol);





			}

			if (request is getNumeDonatorRequest)
			{
				Console.WriteLine("get nume donatori Request ...");
				getNumeDonatorRequest getReq = (getNumeDonatorRequest)request;
				DonatorDTO dto = getReq.Data;
                Console.WriteLine("(worker): received(get nume don): "+dto.ToString());

				try
				{
					IEnumerable<Donator> donatori;
					lock (server)
					{
                        Console.WriteLine("caut dupa :" +dto.Nume);
						donatori = server.getDonatorDupaNume(dto.Nume);
					}
                    Console.WriteLine("in worker, am gasit: ");
					foreach (Donator d in donatori)
					{
						Console.WriteLine(d);
					}
					//return new GetNumeDonResponse(donatori);
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}


			if(request is saveDonatorRequest)
            {
				Console.WriteLine("saaave donator Request ...");
				saveDonatorRequest getReq = (saveDonatorRequest)request;
				DonatorDTO dto = getReq.getDonator;
				Console.WriteLine("(worker): received(get nume don): " + dto.ToString());

				try
				{
					
					lock (server)
					{

						server.saveDonator(dto.Nume, dto.Pre, dto.Adresa, dto.Tel);
                        Console.WriteLine("am salvaaat..");
					}

					return new SaveDonatorResponse();
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}



			if (request is getDateDonRequest)
			{
				Console.WriteLine("get 1 donator Request ...");
				getDateDonRequest getReq = (getDateDonRequest)request;
				DonatorDTO dto = getReq.Data;
				Console.WriteLine("(worker): received(get nume don): " + dto.ToString());

				try
				{
					Donator donator;
					lock (server)
					{
						Console.WriteLine("caut dupa :" + dto.Nume);
						donator = server.getDonatorDupaDate(dto.Nume, dto.Pre, dto.Adresa, dto.Tel);
					}
					Console.WriteLine("in worker, am gasit: ");

					return new GetDateDonResponse(donator);
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}




			if (request is LogoutRequest)
			{
				Console.WriteLine("Logout request");
				LogoutRequest logReq = (LogoutRequest)request;
				Voluntar user = logReq.User;
				
				try
				{
					lock (server)
					{
                        Console.WriteLine("worker: send to logout: "+user);
						server.logout(user, this);
					}
					connected = false;
					return new OkResponse();

				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}


			if (request is findCazRequest)
			{
				Console.WriteLine("find caz request ... ");
				findCazRequest logReq = (findCazRequest)request;
				CazCaritabilDTO user = logReq.getDTO;

				try
				{
					CazCaritabil caz;
					lock (server)
					{
						Console.WriteLine("worker: send to find caz: " + user.Id);
						caz = server.findCaz(user.Id);
					}
                    Console.WriteLine("worker: found caz: "+caz.Nume);
					return new findCazResponse(caz);

				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}



			if (request is saveDonatieRequest)
			{
				Console.WriteLine("save donatie request ... ");
				saveDonatieRequest logReq = (saveDonatieRequest)request;
				Donatie user = logReq.User;

				try
				{
					
					lock (server)
					{
						Console.WriteLine("worker: save donatia " + user.Suma_donata);
						server.saveDonatie(user,this);
					}

					return new NewDonatieResponse();

				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}





			if (request is getCazuriRequest)
			{
				Console.WriteLine("Get Cazuri...");
				getCazuriRequest getReq = (getCazuriRequest)request;
			    Console.WriteLine("am primit request:(worker) " +getReq);
				
				try
				{
					IEnumerable<CazCaritabilDTO> cazs;
					lock (server)
					{
                        Console.WriteLine("server locked in worker-get cazuri");
						IEnumerable<CazCaritabil> gasite = server.getCazuri();
                        
						Console.WriteLine("cazuri gasite in worker "+gasite);
						//cazs =DTOUtils.createCazDTO2(gasite);

						
					}
					
					//Console.WriteLine("worker: sending cazuri back: " + cazs);
					/*foreach(CazCaritabilDTO caz in cazs)
                    {
                        Console.WriteLine(caz.ToString());
                    }*/
					//return new GetCazuriResponse(cazs);


				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			if (request is getDonatoriRequest)
			{
				Console.WriteLine("get donatori Request ...");
				getDonatoriRequest getReq = (getDonatoriRequest)request;
				
				try
				{
					IEnumerable<Donator> donatori;
					lock (server)
					{

						donatori = server.getDonatori();
					}
                    Console.WriteLine("returnez din worker nume: ");

					
					//return new GetDonatoriResponse(donatori);
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			return response;
		}

		private void sendResponse(Response response)
		{
			//lock
			Console.WriteLine("sending response(worker) " + response.ToString());
			lock (stream)
			{
				formatter.Serialize(stream, response);
				stream.Flush();
			}
		}

       
    }
}

