package rpcProtocol;

import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Voluntar;
import Service.IObserver;
import Service.IService;
import Service.ServiceException;
import dto.DTOUtils;
import dto.VoluntarDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class ChatClientRpcReflectionWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientRpcReflectionWorker(IService server, Socket connection) {
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
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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


    private static Response okResponse=new Response.Builder().type(ResponseTime.OK).build();
    //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
//        System.out.println("Login request ..."+request.type());
//        VoluntarDTO udto=(VoluntarDTO) request.data();
//        Voluntar user= DTOUtils.getFromDTO(udto);
//        try {
//           // server.login(user, this);
//            return okResponse;
//        } catch (ServiceException e) {
//            connected=false;
//            return new Response.Builder().type(ResponseTime.ERROR).data(e.getMessage()).build();
//        }
        return null;
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        VoluntarDTO udto=(VoluntarDTO) request.data();
        Voluntar user=DTOUtils.getFromDTO(udto);
        try {
            server.logout(user, this);
            connected=false;
            return okResponse;

        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseTime.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }



    @Override
    public void refreshCazuri(Donatie cazuri) throws ServiceException {

    }
}
