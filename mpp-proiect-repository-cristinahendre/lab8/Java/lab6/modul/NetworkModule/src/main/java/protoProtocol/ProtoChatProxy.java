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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoChatProxy implements IService {
    private String host;
      private int port;

      private IObserver client;

      private InputStream input;
      private OutputStream output;
      private Socket connection;

      private BlockingQueue<ChatProtobufs.ChatResponse> qresponses;
      private volatile boolean finished;
      public ProtoChatProxy(String host, int port) {
          this.host = host;
          this.port = port;
          qresponses=new LinkedBlockingQueue<ChatProtobufs.ChatResponse>();
      }


//    public void sendMessage(Message message) throws ChatException {
//        sendRequest(ProtoUtils.createSendMesssageRequest(message));
//        ChatProtobufs.ChatResponse response=readResponse();
//        if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
//            String errorText=ProtoUtils.getError(response);
//            throw new ChatException(errorText);
//        }
//    }
//
//    public void logout(User user, IChatObserver client) throws ChatException {
//        sendRequest(ProtoUtils.createLogoutRequest(user));
//        ChatProtobufs.ChatResponse response=readResponse();
//        closeConnection();
//        if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
//            String errorText=ProtoUtils.getError(response);
//            throw new ChatException(errorText);
//        }
//    }
//
//    public User[] getLoggedFriends(User user) throws ChatException {
//        sendRequest(ProtoUtils.createLoggedFriendsRequest(user));
//        ChatProtobufs.ChatResponse response=readResponse();
//        if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
//            String errorText=ProtoUtils.getError(response);
//            throw new ChatException(errorText);
//        }
//        User[] friends=ProtoUtils.getFriends(response);
//        return friends;
//    }

      private void closeConnection() {
          finished=true;
          try {
              input.close();
              output.close();
              connection.close();
              client=null;
          } catch (IOException e) {
              e.printStackTrace();
          }

      }

      private void sendRequest(ChatProtobufs.ChatRequest request)throws ServiceException{
          try {
              System.out.println("Sending request ..."+request);
              //request.writeTo(output);
              request.writeDelimitedTo(output);
              output.flush();
              System.out.println("RequestObject sent.");
          } catch (IOException e) {
              throw new ServiceException("Error sending object "+e);
          }

      }

      private ChatProtobufs.ChatResponse readResponse() throws ServiceException{
          ChatProtobufs.ChatResponse response=null;
          try{
              response=qresponses.take();

          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          return response;
      }
      private void initializeConnection() throws ServiceException {
           try {
              connection=new Socket(host,port);
              output=connection.getOutputStream();
              //output.flush();
              input=connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
              finished=false;
              startReader();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      private void startReader(){
          Thread tw=new Thread(new ReaderThread());
          tw.start();
      }

    @Override
    public Voluntar login(String e, String p, IObserver client) throws ServiceException {
          initializeConnection();
          VoluntarDTO dto=new VoluntarDTO(e,p);
          ChatProtobufs.ChatRequest request =ProtoUtils.createLoginRequest(dto);
          sendRequest(request);
          ChatProtobufs.ChatResponse response=readResponse();
          if (response.getType()==ChatProtobufs.ChatResponse.Type.Ok){
              this.client=client;
              ChatProtobufs.Voluntar v= response.getVoluntar(0);
              Voluntar nou =new Voluntar(v.getNume(),v.getPrenume(),v.getEmail(),v.getPasswd());
              nou.setId(v.getId());
              return nou;
          }
          if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
              String errorText=ProtoUtils.getError(response);
              closeConnection();
              throw new ServiceException(errorText);
          }
          return  null;
    }

    @Override
    public void logout(Voluntar user, IObserver client) throws ServiceException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        ChatProtobufs.ChatResponse response=readResponse();
        closeConnection();
        if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new ServiceException(errorText);
        }

    }

    @Override
    public Voluntar getVoluntarDupaDate(String e, String p) {
        return null;
    }

    @Override
    public void saveDonatie(Donatie don, IObserver client) {


        ChatProtobufs.ChatRequest request =ProtoUtils.SaveDonatieRequest(don);
        try {
            sendRequest(request);
            ChatProtobufs.ChatResponse response = readResponse();

            if (response.getType() == ChatProtobufs.ChatResponse.Type.Error) {
                String errorText = ProtoUtils.getError(response);
                closeConnection();
                throw new ServiceException(errorText);
            }
        }catch (ServiceException ex){
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public Donator getDonatorDupaDate(String n, String p, String ad, long tel) {
        DonatorDTO dto=new DonatorDTO(n,p,ad,tel);
        ChatProtobufs.ChatRequest request =ProtoUtils.GetDateDonRequest(dto);
        try {
            sendRequest(request);
            ChatProtobufs.ChatResponse response = readResponse();
            if (response.getType() == ChatProtobufs.ChatResponse.Type.Unknown){
                    return  null;
            }
            if (response.getType() == ChatProtobufs.ChatResponse.Type.GetDonator) {
                ChatProtobufs.Donator donators=response.getDonator(0);
                Donator nou=new Donator(donators.getNume(), donators.getPrenume(),
                        donators.getAdresa(), (long) donators.getNr());
                nou.setId(donators.getId());
                return nou;
            }
            if (response.getType() == ChatProtobufs.ChatResponse.Type.Error) {
                String errorText = ProtoUtils.getError(response);
                closeConnection();
                throw new ServiceException(errorText);
            }
        }catch (ServiceException ex){
            System.out.println(ex.getMessage());
        }
        return  null;
    }

    @Override
    public void saveDonator(String n, String p, String ad, long tel) {

            DonatorDTO dto =new DonatorDTO(n,p,ad,tel);
            ChatProtobufs.ChatRequest request =ProtoUtils.SaveDonatorRequest(dto);
            try {
                sendRequest(request);
                ChatProtobufs.ChatResponse response = readResponse();


                if (response.getType() == ChatProtobufs.ChatResponse.Type.Error) {
                    String errorText = ProtoUtils.getError(response);
                    closeConnection();
                    throw new ServiceException(errorText);
                }
            }catch (ServiceException ex){
                System.out.println(ex.getMessage());
            }

    }

    @Override
    public Iterable<CazCaritabil> getCazuri() {

        ChatProtobufs.ChatRequest request =ProtoUtils.createGetCazuriRequest();
        try {
            sendRequest(request);
            ChatProtobufs.ChatResponse response=readResponse();
            if (response.getType()==ChatProtobufs.ChatResponse.Type.GetCazuri){
                Iterable<CazCaritabil> caz=ProtoUtils.getCazuriProto(response);

                return caz;
            }
            if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
                String errorText=ProtoUtils.getError(response);
                closeConnection();
                throw new ServiceException(errorText);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return  null;
    }

    @Override
    public Iterable<Donator> getDonatori() {
        ChatProtobufs.ChatRequest request =ProtoUtils.createGetDonatoriAll();
        try {
            sendRequest(request);
            ChatProtobufs.ChatResponse response=readResponse();
            if (response.getType()==ChatProtobufs.ChatResponse.Type.GetDonatori){
                Iterable<Donator> donators=ProtoUtils.getDonatoriProto(response);
                return donators;
            }
            if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
                String errorText=ProtoUtils.getError(response);
                closeConnection();
                throw new ServiceException(errorText);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return  null;
    }


    @Override
    public Iterable<Donator> getDonatorDupaNume(String n) {
        DonatorDTO dto=new DonatorDTO(n);
        ChatProtobufs.ChatRequest request =ProtoUtils.GetNumeDonRequest(dto);
        try {
            sendRequest(request);
            ChatProtobufs.ChatResponse response = readResponse();
            if (response.getType() == ChatProtobufs.ChatResponse.Type.GetNumeDon) {
                Iterable<Donator> donators=ProtoUtils.getDonatoriProto(response);
                return donators;
            }
            if (response.getType() == ChatProtobufs.ChatResponse.Type.Error) {
                String errorText = ProtoUtils.getError(response);
                closeConnection();
                throw new ServiceException(errorText);
            }
        }catch (ServiceException ex){
            System.out.println(ex.getMessage());
        }
        return  null;
    }


    private void handleUpdate(ChatProtobufs.ChatResponse updateResponse){
          switch (updateResponse.getType()){
              case Refresh:{
                  Donatie d=ProtoUtils.getDonatie(updateResponse);
                  System.out.println("Donatie proxy: "+d);
                  try {
                      client.refreshCazuri(d);
                  } catch (ServiceException e) {
                      e.printStackTrace();
                  }
                  break;
              }


          }

      }


      private class ReaderThread implements Runnable{
          public void run() {
              while(!finished){
                  try {
                      ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.parseDelimitedFrom(input);
                      System.out.println("response received "+response);

                      if (isUpdateResponse(response.getType())){
                           handleUpdate(response);
                      }else{
                          try {
                              qresponses.put(response);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                      }
                  } catch (IOException e) {
                      System.out.println("Reading error "+e);
                  }
              }
          }
      }

    private boolean isUpdateResponse(ChatProtobufs.ChatResponse.Type type){
        if (type == ChatProtobufs.ChatResponse.Type.Refresh) {
            return true;
        }
        return false;
    }
}
