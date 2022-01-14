import Domain.Jucator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LoginController {

    IService service;
    MainController ctr;
    Scene parinte;
    Parent root2;

    @FXML
    TextField numeP,emailP;

    @FXML
    Button loginButton;

    @FXML
    public void initialize(){
        emailP.setPromptText("Tastati adresa de email");
        numeP.setPromptText("Tastati numele");

    }

    public void setParent(MainController ctr, Scene parinte){
        this.ctr=ctr;
        this.parinte=parinte;
    }


    public void setService(IService service){
        this.service=service;
    }
    public void login(ActionEvent actionEvent) {
        if(emailP!= null){
            String adresa_email = emailP.getText();
            if(numeP !=null){
                String parola= numeP.getText();
                System.out.println("email = "+adresa_email+"  parola= "+parola);



                Jucator vol = null;
                try {
                    creare();
                    vol = service.getDateJucator(adresa_email,parola);
                    if(vol == null){
                        Notification.showNotification("Om invalid.", Alert.AlertType.ERROR);
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage=new Stage();
                stage.setTitle(vol.getEmail());
                stage.setScene(new Scene(root2));

                Stage myStage = (Stage) loginButton.getScene().getWindow();
                ctr.setServer(service,myStage, vol);
                try {
                    service.login(vol,ctr.getObserver());
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                //mainCtr.setServer(service);
                emailP.setText("");
                numeP.setText("");
                emailP.setPromptText("Tastati adresa de email");
                numeP.setPromptText("Tastati numele");

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

    private void creare() throws IOException {
        FXMLLoader ploader = new FXMLLoader(
                getClass().getClassLoader().getResource("pagina.fxml"));
        root2=ploader.load();

        ctr= ploader.<MainController>getController();
    }
}
