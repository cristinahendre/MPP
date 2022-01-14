using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Cerinta1.Service;
using Chat.Protocol;
using Google.Protobuf;
using Network;

namespace protobuf
{
	public class ProtoChatWorker : IObserver
	{
		private IService server;
		private TcpClient connection;

		private NetworkStream stream;
		private volatile bool connected;
		public ProtoChatWorker(IService server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{

				stream = connection.GetStream();
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

					ChatRequest request = ChatRequest.Parser.ParseDelimitedFrom(stream);
					ChatResponse response = handleRequest(request);
					if (response != null)
					{
						sendResponse(response);
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
		
		public virtual void refreshCazuri(Cerinta1.Domain.Donatie d)
		{
			Console.WriteLine("refresh pe worker cu " + d);
			try
			{
				sendResponse(ProtoUtils.createRefreshResponse(d));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		private ChatResponse handleRequest(ChatRequest request)
		{
			ChatResponse response = null;
			ChatRequest.Types.Type reqType = request.Type;
			switch (reqType)
			{
				case ChatRequest.Types.Type.Login:
					{
						Console.WriteLine("Login request ...");
						VoluntarDTO user = ProtoUtils.getVoluntar(request);
						try
						{
							Cerinta1.Domain.Voluntar v = null;
							lock (server)
							{
								v = server.login(user.Email,user.Passwd, this);
							}
							return ProtoUtils.createOkResponse(v);
						}
						catch (ServiceException e)
						{
							connected = false;
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
				case ChatRequest.Types.Type.Logout:
					{
						Console.WriteLine("Logout request");
						Cerinta1.Domain.Voluntar user = ProtoUtils.getVoluntarBun(request);
						try
						{
							lock (server)
							{

								server.logout(user, this);
							}
							connected = false;
							return ProtoUtils.createLogoutResponse();

						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
				case ChatRequest.Types.Type.GetCazuri:
					{
						Console.WriteLine("get all cazuri ...");
						try
						{
							IEnumerable<Cerinta1.Domain.CazCaritabil> cazuri;
							lock (server)
							{
								cazuri = server.getCazuri();
							}
                            Console.WriteLine("found cazuri: "+cazuri);
							return ProtoUtils.GetCazuriResponse(cazuri);
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case ChatRequest.Types.Type.GetDonatori:
					{
						Console.WriteLine("get all donators ...");
						try
						{
							IEnumerable<Cerinta1.Domain.Donator> cazuri;
							lock (server)
							{
								cazuri = server.getDonatori();
							}
							Console.WriteLine("found donatorii: " + cazuri);
							return ProtoUtils.GetDonatoriResponse(cazuri);
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case ChatRequest.Types.Type.SearchDonator:
					{
						Console.WriteLine("search donator ...");
						DonatorDTO user = ProtoUtils.getDonator(request);  //DTOUtils.getFromDTO(udto);
						try
						{
							IEnumerable<Cerinta1.Domain.Donator> dons;
							lock (server)
							{

								dons = server.getDonatorDupaNume(user.Nume);
							}
							return ProtoUtils.GetNumeDonResponse(dons);
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}


				case ChatRequest.Types.Type.GetDateDonator:
					{
						Console.WriteLine("get date donator ...");
						DonatorDTO user = ProtoUtils.getDTODonator(request);  //DTOUtils.getFromDTO(udto);
						try
						{
							Cerinta1.Domain.Donator d;
							lock (server)
							{

								d = server.getDonatorDupaDate(user.Nume, user.Pre,
									user.Adresa, user.Tel);
							}
                            Console.WriteLine("found donator: "+d);
							if (d != null)
								return ProtoUtils.getDateDonResponse(d);
							else return ProtoUtils.createNullDonatorResp();
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case ChatRequest.Types.Type.SaveDonatie:
					{
						Console.WriteLine("save donatie ...");
						Cerinta1.Domain.Donatie d = ProtoUtils.getDonatie(request);  //DTOUtils.getFromDTO(udto);
						try
						{
							
							lock (server)
							{

								server.saveDonatie(d,this);
							}
							return ProtoUtils.createSaveDonatieResponse();
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}


				case ChatRequest.Types.Type.SaveDonator:
					{
						Console.WriteLine("save donator ...");
						Cerinta1.Domain.Donator d = ProtoUtils.getDonatorfromDTO(request);
                        Console.WriteLine("am primit dto: "+d);
						try
						{

							lock (server)
							{

								server.saveDonator(d.Nume, d.Prenume, d.Adresa, d.NrTelefon);
							}
							return ProtoUtils.createSaveDonatorResponse();
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
			}
			return response;
		}

		private void sendResponse(ChatResponse response)
		{
			Console.WriteLine("sending response " + response);
			lock (stream)
			{
				response.WriteDelimitedTo(stream);
				stream.Flush();
			}

		}
	}

}
