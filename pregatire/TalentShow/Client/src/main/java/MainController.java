import Domain.Participant;
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

public class MainController extends UnicastRemoteObject implements  IObserver , Serializable {
    private IService service;
    Persoana actual;
    Stage loginStage;


    @FXML
    TableView<Participant> tableView;


    @FXML
    TableColumn<String, Participant> nume;

    @FXML
    TableColumn<String, Participant> status;
    ObservableList<Participant> model = FXCollections.observableArrayList();


    @FXML
    Button butonIesire;


    @FXML
    TextField notaText;


    public MainController() throws RemoteException {
    }

    @FXML
    public void initialize() {

        nume.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        tableView.setItems(model);

    }


    private void setAll() {

        model.setAll(service.getParticipanti());
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
        setAll();

    }

    @Override
    public void refresh() throws ServiceException, RemoteException {
        Platform.runLater(this::setAll
        );

        System.out.println("refresh");

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

    public void acordaNota(ActionEvent actionEvent) {
        try {

            int nota = Integer.parseInt(notaText.getText());
            if (nota > 10 || nota < 1) {
                Notification.showNotification("Nu s-a dat o nota valida.", Alert.AlertType.ERROR);
                return;
            }
            Participant p = tableView.getSelectionModel().getSelectedItem();
            if(p==null){
                Notification.showNotification("Nu s-a selectat.", Alert.AlertType.ERROR);
                return;
            }
            System.out.println("[main] ati ales din tabel: " + p);
            if (!p.getStatus().equals("NoResults") && !p.getStatus().equals("Pending")) {
                Notification.showNotification("Persoana a fost deja punctata.", Alert.AlertType.ERROR);
                return;
            }
            Platform.runLater(() -> {
                try {
                    service.puncteaza(p, nota, actual);
                } catch (ServiceException e) {
                    Notification.showNotification(e.getMessage(), Alert.AlertType.ERROR);

                }
            });
        } catch (NumberFormatException ex) {
            Notification.showNotification("Nu s-a dat o nota valida.", Alert.AlertType.ERROR);


        }
    }
}

