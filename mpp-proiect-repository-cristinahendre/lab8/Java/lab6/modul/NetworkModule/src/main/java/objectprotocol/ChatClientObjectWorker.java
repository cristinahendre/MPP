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


public class ChatClientObjectWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientObjectWorker(IService server, Socket connection) {
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
                Object response=handleRequest((RequestObject)request);
                if (response!=null){
                   sendResponse((ResponseObject) response);
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





    private ResponseObject handleRequest(RequestObject requestObject){
        ResponseObject responseObject =null;
        if (requestObject instanceof LoginRequestObject){
            System.out.println("Login requestObject ...");
            LoginRequestObject logReq=(LoginRequestObject) requestObject;
            VoluntarDTO udto=logReq.getUser();

            try {
                Voluntar vol = server.login(udto.getEmail(), udto.getPasswd(),this);
                System.out.println("worker(login): found "+vol);
                return new LoginResponse(vol);
            } catch (ServiceException e) {
                connected=false;
                return new ErrorResponseObject(e.getMessage());
            }
        }

        if (requestObject instanceof GetCazuriObjectRequest){
            System.out.println("get cazuri request ...");
            GetCazuriObjectRequest logReq=(GetCazuriObjectRequest) requestObject;


            Iterable<CazCaritabil> cazs = server.getCazuri();
            System.out.println("worker(get cazuri): found "+cazs);
            return new GetCazuriResponse(cazs);

        }

        if (requestObject instanceof GetDonatoriORequest){
            System.out.println("get donatori request ...");
            GetDonatoriORequest logReq=(GetDonatoriORequest) requestObject;

            Iterable<Donator> cazs = server.getDonatori();
            System.out.println("worker(get donatori): found "+cazs);
            return new GetDonatoriOResponse(cazs);

        }

        if (requestObject instanceof GetDateDonRequest){
            System.out.println("get date don request ...");
            GetDateDonRequest logReq=(GetDateDonRequest) requestObject;
            DonatorDTO dto = logReq.getDonator();
            System.out.println("avem dto: "+dto);

            Donator rez = server.getDonatorDupaDate(dto.getNume(), dto.getPrenume(),
                    dto.getAdresa(),dto.getTel());
            System.out.println("worker(get donatori(date)): found "+rez);
            return new GetDateDonResponse(rez);

        }

        if (requestObject instanceof GetNumeDonatorRequest){
            System.out.println("get nume* don request ...");
            GetNumeDonatorRequest logReq=(GetNumeDonatorRequest) requestObject;
            DonatorDTO dto = logReq.getDonator();
            System.out.println("avem dto: "+dto);

            Iterable<Donator> rez=server.getDonatorDupaNume(dto.getNume());
            System.out.println("worker(get donatori-nume(date)): found "+rez);
            return new GetNumeDonatorResponse(rez);

        }

        if (requestObject instanceof SaveDonatieRequest){
            System.out.println("save donatie request ...");
            SaveDonatieRequest logReq=(SaveDonatieRequest) requestObject;
            Donatie dto = logReq.getDonatie();
            System.out.println("avem donatia: "+dto);

            server.saveDonatie(dto,this);

            return new SaveDonatieResponse();

        }


        if (requestObject instanceof LogoutRequestObject){
            System.out.println("Logout requestObject");
            LogoutRequestObject logReq=(LogoutRequestObject) requestObject;
            Voluntar udto=logReq.getVoluntar();

            try {
                server.logout(udto, this);
                connected=false;
                return new LogoutResponse();

            } catch (ServiceException e) {
               return new ErrorResponseObject(e.getMessage());
            }
        }


        return responseObject;
    }

    private void sendResponse(ResponseObject responseObject) throws IOException{
        System.out.println("sending responseObject "+ responseObject);
        synchronized (output) {
            output.writeObject(responseObject);
            output.flush();
        }
    }

    @Override
    public void refreshCazuri(Donatie don) throws ServiceException {


        System.out.println("Refresh cu donatia:  "+don);
        try {

            sendResponse(new RefreshResponse(don));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
