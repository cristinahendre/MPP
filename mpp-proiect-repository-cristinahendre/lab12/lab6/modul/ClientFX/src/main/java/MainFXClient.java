

import Controller.LoginController;
import Controller.MainController;
import Service.IService;
import Service.IServiceAM;
import Service.NotificationReceiver;
import ams.NotificationReceiverImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpcProtocol.ChatServicesRpcProxy;

import java.io.IOException;
import java.util.Properties;


public class MainFXClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServiceAM server=(IServiceAM)factory.getBean("chatService");

        NotificationReceiver receiver= (NotificationReceiver)factory.getBean("notificationReceiver");
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);




        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("pagina1.fxml"));
        Parent croot=cloader.load();


        MainController chatCtrl =
                cloader.<MainController>getController();
        //chatCtrl.setServer(server);

        ctrl.setChatController(chatCtrl);
        chatCtrl.setReceiver(receiver);
        ctrl.setParent(croot);

        primaryStage.setTitle("Teledon");
        primaryStage.setScene(new Scene(root, 280, 230));
        primaryStage.show();




    }




}


