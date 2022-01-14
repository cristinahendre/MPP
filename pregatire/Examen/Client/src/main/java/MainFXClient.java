
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MainFXClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55111;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IService server=(IService)factory.getBean("chatService");

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setService(server);
//
//        FXMLLoader ploader = new FXMLLoader(
//                getClass().getClassLoader().getResource("pagina.fxml"));
//        Parent root2=ploader.load();

//
//        MainController mCtrl =
//                ploader.<MainController>getController();
//        Scene s=new Scene(root2);
//
//        ctrl.setParent(mCtrl,s);




        primaryStage.setTitle("Persoane");
        primaryStage.setScene(new Scene(root, 210, 200));
        primaryStage.show();

    }

}


