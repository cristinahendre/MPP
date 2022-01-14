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
    TableView<Persoana> tableView;


    @FXML
    TableColumn<String, Persoana> nume;

    @FXML
    TableColumn<String, Persoana> email;
    ObservableList<Persoana> model = FXCollections.observableArrayList();


    @FXML
    Button updateButton, deleteButton, addButton, butonIesire;


    @FXML
    TextField numeP, emailP, id;


    public MainController() throws RemoteException {
    }

    @FXML
    public void initialize() {

        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        nume.setCellValueFactory(new PropertyValueFactory<>("Parola"));
        tableView.setItems(model);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        numeP.setPromptText("Nume");
        emailP.setPromptText("Email");

        id.setPromptText("Cautati o persoana");
    }


    private void setAll() {

        model.setAll((Collection<? extends Persoana>) service.getPeople());
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
    public void refresh(Persoana don) throws ServiceException, RemoteException {
        Platform.runLater(this::setAll
        );

        System.out.println("refresh");

    }

    public void searchById(KeyEvent actionEvent) {
        if(id.getText().equals("")|| id.getText().equals(" ")){
            //Notification.showNotification("Id vid.", Alert.AlertType.ERROR);
            setAll();
        }
        else{
            try{
                int idNumar= Integer.parseInt(id.getText());
                model.setAll(service.getOne(idNumar));
            }
            catch (NumberFormatException ex){
                Notification.showNotification("Id invalid.", Alert.AlertType.ERROR);

            }
        }
    }

    public void add(ActionEvent actionEvent) {
        String nume=numeP.getText();
        String email=emailP.getText();
        if(nume.equals("")|| email.equals("")|| nume.equals(" ")|| email.equals(" ")){
            Notification.showNotification("Nume/email invalide.", Alert.AlertType.ERROR);
        }
        else{
            service.save(nume,email, this);
        }

    }

    public void update(ActionEvent actionEvent) {
        try{
            Persoana p =tableView.getSelectionModel().getSelectedItem();
            Persoana noua= service.getPersoanaDupaDate(p.getParola(),p.getEmail());
            String nume=numeP.getText();
            String email =emailP.getText();
            if(nume.equals("")|| email.equals("")|| nume.equals(" ")|| email.equals(" ")){
                Notification.showNotification("Nume/email invalide.", Alert.AlertType.ERROR);
            }
            else{
                noua.setEmail(email);
                noua.setParola(nume);
                service.update(noua);
            }

        }
        catch (NullPointerException ex){
            Notification.showNotification("Nu ati selectat.", Alert.AlertType.ERROR);

        }
    }

    public void delete(ActionEvent actionEvent) {
        try{
            Persoana[] pers=tableView.getSelectionModel().getSelectedItems().toArray(new Persoana[0]);
            if(pers.length ==0){
                Notification.showNotification("Nu ati selectat.", Alert.AlertType.ERROR);

            }
            else{
                for(Persoana p:pers){
                    service.delete(p);
                }
            }
        }
        catch (NullPointerException ex){
            Notification.showNotification("Nu ati selectat.", Alert.AlertType.ERROR);
        }
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
}

