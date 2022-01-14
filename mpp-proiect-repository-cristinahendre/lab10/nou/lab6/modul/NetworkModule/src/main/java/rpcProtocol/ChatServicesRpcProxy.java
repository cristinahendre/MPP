package rpcProtocol;

import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
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
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServicesRpcProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ChatServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    public Voluntar login(String e, String p, IObserver client) throws ServiceException {

        try {
            initializeConnection();
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }

        VoluntarDTO udto = new VoluntarDTO(e,p);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(udto).build();
        sendRequest(req);
        System.out.println("TRIMIT REQUEST LA LOGIN.(proxy)");
        Response response = readResponse();
        if (response.type() == ResponseTime.OK) {
            this.client = client;
            Voluntar v = (Voluntar) response.data();
            System.out.println("am citit voluntarul "+v);

            //System.out.println("am primit raspuns ");
            return v;


        }
        if (response.type() == ResponseTime.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new ServiceException(err);
        }
        return null;
    }





    public void logout(Voluntar user, IObserver client) throws ServiceException {
        VoluntarDTO udto= DTOUtils.getDTO(user);

        Request req=new Request.Builder().type(RequestType.LOGOUT).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseTime.ERROR){
            String err=response.data().toString();
            throw new ServiceException(err);
        }
    }

    @Override
    public Voluntar getVoluntarDupaDate(String e, String p) {

        System.out.println("Caut voluntarul in char services rpc proxy");

        VoluntarDTO udto = DTOUtils.getHalfVoluntar(e,p);
        System.out.println("am creat udto: "+udto);
        Request req = new Request.Builder().type(RequestType.GET_VOL).data(udto).build();
        try {
            sendRequest(req);
            System.out.println("s-a trimis request pentru cauta voluntar");

        Response response = readResponse();
            System.out.println("citesc raspunsul referitor la voluntar");
        if (response.type() == ResponseTime.OK) {
            Voluntar vol = (Voluntar) response.data();
            System.out.println("am primit raspuns "+vol);
            return vol;
        }
        if (response.type() == ResponseTime.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new ServiceException(err);
        }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveDonatie(Donatie don, IObserver client) {

        this.client=client;
        System.out.println("Vreau sa salvez o donatie");

        DonatieDTO donDTO =new DonatieDTO(don.getCaz(),don.getDonator(),don.getSuma_donata());
        Request req = new Request.Builder().type(RequestType.NEW_DONATIE).data(donDTO).build();
        try {
            sendRequest(req);
            System.out.println("s-a trimis request pentru a salva donatia");

            Response response = readResponse();
            System.out.println("citesc raspunsul referitor la donatie");
            if (response.type() == ResponseTime.NEW_DONATIE) {
                this.client=client;
                String resp = (String) response.data();
                System.out.println("am primit raspuns "+resp);
                return;

            }
            if (response.type() == ResponseTime.ERROR) {
                String err = response.data().toString();
                closeConnection();
                throw new ServiceException(err);
            }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }




    }

    @Override
    public Donator getDonatorDupaDate(String n, String p, String ad, long tel) {
        System.out.println("Caut donatorul in char services rpc proxy");

        DonatorDTO dto =new DonatorDTO(n,p,ad,tel);
        Request req = new Request.Builder().type(RequestType.GET_DATE_DON).data(dto).build();
        try {
            sendRequest(req);
            System.out.println("s-a trimis request pentru cauta date donator");

            Response response = readResponse();
            System.out.println("citesc raspunsul referitor la donator");
            if (response.type() == ResponseTime.GET_DONATOR) {
                Donator vol = (Donator) response.data();
                System.out.println("am primit raspuns "+vol);
                return vol;
            }
            if (response.type() == ResponseTime.ERROR) {
                String err = response.data().toString();
                closeConnection();
                throw new ServiceException(err);
            }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveDonator(String n, String p, String ad, long tel) {
        System.out.println("Vreau sa salvez un donator");

        DonatorDTO don =new DonatorDTO(n,p,ad,tel);
        Request req = new Request.Builder().type(RequestType.SAVE_DON).data(don).build();
        try {
            sendRequest(req);
            System.out.println("s-a trimis request pentru a salva un donator");

            Response response = readResponse();
            System.out.println("citesc raspunsul referitor la donatori");
            if (response.type() == ResponseTime.SAVE_DONATOR) {
                String resp = (String) response.data();
                System.out.println("am primit raspuns "+resp);

            }
            if (response.type() == ResponseTime.ERROR) {
                String err = response.data().toString();
                closeConnection();
                throw new ServiceException(err);
            }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }



    }

    @Override
    public Iterable<CazCaritabil> getCazuri() {

        System.out.println("Vreau toate cazurile caritabile");


        Request req = new Request.Builder().type(RequestType.GET_CAZURI).build();
        try {
            sendRequest(req);
            System.out.println("s-a trimis request pentru cazuri");

            Response response = readResponse();
            System.out.println("citesc raspunsul referitor la cazuri");
            if (response.type() == ResponseTime.GET_CAZURI) {
                Iterable<CazCaritabil> cazuri = (Iterable<CazCaritabil>) response.data();
                System.out.println("am primit raspuns "+cazuri);
                return cazuri;
            }
            if (response.type() == ResponseTime.ERROR) {
                String err = response.data().toString();
                closeConnection();
                throw new ServiceException(err);
            }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Donator> getDonatori() {

        System.out.println("Vreau toti donatorii");


        Request req = new Request.Builder().type(RequestType.GET_DONATORI).build();
        try {
            sendRequest(req);
            System.out.println("s-a trimis request pentru DONATORI");

            Response response = readResponse();
            System.out.println("citesc raspunsul referitor la donatori");
            if (response.type() == ResponseTime.GET_DONATORI) {
                Iterable<Donator> cazuri = (Iterable<Donator>) response.data();
                System.out.println("am primit raspuns "+cazuri);
                return cazuri;
            }
            if (response.type() == ResponseTime.ERROR) {
                String err = response.data().toString();
                closeConnection();
                throw new ServiceException(err);
            }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Donator> getDonatorDupaNume(String n) {
        System.out.println("Vreau toti donatorii ce au numele incepand cu un string");


        Request req = new Request.Builder().type(RequestType.SEARCH_DONATOR).data(n).build();
        try {
            sendRequest(req);
            System.out.println("s-a trimis request pentru DONATORI");

            Response response = readResponse();
            System.out.println("citesc raspunsul referitor la donatori");
            if (response.type() == ResponseTime.GET_DONATOR_NUME) {
                Iterable<Donator> cazuri = (Iterable<Donator>) response.data();
                System.out.println("am primit raspuns "+cazuri);
                return cazuri;
            }
            if (response.type() == ResponseTime.ERROR) {
                String err = response.data().toString();
                closeConnection();
                throw new ServiceException(err);
            }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }

        return null;
    }

    private void handleUpdate(Response response) {
        if (response.type() == ResponseTime.REFRESH ) {

            Donatie friend = DTOUtils.getDonatieFromDTO((DonatieDTO) response.data());
            System.out.println("Donatie saved " + friend);
            System.out.println("SA SE SALVEZE DONATIAAAAAAA");
            try {
                client.refreshCazuri(friend);
            } catch (ServiceException |RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    private void closeConnection() {
        finished = true;
        try {

            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private synchronized void sendRequest(Request request) throws ServiceException {
        try {

                output.writeObject(request);
                output.flush();

        } catch (IOException e) {
            throw new ServiceException("Error sending object " + e);
        }

    }

    private synchronized  Response readResponse() throws ServiceException {
        Response response = null;
        try {

            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws ServiceException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private boolean isUpdate(Response response) {
        return response.type() == ResponseTime.REFRESH;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        System.out.println("CAZ DE UPDATE. INTRAAAM");
                        handleUpdate((Response)response);


                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

}