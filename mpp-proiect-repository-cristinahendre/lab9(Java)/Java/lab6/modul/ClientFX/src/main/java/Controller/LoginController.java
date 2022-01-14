package Controller;


import Domain.Voluntar;
import Service.IService;
import Service.ServiceException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class LoginController {

    Parent mainChatParent;
    private MainController mainCtr;


    @FXML
    private PasswordField parolaField;


    @FXML
    private TextField email;

    @FXML
    private Button submit;

    public LoginController() {
    }

    private IService service;

    public void setService(IService service){
        this.service=service;
    }

    @FXML
    public void initialize(){
        email.setPromptText("Tastati adresa de email");
        parolaField.setPromptText("Tastati parola");

    }

    public void cautaVoluntar(ActionEvent actionEvent) {

        if(email!= null){
                String adresa_email = email.getText();
                if(parolaField !=null){
                    String parola= parolaField.getText();
                    System.out.println("email = "+adresa_email+"  parola= "+parola);
                    //Voluntar vol = service.getVoluntarDupaDate(adresa_email,parola);


                    Voluntar vol = null;
                    try {
                        vol = service.login(adresa_email,parola,mainCtr.getObserver());
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                    if(vol == null){
                        Notification.showNotification("Voluntar invalid. ", Alert.AlertType.ERROR);
                        return;
                    }

                    Stage stage=new Stage();
                        stage.setTitle(vol.getEmail());
                        stage.setScene(new Scene(mainChatParent));
                        Stage myStage = (Stage) submit.getScene().getWindow();
                        mainCtr.setServices(service,myStage, vol);
                        //mainCtr.setServer(service);
                        email.setPromptText("Tastati adresa de email");
                        parolaField.setPromptText("Tastati parola");

                        myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                //mainCtr.logout();
                                System.exit(0);
                            }
                        });

                        stage.show();


                        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                    }
                    else{
                        Notification.showNotification("Date incorecte.", Alert.AlertType.ERROR);
                    }


        }
        else{
            Notification.showNotification("Email-ul este vid.", Alert.AlertType.ERROR);
        }
    }

    private  void afiseazaFereastra(Voluntar voluntar, Stage stage){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pagina1.fxml"));
            AnchorPane root = loader.load();
            MainController ctrl = loader.getController();
            //ctrl.setServices(service, stage);
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Teledon");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setServer(IService server) {
        this.service=server;
    }

    public void setChatController(MainController chatCtrl) {
        this.mainCtr=chatCtrl;
    }

    public void setParent(Parent croot) {
        this.mainChatParent=croot;
    }
}
