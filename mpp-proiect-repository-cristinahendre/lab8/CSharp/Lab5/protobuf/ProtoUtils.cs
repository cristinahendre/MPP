using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Chat.Protocol;
using Network;
using proto=Chat.Protocol;

namespace protobuf
{
    static class ProtoUtils
    {
        public static ChatRequest createLoginRequest(VoluntarDTO user)
        {
            //new proto.User {Id = user.Id, Passwd = user.Password};
            proto.Voluntar userDTO = new proto.Voluntar { Email = user.Email, Passwd = user.Passwd };
            ChatRequest request = new ChatRequest { Type = ChatRequest.Types.Type.Login};
            request.Voluntar.Add(userDTO);

            return request;
        }

        public static ChatRequest createLogoutRequest(Cerinta1.Domain.Voluntar user)
        {
            proto.Voluntar userDTO = new proto.Voluntar { Id = user.Id, Nume=user.Nume,
                                      Email=user.Email, Passwd=user.Parola};
            ChatRequest request = new ChatRequest { Type = ChatRequest.Types.Type.Logout };
            request.Voluntar.Add(userDTO);
            return request;
        }
        public static ChatRequest GetNumeDonRequest(DonatorDTO message)
        {
            proto.Donator messageDTO = new proto.Donator
            {
                Nume = message.Nume
            };

            ChatRequest request = new ChatRequest { Type = ChatRequest.Types.Type.SearchDonator, Donator = messageDTO };
            return request;
        }


        public static ChatRequest SaveDonatieRequest(Cerinta1.Domain.Donatie don)
        {
            proto.CazCaritabil caz = new proto.CazCaritabil
            {
                Id = don.Caz.Id,
                Nume = don.Caz.Nume,
                Suma = don.Caz.Suma_donata
            };

            proto.Donator donator = new proto.Donator
            {
                Id = don.Donator.Id,
                Nume = don.Donator.Nume,
                Prenume = don.Donator.Prenume,
                Adresa = don.Donator.Adresa,
                Nr = don.Donator.NrTelefon
            };
            proto.Donatie userDTO = new proto.Donatie {
                Don=donator,
                Caz=caz,
                Suma=don.Suma_donata

            };
            ChatRequest request = new ChatRequest
            {
                Type = ChatRequest.Types.Type.SaveDonatie,
                Donatie = userDTO
            };
            return request;
        }


        public static ChatResponse createOkResponse()
        {
            ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.Ok };
            return response;
        }


