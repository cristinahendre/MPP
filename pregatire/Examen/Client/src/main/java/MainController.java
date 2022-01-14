import Domain.Jucator;
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

public class MainController extends UnicastRemoteObject implements  IObserver , Serializable {
    private IService service;
    Jucator actual;
    Stage loginStage;


    @FXML
    TableView<Jucator> tableView;


    @FXML
    TableColumn<String, Jucator> idColumn;


    ObservableList<Jucator> model = FXCollections.observableArrayList();


    @FXML
    Button butonStart, butonIesire,butonSend;

    @FXML
    TextField car1, car2;

    @FXML
    Label cuvantText;


    public MainController() throws RemoteException {
    }

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableView.setItems(model);
        butonStart.setDisable(true);
        butonSend.setDisable(true);


    }


    private void setAll() {

        model.setAll((Collection<? extends Jucator>) service.getPeople());
    }


    public void exitApp(ActionEvent actionEvent) {

        logout();

    }

    public IObserver getObserver() {
        return this;
    }

    public void setServer(IService server, Stage loginStage, Jucator p) {
        this.service = server;
        this.actual = p;
        this.loginStage = loginStage;

    }

    @Override
    public void refresh(Jucator don) throws ServiceException, RemoteException {
        Platform.runLater(this::setAll
        );

        System.out.println("refresh");

    }

    @Override
    public void putemIncepe() throws RemoteException {
        Platform.runLater(()->{
            butonStart.setDisable(false);
        });

    }

    @Override
    public void trimitCuvant(String cuv) throws RemoteException {
        Platform.runLater(()->{
            setAll();
            cuvantText.setText(cuv);
            butonStart.setDisable(true);
            butonSend.setDisable(false);
        });
    }

    @Override
    public void afiseazaPuncte(String s,String cuv) throws RemoteException {
        Platform.runLater(()->{
            Notification.showNotification(s, Alert.AlertType.INFORMATION);
            butonSend.setDisable(false);
            cuvantText.setText(cuv);
        });
    }

    @Override
    public void sfarsitJoc(String msg) throws RemoteException {
        Platform.runLater(()->{
            Notification.showNotification(msg, Alert.AlertType.INFORMATION);
            butonSend.setDisable(true);
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
        service.startJoc(actual);
    }

    public void trimiteCarac(ActionEvent actionEvent) {
        String cuv1=car1.getText();
        String cuv2=car2.getText();

        if(cuv1.equals("") || cuv2.equals("") || cuv1.equals(" ") || cuv2.equals(" ")){
            Notification.showNotification("Tastati date valide.", Alert.AlertType.ERROR);
            return;
        }
        if(cuv1.equals(cuv2)){
            Notification.showNotification("Caracteristici egale!", Alert.AlertType.ERROR);
            return;
        }
        butonSend.setDisable(true);

        service.procesezCaracteristici(actual,cuv1,cuv2,cuvantText.getText());

    }
}

