
import Domain.Joc;
import Domain.Persoana;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class TablaController extends UnicastRemoteObject implements  IObserver , Serializable {

    private IService service;
    Persoana actual;
    Stage loginStage;

    public TablaController() throws RemoteException {
    }

    public IObserver getObserver() {
        return this;
    }

    ObservableList<Persoana> model = FXCollections.observableArrayList();


    @FXML
    TableView<Persoana> tableView;


    @FXML
    TableColumn<String, Persoana> idPlayer;

    @FXML
    Button butonStart, butonAlege;

    @FXML
    TextField pozAvion;

    @FXML
    Rectangle unu, doi, trei, patru, cinci, sase, sapte, opt, noua;

    @FXML
    public void initialize() {

        idPlayer.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableView.setItems(model);
    }


    public void start(ActionEvent actionEvent) {

        try {
            String poz = pozAvion.getText();
            if (poz.equals("") || poz.equals(" ")) {
                Notification.showNotification("Nu ati introdus date.", Alert.AlertType.ERROR);
                return;
            }
            int p = Integer.parseInt(poz);
            if (!actual.isParticipa()) {
                actual.setParticipa(true);
                service.update(actual);

            }
            if (service.alegeUnParticipant(actual) == null) {
                return;
            }
            model.setAll(service.getPersoaneParticipante());
            System.out.println("Tabla ctr: s-a ales participant..");
            if(!service.participaLaJocPersoana(actual)){
                Joc joc =new Joc(actual.getId(),"#ff0000",poz);
                    service.incepeJoc(joc);
                    colorare(p,Color.VIOLET);
                    butonStart.setDisable(true);


            }
        } catch (NullPointerException ex) {
            Notification.showNotification("Nu ati introdus date.", Alert.AlertType.ERROR);
        } catch (NumberFormatException ex) {
            Notification.showNotification("Pozitie invalida.", Alert.AlertType.ERROR);

        }
    }

    public void setServer(IService server, Stage loginStage, Persoana p) {
        this.service = server;
        this.actual = p;
        this.loginStage = loginStage;

    }


    @Override
    public void refresh(Persoana don) throws ServiceException, RemoteException {


        try {

                if (service.alegeUnParticipant(actual) != null) {
                    System.out.println("refresh in ctr: s-a ales participant..");

                    model.setAll(service.getPersoaneParticipante());
                }


                String poz = pozAvion.getText();
                    if (poz.equals("") || poz.equals(" ")) {
                        Notification.showNotification("Nu ati introdus date.", Alert.AlertType.ERROR);
                        return;
                    }
                    int p = Integer.parseInt(poz);
                    if (p < 1 || p > 9) {

                    }
                    if(!service.participaLaJocPersoana(actual)){
                        Joc joc =new Joc(actual.getId(),"#ff0000",poz);


                        service.incepeJoc(joc);
                        butonStart.setDisable(true);
                        colorare(p,Color.VIOLET);



                    }

        } catch (NumberFormatException ex) {
            Notification.showNotification("Numar invalid.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void pozitieInamic(Persoana p ,int pozitie) throws RemoteException {
        if(!p.equals(actual))
            colorare(pozitie,Color.YELLOWGREEN);
    }

    @Override
    public void castigator(Persoana p) throws RemoteException {

        Platform.runLater(()-> {
            Notification.showNotification("Castigator e: " + p.getId(), Alert.AlertType.CONFIRMATION);
            butonAlege.setDisable(true);
        });

    }


    private void colorare(int p, Color culoare) {
        if(p==1){
            unu.setFill(culoare);
        } if(p==2){
            doi.setFill(culoare);
        } if(p==3){
            trei.setFill(culoare);
        } if(p==4){
            patru.setFill(culoare);
        } if(p==5){
            cinci.setFill(culoare);
        } if(p==6){
            sase.setFill(culoare);
        } if(p==7){
            sapte.setFill(culoare);
        } if(p==8){
            opt.setFill(culoare);
        }if(p==9){
            noua.setFill(culoare);
        }
    }

    public void alegere(ActionEvent actionEvent) {
        String poz = pozAvion.getText();
        if (poz.equals("") || poz.equals(" ")) {
            Notification.showNotification("Nu ati introdus date.", Alert.AlertType.ERROR);
            return;
        }
        int p = Integer.parseInt(poz);
        service.ghicire(actual,p);
    }

    public void logout(ActionEvent actionEvent) {
        try{
            service.logout(actual, this);
            Stage myStage = (Stage) butonAlege.getScene().getWindow();

            myStage.close();
            loginStage.show();

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}