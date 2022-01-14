import Domain.Persoana;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Random;

public class MainController extends UnicastRemoteObject implements  IObserver , Serializable {
    private IService service;
    Persoana actual;
    Stage loginStage;


    @FXML
    TableView<Persoana> tableView;


    @FXML
    TableColumn<String, Integer> idColumn;


    ObservableList<Persoana> model = FXCollections.observableArrayList();


    @FXML
    Button butonStart, butonIesire;

    @FXML
    Label traseuText;



    public MainController() throws RemoteException {
    }

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableView.setItems(model);
        butonStart.setDisable(true);
    }


    private void setAll() {

        model.setAll( service.getPeople());
    }


    public void exitApp(ActionEvent actionEvent) {

        logout();

    }

    public IObserver getObserver() {
        return this;
    }

    public void setServer(IService server, Stage loginStage, Persoana p) {
        this.service = server;
        this.actual = p;
        this.loginStage = loginStage;

    }

    @Override
    public void refresh(String don) throws ServiceException, RemoteException {
        Platform.runLater(()->{
            setAll();
            traseuText.setText(don);

        });


        System.out.println("refresh");

    }

    @Override
    public void incepe() throws RemoteException {
        butonStart.setDisable(false);

    }

    @Override
    public void notificaDate(String data, String traseu) throws RemoteException {
        Platform.runLater(()->{
            Notification.showNotification(data, Alert.AlertType.INFORMATION);
            traseuText.setText(traseu);
        });
    }


    public void logout() {

        try {
            service.logout(actual, this);
            Stage stage = (Stage) butonIesire.getScene().getWindow();
            stage.close();
            loginStage.show();
        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }

    public void start(ActionEvent actionEvent) {
        service.startGame(actual);
    }

    public void generate(ActionEvent actionEvent) {
        Random rand=new Random();
        int numar=rand.nextInt(3);
       // Notification.showNotification("Ati ales "+numar, Alert.AlertType.INFORMATION);

    }
}

