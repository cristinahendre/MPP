package Controller;

import Domain.MD5;
import Domain.Voluntar;
import Service.SuperService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    private PasswordField parolaField;

    @FXML
    private TextField email;

    @FXML
    private Button submit;

    public LoginController() {
    }

    private SuperService service;

    public void setService(SuperService service){
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
                    Voluntar vol = service.getVoluntarDupaDate(adresa_email,parola);
                    if(vol!=null){

                        Stage stage = (Stage) submit.getScene().getWindow();
                        afiseazaFereastra(vol, stage);
                        parolaField.clear();
                        email.clear();
                        stage.close();
                    }
                    else{
                        Notification.showNotification("Date incorecte.", Alert.AlertType.ERROR);
                    }
                }
                else{
                    Notification.showNotification("Parola e vida.", Alert.AlertType.ERROR);

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
            ctrl.setServices(service, stage);
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Teledon");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
