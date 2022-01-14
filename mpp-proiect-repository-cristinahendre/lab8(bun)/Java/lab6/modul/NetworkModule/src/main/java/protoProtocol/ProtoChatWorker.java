package protoProtocol;

import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Voluntar;
import Service.IObserver;
import Service.IService;
import Service.ServiceException;
import dto.DonatorDTO;
import dto.VoluntarDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ProtoChatWorker implements Runnable, IObserver {
    private IService server;
     private Socket connection;

     private InputStream input;
     private OutputStream output;
     private volatile boolean connected;
     public ProtoChatWorker(IService server, Socket connection) {
         this.server = server;
         this.connection = connection;
         try{
             output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
             input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
             connected=true;
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void run() {
         while(connected){
             try {
                // Object request=input.readObject();
                 System.out.println("Waiting requests ...");
                protoProtocol.ChatProtobufs.ChatRequest request= ChatProtobufs.ChatRequest.parseDelimitedFrom(input);
                 System.out.println("RequestObject received: "+request);
                 protoProtocol.ChatProtobufs.ChatResponse response=handleRequest(request);
                 if (response!=null){
                    sendResponse(response);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         try {
             input.close();
             output.close();
             connection.close();
         } catch (IOException e) {
             System.out.println("Error "+e);
         }
     }

//     public void messageReceived(Message message) throws ChatException {
//         System.out.println("Message received  "+message);
//         try {
//             sendResponse(ProtoUtils.createNewMessageResponse(message));
//         } catch (IOException e) {
//             throw new ChatException("Sending error: "+e);
//         }
//     }

//     public void friendLoggedIn(User friend) throws ChatException {
//         System.out.println("Friend logged in "+friend);
//         try {
//             sendResponse(ProtoUtils.createFriendLoggedInResponse(friend));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

     public void friendLoggedOut(Voluntar friend) throws ServiceException {
//         System.out.println("Friend logged out "+friend);
//         try {
//             sendResponse(ProtoUtils.createFriendLoggedOutResponse(friend));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
     }

     private protoProtocol.ChatProtobufs.ChatResponse handleRequest(protoProtocol.ChatProtobufs.ChatRequest request){
         protoProtocol.ChatProtobufs.ChatResponse response=null;
         switch (request.getType()){
             case Login:{
                 System.out.println("Login request ...");
                 VoluntarDTO user=ProtoUtils.getVoluntar(request);
                 System.out.println("am primit dto: "+user);

                 Voluntar don = null;
                 try {
                     don = server.login(user.getEmail(), user.getPasswd(), this);
                     System.out.println("worker( login ): am gasit: "+don);
                     return ProtoUtils.createOkResponse(don);
                 } catch (ServiceException e) {
                     e.printStackTrace();
                 }


             }
             case Logout:{
                 System.out.println("Logout request");
                 Voluntar user=ProtoUtils.getVoluntarBun(request);
                 try {
                     server.logout(user, this);
                     connected=false;
                     return ProtoUtils.createLogoutResponse();

                 } catch (ServiceException e) {
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 }
             }
             case GetCazuri:{
               System.out.println("get cazuri request ...");

               Iterable<CazCaritabil> cazs = server.getCazuri();
               System.out.println("worker(login): am gasit: "+cazs);
               return ProtoUtils.GetCazuriResponse(cazs);

             }
             case GetDonatori:{
                 System.out.println("get donatori request ...");

                 Iterable<Donator> cazs = server.getDonatori();
                 System.out.println("worker(get donatori): am gasit: "+cazs);
                 return ProtoUtils.GetDonatoriResponse(cazs);
             }
             case SearchDonator:{
                 System.out.println("cauta donator dupa nume request ...");
                 DonatorDTO dto=ProtoUtils.getDonator(request);
                 Iterable<Donator> cazs = server.getDonatorDupaNume(dto.getNume());
                 System.out.println("worker(get donatori): am gasit: "+cazs);
                 return ProtoUtils.GetNumeDonResponse(cazs);
             }
             case GetDateDonator:{
                 System.out.println("get donator dupa date request ...");
                 DonatorDTO dto=ProtoUtils.getDTODonator(request);
                 System.out.println("am primit dto: "+dto);
                 Donator d=server.getDonatorDupaDate(dto.getNume(), dto.getPrenume(),
                         dto.getAdresa(),dto.getTel());
                 System.out.println("worker(get donatori): am gasit: "+d);
                 if(d!=null)
                    return ProtoUtils.getDateDonResponse(d);
                 else ProtoUtils.createNullDonatorResp();
             }
             case SaveDonatie:{
                 System.out.println("save donatie request...");
                 Donatie dto=ProtoUtils.getDonatie(request);
                 System.out.println("am primit donatie de salvat: "+dto);
                 server.saveDonatie(dto,this);

                 return ProtoUtils.createSaveDonatieResponse();

             }
             case SaveDonator:{
                 System.out.println("save donator request...");
                 Donator dto=ProtoUtils.getDonatorfromDTO(request);
                 System.out.println("am primit donator de salvat: "+dto);
                 server.saveDonator(dto.getNume(),dto.getPrenume(),dto.getAdresa(),
                         dto.getNrTelefon());

                 return ProtoUtils.createSaveDonatorResponse();

             }
         }
         return response;
     }

     private void sendResponse(protoProtocol.ChatProtobufs.ChatResponse response) throws IOException{
         System.out.println("sending response "+response);
         response.writeDelimitedTo(output);
         //output.writeObject(response);
         output.flush();
     }

    @Override
    public void refreshCazuri(Donatie don) throws ServiceException {

        System.out.println("Refresh pe worker! "+don);
        try {
            sendResponse(ProtoUtils.createRefreshResponse(don));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
