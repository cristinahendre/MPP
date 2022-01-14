package NetworkUtils;


import Service.IService;
import Service.IServiceAM;

import java.net.Socket;

public class AmsConcurrentServer extends AbsConcurrentServer {
    private IServiceAM chatServer;
    public AmsConcurrentServer(int port, IServiceAM chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcAMSConcurrentServer port "+port);
    }

    @Override
    protected Thread createWorker(Socket client) {
//        ChatClientAMSRpcReflectionWorker worker=new ChatClientAMSRpcReflectionWorker(chatServer, client);
//
//        Thread tw=new Thread(worker);
//        return tw;
        return null;
    }
}
