package NetworkUtils;



import Service.IService;
import objectprotocol.ChatClientObjectWorker;
import rpcProtocol.ChatClientRpcReflectionWorker;
import rpcProtocol.ChatClientRpcWorker;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    public RpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        ChatClientObjectWorker worker=new ChatClientObjectWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
