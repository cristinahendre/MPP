
import Controller.LoginController;
import Controller.MainController;
import Service.IService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import protoProtocol.ProtoChatProxy;

import java.io.IOException;
import java.util.Properties;

public class StartProtobuffClient extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    public void start(Stage primaryStage) {

        Properties clientProps=new Properties();
        try {
            clientProps.load(MainFXClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

        IService server=new ProtoChatProxy(serverIP, serverPort);
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("login.fxml"));
            Parent root = loader.load();


            LoginController ctrl =
                    loader.<LoginController>getController();
            ctrl.setServer(server);


            FXMLLoader cloader = new FXMLLoader(
                    getClass().getClassLoader().getResource("pagina1.fxml"));
            Parent croot = cloader.load();


            MainController chatCtrl =
                    cloader.<MainController>getController();
            chatCtrl.setServer(server);

            ctrl.setChatController(chatCtrl);
            ctrl.setParent(croot);

            primaryStage.setTitle("Teledon");
            primaryStage.setScene(new Scene(root, 280, 230));
            primaryStage.show();

        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
