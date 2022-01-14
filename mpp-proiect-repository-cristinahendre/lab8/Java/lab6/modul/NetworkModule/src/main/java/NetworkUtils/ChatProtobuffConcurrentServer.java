package NetworkUtils;

import Service.IService;
import protoProtocol.ProtoChatWorker;

import java.net.Socket;


public class ChatProtobuffConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    public ChatProtobuffConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProtoChatWorker worker=new ProtoChatWorker(chatServer,client);
        Thread tw=new Thread(worker);
        return tw;
    }
}