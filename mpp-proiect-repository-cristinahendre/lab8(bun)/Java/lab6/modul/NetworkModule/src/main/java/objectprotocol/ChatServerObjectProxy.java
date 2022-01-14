package objectprotocol;

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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ChatServerObjectProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    //private List<ResponseObject> responses;
    private BlockingQueue<ResponseObject> qrespons;
    private volatile boolean finished;
    public ChatServerObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<ResponseObject>();
        qrespons =new LinkedBlockingQueue<ResponseObject>();
    }

    public Voluntar login(String e, String p, IObserver client) throws ServiceException {
        initializeConnection();
        VoluntarDTO udto= new VoluntarDTO(e,p);
        sendRequest(new LoginRequestObject(udto));
        ResponseObject responseObject =readResponse();
        if (responseObject instanceof LoginResponse){
            this.client=client;
            Voluntar rez=((LoginResponse) responseObject).getVoluntar();
            return rez;
        }
        if (responseObject instanceof ErrorResponseObject){
            ErrorResponseObject err=(ErrorResponseObject) responseObject;
            closeConnection();
            throw new ServiceException(err.getMessage());
        }
        return null;
    }

    public void logout(Voluntar user, IObserver client) throws ServiceException {

        sendRequest(new LogoutRequestObject(user));
        ResponseObject response=readResponse();
        closeConnection();
        if (response instanceof ErrorResponseObject){
            ErrorResponseObject err=(ErrorResponseObject)response;
            throw new ServiceException(err.getMessage());
        }
    }

    @Override
    public Voluntar getVoluntarDupaDate(String e, String p) {
        return null;
    }

    @Override
    public void saveDonatie(Donatie don, IObserver client) {
        try {

            sendRequest(new SaveDonatieRequest(don));
            ResponseObject responseObject = readResponse();
            if (responseObject instanceof SaveDonatieResponse) {
                System.out.println("da");
            }
            if (responseObject instanceof ErrorResponseObject) {
                ErrorResponseObject err = (ErrorResponseObject) responseObject;
                closeConnection();
                throw new ServiceException(err.getMessage());
            }
        }
        catch (ServiceException e){
            e.printStackTrace();
        }

    }

    @Override
    public Donator getDonatorDupaDate(String n, String p, String ad, long tel) {

        try {
            DonatorDTO udto = new DonatorDTO(n, p, ad, tel);
            sendRequest(new GetDateDonRequest(udto));
            ResponseObject responseObject = readResponse();
            if (responseObject instanceof GetDateDonResponse) {
                Donator rez = ((GetDateDonResponse) responseObject).getDon();
                return rez;
            }
            if (responseObject instanceof ErrorResponseObject) {
                ErrorResponseObject err = (ErrorResponseObject) responseObject;
                closeConnection();
                throw new ServiceException(err.getMessage());
            }
        }
        catch (ServiceException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveDonator(String n, String p, String ad, long tel) {

    }

    @Override
    public Iterable<CazCaritabil> getCazuri() {

        try {
            sendRequest(new GetCazuriObjectRequest());
            ResponseObject responseObject =readResponse();
            if (responseObject instanceof GetCazuriResponse){
                this.client=client;
                Iterable<CazCaritabil> rez=((GetCazuriResponse) responseObject).getCazuri();
                return rez;
            }
            if (responseObject instanceof ErrorResponseObject){
                ErrorResponseObject err=(ErrorResponseObject) responseObject;
                closeConnection();
                throw new ServiceException(err.getMessage());
            }

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Donator> getDonatori() {
        try {
            sendRequest(new GetDonatoriORequest());
            ResponseObject responseObject =readResponse();
            if (responseObject instanceof GetDonatoriOResponse){

                Iterable<Donator> rez=((GetDonatoriOResponse) responseObject).getDonatori();
                return rez;
            }
            if (responseObject instanceof ErrorResponseObject){
                ErrorResponseObject err=(ErrorResponseObject) responseObject;
                closeConnection();
                throw new ServiceException(err.getMessage());
            }

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Donator> getDonatorDupaNume(String n) {

        try {
            DonatorDTO udto = new DonatorDTO(n);
            sendRequest(new GetNumeDonatorRequest(udto));
            ResponseObject responseObject = readResponse();
            if (responseObject instanceof GetNumeDonatorResponse) {
                Iterable<Donator> rez = ((GetNumeDonatorResponse) responseObject).getDonatori();
                return rez;
            }
            if (responseObject instanceof ErrorResponseObject) {
                ErrorResponseObject err = (ErrorResponseObject) responseObject;
                closeConnection();
                throw new ServiceException(err.getMessage());
            }
        }
        catch (ServiceException e){
            e.printStackTrace();
        }
        return null;
    }


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

    private void sendRequest(RequestObject requestObject)throws ServiceException {
        try {
            synchronized (output) {
                output.writeObject(requestObject);
                output.flush();
            }
        } catch (IOException e) {
            throw new ServiceException("Error sending object "+e);
        }

    }

    private ResponseObject readResponse() throws ServiceException {
        ResponseObject responseObject =null;
        try{
            /*synchronized (responses){
                responses.wait();
            }
            responseObject = responses.remove(0);    */
            responseObject = qrespons.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return responseObject;
    }
    private void initializeConnection() throws ServiceException {
         try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
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


    private void handleUpdate(UpdateResponseObject update){

            RefreshResponse msgRes=(RefreshResponse) update;
            Donatie message=msgRes.getDto();
            try {
                client.refreshCazuri(message);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (response instanceof UpdateResponseObject){
                        System.out.println("update...");
                         handleUpdate((UpdateResponseObject)response);
                    }else{
                        /*responses.add((ResponseObject)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (responses){
                            responses.notify();
                        }*/
                        try {
                            qrespons.put((ResponseObject)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