        public static ChatResponse createErrorResponse(String text)
        {
            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.Error,
                Error = text
            };
            return response;
        }

        public static ChatResponse createOkResponse(Cerinta1.Domain.Voluntar user)
        {
            proto.Voluntar dto = new proto.Voluntar { 
                Id = user.Id,
                Nume = user.Nume,
                Prenume=user.Prenume,
                Email=user.Email,
                Passwd=user.Parola
            };
            ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.Ok
                };
            response.Voluntar.Add(dto);
            return response;
        }


        public static ChatResponse getDateDonResponse(Cerinta1.Domain.Donator user)
        {
            proto.Donator userDTO = new proto.Donator { Id = user.Id ,
                Nume=user.Nume,
                Prenume=user.Prenume,
                Adresa=user.Adresa,
                Nr=user.NrTelefon
            };
            ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.GetDonator
            };
            response.Donator.Add(userDTO);
            return response;
        }


        public static ChatResponse createLogoutResponse()
        {
            

            ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.Logout };
            return response;
        }

        public static ChatRequest createGetCazuriRequest()
        {


            ChatRequest response = new ChatRequest { 
                Type = ChatRequest.Types.Type.GetCazuri
            };
            return response;
        }

        public static ChatRequest createGetDonatoriAll()
        {


            ChatRequest response = new ChatRequest
            {
                Type = ChatRequest.Types.Type.GetDonatori
            };
            return response;
        }

        public static ChatResponse GetCazuriResponse(IEnumerable<Cerinta1.Domain.CazCaritabil> cazuri)
        {
            IList<proto.CazCaritabil> cazs = new List<proto.CazCaritabil>();
            foreach(Cerinta1.Domain.CazCaritabil c in cazuri)
            {
                proto.CazCaritabil ca = new proto.CazCaritabil
                {
                    Id = c.Id,
                    Nume = c.Nume,
                    Suma = c.Suma_donata
                };

                cazs.Add(ca);


            }

            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.GetCazuri,
                
            };
            response.Cazuri.Add(cazs);

            return response;

        }



        public static ChatResponse GetDonatoriResponse(IEnumerable<Cerinta1.Domain.Donator> cazuri)
        {
            IList<proto.Donator> cazs = new List<proto.Donator>();
            foreach (Cerinta1.Domain.Donator c in cazuri)
            {
                proto.Donator ca = new proto.Donator
                {
                    Id = c.Id,
                    Nume = c.Nume,
                    Prenume=c.Prenume,
                    Adresa=c.Adresa,
                    Nr=c.NrTelefon
                };

                cazs.Add(ca);


            }

            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.GetDonatori

            };
            response.Donator.Add(cazs);

            return response;

        }


        public static ChatResponse createRefreshResponse(Cerinta1.Domain.Donatie d)
        {
            proto.CazCaritabil caz = new proto.CazCaritabil
            {
                Id = d.Caz.Id,
                Nume = d.Caz.Nume,
                Suma = d.Caz.Suma_donata
            };

            proto.Donator donator= new proto.Donator
            {
                Id=d.Donator.Id,
                Nume=d.Donator.Nume,
                Prenume=d.Donator.Prenume,
                Adresa=d.Donator.Adresa,
                Nr=d.Donator.NrTelefon
            };


            proto.Donatie dona = new proto.Donatie
            {
                Caz = caz,
                Don = donator,
                Suma = d.Suma_donata
            };


            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.Refresh,
                Donatie=dona
            };
         
            return response;
        }

        public static String getError(ChatResponse response)
        {
            String errorMessage = response.Error;
            return errorMessage;
        }

        public static ChatResponse createSaveDonatieResponse()
        {

            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.NewDonatie
            };

            return response;
        }


        public static ChatResponse createSaveDonatorResponse()
        {

            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.NewDonator
            };

            return response;
        }

        public static VoluntarDTO getVoluntar(ChatRequest resp)
        {
            proto.Voluntar  vol = resp.Voluntar[0];
            VoluntarDTO dto = new VoluntarDTO(vol.Email, vol.Passwd);
            return dto;
        }

        public static Cerinta1.Domain.Voluntar getVoluntarBun(ChatRequest req)
        {
            proto.Voluntar vol = req.Voluntar[0];
            Cerinta1.Domain.Voluntar v = new Cerinta1.Domain.Voluntar(vol.Nume, vol.Prenume, vol.Email, vol.Passwd);
            v.Id = vol.Id;
            return v;


        }


        public static ChatRequest SaveDonatorRequest(DonatorDTO d)
        {
            proto.Donator dona = new proto.Donator
            {
                Nume = d.Nume,
                Prenume = d.Pre,
                Adresa = d.Adresa,
                Nr = d.Tel
            };

            ChatRequest request = new ChatRequest
            {
                Type = ChatRequest.Types.Type.SaveDonator,
                Donator = dona
            };
            return request;

        }

        public static ChatResponse createNullDonatorResp()
        {

            ChatResponse request = new ChatResponse
            {
                Type = ChatResponse.Types.Type.Unknown
              
            };
            return request;
        }

        public static Cerinta1.Domain.Donator getDonatorfromDTO(ChatRequest req)
        {
            proto.Donator dona = req.Donator;
            Cerinta1.Domain.Donator nou = new Cerinta1.Domain.Donator(dona.Nume, dona.Prenume,
                dona.Adresa, (long)dona.Nr);

            return nou;

        }




        public static DonatorDTO getDonator(ChatRequest response)
        {
            proto.Donator d = response.Donator;
            DonatorDTO don = new DonatorDTO(d.Nume);
            return don;
        }

        public static DonatorDTO getDTODonator(ChatRequest req)
        {
            proto.Donator d = req.Donator;
            DonatorDTO don = new DonatorDTO(d.Nume,d.Prenume,d.Adresa, (long)d.Nr);
            return don;

        }

        public static Cerinta1.Domain.Donatie getDonatie(ChatRequest request)
        {
            proto.Donatie d = request.Donatie;
            proto.CazCaritabil caz = d.Caz;
            Cerinta1.Domain.CazCaritabil c = new Cerinta1.Domain.CazCaritabil(caz.Nume,
                caz.Suma);
            c.Id = caz.Id;

            proto.Donator don = d.Don;
            Cerinta1.Domain.Donator d1= new Cerinta1.Domain.Donator(don.Nume, don.Prenume,
                don.Adresa, (long)don.Nr);
            d1.Id = don.Id;

            Cerinta1.Domain.Donatie donatia = new Cerinta1.Domain.Donatie(c, d.Suma, d1);
            return donatia;
        }


        public static Cerinta1.Domain.Donatie getDonatie(ChatResponse request)
        {
            proto.Donatie d = request.Donatie;
            proto.CazCaritabil caz = d.Caz;
            Cerinta1.Domain.CazCaritabil c = new Cerinta1.Domain.CazCaritabil(caz.Nume,
                caz.Suma);
            c.Id = caz.Id;

            proto.Donator don = d.Don;
            Cerinta1.Domain.Donator d1 = new Cerinta1.Domain.Donator(don.Nume, don.Prenume,
                don.Adresa, (long)don.Nr);
            d1.Id = don.Id;

            Cerinta1.Domain.Donatie donatia = new Cerinta1.Domain.Donatie(c, d.Suma, d1);
            return donatia;
        }


        public static ChatRequest GetDateDonRequest(DonatorDTO dona)
        {
            proto.Donator sender = new proto.Donator
            {
                Nume = dona.Nume,
                Prenume = dona.Pre,
                Adresa = dona.Adresa,
                Nr = dona.Tel
            };

            ChatRequest request = new ChatRequest
            {
                Type = ChatRequest.Types.Type.GetDateDonator,
                Donator = sender
            };
            return request;
        }



        public static ChatResponse GetNumeDonResponse(IEnumerable<Cerinta1.Domain.Donator> donators)
        {
            IList<proto.Donator> cazs = new List<proto.Donator>();
            foreach (Cerinta1.Domain.Donator c in donators)
            {
                proto.Donator ca = new proto.Donator
                {
                    Id = c.Id,
                    Nume = c.Nume,
                    Prenume = c.Prenume,
                    Adresa = c.Adresa,
                    Nr = c.NrTelefon
                };

                cazs.Add(ca);


            }

            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.GetNumeDon

            };
            response.Donator.Add(cazs);
            return response;
        }

        public static IEnumerable<Cerinta1.Domain.CazCaritabil> getCazuriProto(ChatResponse rep)
        {
            IList<Cerinta1.Domain.CazCaritabil> cazuri = new List<Cerinta1.Domain.CazCaritabil>();
            int i = 0;
            while(i!= rep.Cazuri.Count)
            {
                proto.CazCaritabil ca = rep.Cazuri[i];
                Cerinta1.Domain.
                    CazCaritabil c = new Cerinta1.Domain.CazCaritabil(ca.Nume, ca.Suma);
                c.Id = ca.Id;
                cazuri.Add(c);


                i++;
            }
            return cazuri;
        }


        public static IEnumerable<Cerinta1.Domain.Donator> getDonatoriProto(ChatResponse rep)
        {
            IList<Cerinta1.Domain.Donator> cazuri = new List<Cerinta1.Domain.Donator>();
            int i = 0;
            while (i != rep.Donator.Count)
            {
                proto.Donator ca = rep.Donator[i];
                Cerinta1.Domain.
                    Donator c = new Cerinta1.Domain.Donator(ca.Nume, ca.Prenume,ca.Adresa,
                    (long)ca.Nr);
                c.Id = ca.Id;
                cazuri.Add(c);


                i++;
            }
            return cazuri;
        }

    }
}
