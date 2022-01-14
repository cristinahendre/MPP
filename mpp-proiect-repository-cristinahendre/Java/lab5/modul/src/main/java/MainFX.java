import Controller.LoginController;
import Domain.Donator;
import Domain.Validators.DonatieValidator;
import Domain.Validators.DonatorValidator;
import Domain.Validators.Validator;
import Repository.*;
import Service.ServiceException;
import Service.SuperService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app "+e);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    static SuperService getService() throws ServiceException {
        try {
            Properties properties = new Properties();
            //properties.load(new FileReader("C:\\Users\\crist\\Documents\\MPP\\mpp-proiect-repository-cristinahendre\\Java\\lab5\\bd.config"));
            properties.load(new FileReader("src\\main\\resources\\bd.config"));
            CazRepoI cazRepo = new CazRepo(properties);
            DonatorRepoI donatorRepo = new DonatorRepo(properties);
            DonatieRepoI donatieRepo =new DonatieRepo(properties,cazRepo,donatorRepo);
            VoluntarRepoI voluntarRepo = new VoluntarRepo(properties);
            Validator DonatieValidator =new DonatieValidator();
            Validator DonatorValidator= new DonatorValidator();


            SuperService service= new SuperService(cazRepo,donatorRepo,donatieRepo,voluntarRepo,
                    DonatorValidator,DonatieValidator);
            return service;
        }catch (IOException ex){
            throw new ServiceException("Error starting app "+ex);
        }
    }
}
