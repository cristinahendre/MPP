package rpcProtocol;


import Domain.CazCaritabil;

import Domain.Donatie;
import Domain.Donator;
import Domain.Validators.DonatieValidator;
import Domain.Voluntar;
import Service.IObserver;
import Service.IService;
import Service.ServiceException;
import dto.DTOUtils;
import dto.DonatieDTO;
import dto.DonatorDTO;
import dto.VoluntarDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ChatClientRpcWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientRpcWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                System.out.println("rpc worker: read "+ request);
                Response response=handleRequest((Request)request);
                System.out.println("request handled. => response: "+response);
                if (response!=null){
                    System.out.println("sending a response...");
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("thread sleeping..");
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


    private static Response okResponse=new Response.Builder().type(ResponseTime.OK).build();
    private static Response badResponse=new Response.Builder().type(ResponseTime.ERROR).build();

    //  private static ResponseObject errorResponse=new ResponseObject.Builder().type(ResponseType.ERROR).build();

    private Response handleRequest(Request request){
        Response response=null;
        if (request.type()== RequestType.SAVE_DON){
            System.out.println("Save donator request ..."+request.type());
            DonatorDTO udto=(DonatorDTO) request.data();
            Donator user= DTOUtils.getDonatorDTO(udto);
            server.saveDonator(udto.getNume(),udto.getPrenume(),udto.getAdresa(), udto.getTel());
            System.out.println("request type: save don; found donator: "+user);

            Response resp =new Response.Builder().type(ResponseTime.SAVE_DONATOR).data(" ").build();
            return resp;
        }

        if (request.type()== RequestType.NEW_DONATIE){
            System.out.println("Save donatie request ..."+request.type());
            DonatieDTO udto=(DonatieDTO) request.data();
            Donatie user= DTOUtils.getDonatieFromDTO(udto);
            server.saveDonatie(user, this);
            System.out.println("request type: save donatie; found donatie: "+user);
            Response new_re=new Response.Builder().type(ResponseTime.NEW_DONATIE).build();

            new_re.data("succes");

            return new_re;

        }

        if (request.type()== RequestType.GET_VOL){
            System.out.println("Login request ..."+request.type());
            VoluntarDTO udto=(VoluntarDTO) request.data();
            Voluntar user= DTOUtils.getFromDTO(udto);
            Voluntar vol =server.getVoluntarDupaDate(udto.getEmail(),udto.getPasswd());
            System.out.println("request type: login; found vol: "+vol);
            if(vol!= null){
                okResponse.data(vol);
                System.out.println("raspuns ok: "+vol);
                return okResponse;
            }


            else return badResponse;
        }

        if (request.type()== RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            VoluntarDTO udto=(VoluntarDTO) request.data();
            //Voluntar user= DTOUtils.getFromDTO(udto);
            try {
                Voluntar v =server.login(udto.getEmail(), udto.getPasswd(), this);
                okResponse.data(v);
                System.out.println("data response (in worker) "+okResponse.data());
                return okResponse;
            } catch (ServiceException e) {
                connected=false;
                return new Response.Builder().type(ResponseTime.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type()== RequestType.GET_DATE_DON){
            System.out.println("Get date donator request ..."+request.type());
            DonatorDTO udto=(DonatorDTO) request.data();
            Donator user= DTOUtils.getDonatorDTO(udto);
            Donator vol =server.getDonatorDupaDate(udto.getNume(),udto.getPrenume(),udto.getAdresa(), udto.getTel());
            System.out.println("request type: get date don; found donator: "+vol);
            if(vol!= null){
                Response response1 = new Response.Builder().type(ResponseTime.GET_DONATOR).data(vol).build();

                return response1;
            }

            else {
                Response response1 = new Response.Builder().type(ResponseTime.GET_DONATOR).data(vol).build();

                return response1;

            }
        }





        if (request.type()== RequestType.GET_CAZURI){
            System.out.println("Get cazuri request ..."+request.type());

            Iterable<CazCaritabil> cazs =server.getCazuri();
            System.out.println("request type: *get cazuri*; found cazuri: "+cazs);
            if(cazs!= null){
                Response new_re=new Response.Builder().type(ResponseTime.GET_CAZURI).build();

                new_re.data(cazs);
                System.out.println("raspuns ok: "+cazs);
                return new_re;

            }


            else return badResponse;
        }


        if (request.type()== RequestType.GET_DONATORI){
            System.out.println("Get donatori request ..."+request.type());

            Iterable<Donator> cazs =server.getDonatori();
            System.out.println("request type: *get donatori*; found : "+cazs);
            if(cazs!= null){
                Response new_re=new Response.Builder().type(ResponseTime.GET_DONATORI).build();

                new_re.data(cazs);
                System.out.println("raspuns ok: "+cazs);
                return new_re;
            }


            else return badResponse;
        }

        if(request.type() ==RequestType.SEARCH_DONATOR){
            System.out.println("Search donatori request ..."+request.type());
            String nume= String.valueOf(request.data());
            Iterable<Donator> cazs =server.getDonatorDupaNume(nume);
            System.out.println("request type: *search donator*; found : "+cazs);
            if(cazs!= null){
                Response resp = new Response.Builder().type(ResponseTime.GET_DONATOR_NUME).data(cazs).build();

                return resp;
            }


            else return badResponse;
        }


        if (request.type()== RequestType.LOGOUT){
            System.out.println("Logout request");
            // LogoutRequestObject logReq=(LogoutRequestObject)request;
            VoluntarDTO udto=(VoluntarDTO) request.data();
            Voluntar user= DTOUtils.getFromDTO(udto);
            try {
                server.logout(user, this);
                connected=false;
                Response rep =new Response.Builder().type(ResponseTime.LOGOUT).build();
                return rep;

            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseTime.ERROR).data(e.getMessage()).build();
            }
        }

//        if (request.type()== RequestType.GET_CAZURI) {
//            System.out.println("SendMessageRequestObject ...");
//            DonatieDTO mdto = (DonatieDTO) request.data();
//            Donatie message = DTOUtils.getDonatieFromDTO(mdto);
//
//            server.saveDonatie(message, this);
//            return okResponse;
//
//        }


        return response;
    }

    private  synchronized  void  sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);

            output.writeObject(response);
            output.flush();

    }



    @Override
    public void refreshCazuri(Donatie donatie) throws ServiceException {
        DonatieDTO mdto= DTOUtils.getDonatieDTO(donatie);
        Response resp=new Response.Builder().type(ResponseTime.REFRESH).data(mdto).build();
        System.out.println("Donatie  received  "+donatie);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new ServiceException("Sending error: "+e);
        }
    }
}
