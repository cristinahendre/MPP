import Domain.Donatie;
import Domain.Donator;
import Domain.Validators.DonatieValidator;
import Domain.Validators.DonatorValidator;
import Domain.Validators.Validator;
import NetworkUtils.AbstractServer;
import NetworkUtils.RpcConcurrentServer;
import NetworkUtils.ServerException;
import Repository.*;
import Service.IService;
import Service.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");

    }
//        // UserRepository userRepo=new UserRepositoryMock();
//        Properties serverProps=new Properties();
//        try {
//            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));
//            System.out.println("Server properties set. ");
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find chatserver.properties "+e);
//            return;
//        }
//
//        CazRepoI cazRepo =new CazRepo(serverProps);
//        DonatorRepoI donatorRepo =new DonatorRepo(serverProps);
//        DonatieRepoI donatieRepo =new DonatieRepo(serverProps,cazRepo,donatorRepo);
//        VoluntarRepoI voluntarRepo =new VoluntarRepo(serverProps);
//        Validator<Donator> donatorValidator =new DonatorValidator();
//        Validator<Donatie> donatieValidator =new DonatieValidator();
//
//        IService chatServerImpl=new SuperService(cazRepo,donatorRepo,donatieRepo,voluntarRepo,donatorValidator,donatieValidator);
//        int chatServerPort=defaultPort;
//        try {
//            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
//        }catch (NumberFormatException nef){
//            System.err.println("Wrong  Port Number"+nef.getMessage());
//            System.err.println("Using default port "+defaultPort);
//        }
//        System.out.println("Starting server on port: "+chatServerPort);
//        AbstractServer server = new RpcConcurrentServer(chatServerPort, chatServerImpl);
//        try {
//            server.start();
//        } catch (ServerException  e) {
//            System.err.println("Error starting the server" + e.getMessage());
//        }finally {
//            try {
//                server.stop();
//            }catch( ServerException e){
//                System.err.println("Error stopping server "+e.getMessage());
//            }
//        }
//    }
}
