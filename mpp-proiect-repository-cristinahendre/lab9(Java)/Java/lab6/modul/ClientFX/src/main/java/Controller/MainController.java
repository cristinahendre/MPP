package Controller;


import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Voluntar;
import Service.IObserver;
import Service.IService;
import Service.ServiceException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.lang.management.PlatformManagedObject;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class MainController  implements  IObserver {
    private IService service;
    Voluntar actual;


    @FXML
    TableView<CazCaritabil> tableView;

    @FXML
    TableView<Donator> donatorTable;

    @FXML
    TableColumn<String, CazCaritabil> cazColumn;

    @FXML
    TableColumn<Integer, CazCaritabil> sumaColumn;
    ObservableList<CazCaritabil> model = FXCollections.observableArrayList();
    ObservableList<Donator> modelDon = FXCollections.observableArrayList();

    @FXML
    TableColumn<String, Donator> donNumeColumn;

    @FXML
    TableColumn<String,Donator> donPrenumeColumn;

    @FXML
    TableColumn<String, Donator> donAdresaColumn;

    @FXML
    TableColumn<String, Long> donTelColumn;


    @FXML
    Button buton, butonIesire;

    Stage loginStage;

    @FXML
    TextField numeDonator,prenumeDonator,telefonDonator, sumaDonata,
    searchField;

    @FXML
    TextArea adresaDon;

    public MainController() {
    }

    @FXML
    public void initialize(){

        cazColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        sumaColumn.setCellValueFactory(new PropertyValueFactory<>("Suma_donata"));
        tableView.setItems(model);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);




        donNumeColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        donPrenumeColumn.setCellValueFactory(new PropertyValueFactory<>("Prenume"));
        donAdresaColumn.setCellValueFactory(new PropertyValueFactory<>("Adresa"));
        donTelColumn.setCellValueFactory(new PropertyValueFactory<>("NrTelefon"));
        donatorTable.setItems(modelDon);

        numeDonator.setPromptText("Numele donatorului");
        prenumeDonator.setPromptText("Prenumele donatorului");
        adresaDon.setPromptText("Adresa donator");
        telefonDonator.setPromptText("Numarul de telefon al donatorului");
        sumaDonata.setPromptText("Suma donata de donator");
        searchField.setPromptText("Cautati un donator");
    }

    public void setServices(IService service, Stage stage, Voluntar v){
        this.service=service;
        this.loginStage=stage;
        this.actual=v;

        setCazuri();
        setDonatori();



    }

    private void setCazuri(){

        model.setAll((Collection<? extends CazCaritabil>) service.getCazuri());
    }

    private void setDonatori(){
        modelDon.setAll((Collection<? extends Donator>) service.getDonatori());
    }

    public void adaugaDonatie(ActionEvent actionEvent) {
        try{
            String nume =numeDonator.getText();
            String prenume =prenumeDonator.getText();
            String adresa=adresaDon.getText();

            long telefon = Long.parseLong(telefonDonator.getText());
            int suma= Integer.parseInt(sumaDonata.getText());
            CazCaritabil[] cazuri =tableView.getSelectionModel().getSelectedItems().toArray(new CazCaritabil[0]);
            if(cazuri.length == 0){
                Notification.showNotification("Selectati un caz(cel putin) din tabel.", Alert.AlertType.ERROR);
                return;
            }
            Donator donator =service.getDonatorDupaDate(nume,prenume,adresa,telefon);
            if (donator == null) {

                service.saveDonator(nume, prenume, adresa, telefon);
                Donator salvat = service.getDonatorDupaDate(nume, prenume, adresa, telefon);
                for(CazCaritabil caz:cazuri) {
                    //nu exista in baza de date, il adaug intai si apoi adaug donatia
                    Donatie dona =new Donatie(salvat,caz,suma);
                    Platform.runLater(()->service.saveDonatie(dona, this));


                }
                Notification.showNotification("Succes!", Alert.AlertType.CONFIRMATION);
                //setCazuri();
                //setDonatori();
                return;
            }
            else{
                for(CazCaritabil caz: cazuri) {
                    Donatie dona =new Donatie(donator,caz,suma);
                    Platform.runLater(()->service.saveDonatie(dona, this));
                }
                Notification.showNotification("Succes!", Alert.AlertType.CONFIRMATION);
              //  setCazuri();

            }


        }
        catch (Exception ex  ){
            System.out.println(ex.getMessage());
            Notification.showNotification(ex.getMessage(), Alert.AlertType.ERROR);
           // Notification.showNotification("Introduceti date valide!", Alert.AlertType.ERROR);

        }


    }

    public void searchDonator(KeyEvent keyEvent) {

        try{
            if(searchField.getText()!=null){
                String nume=searchField.getText();
                Iterable<Donator> lista= service.getDonatorDupaNume(nume);
                modelDon.setAll((Collection<? extends Donator>) lista);
            }
        }
        catch (NullPointerException ex){
            return;
        }
    }

    public void afiseazaDateDonator(MouseEvent mouseEvent) {
        try{
            Donator don = donatorTable.getSelectionModel().getSelectedItem();
            numeDonator.setText(don.getNume());
            prenumeDonator.setText(don.getPrenume());
            adresaDon.setText(don.getAdresa());

            telefonDonator.setText(String.valueOf(don.getNrTelefon()));
        }
        catch (NullPointerException ex){
            return;
        }
    }

    public void exitApp(ActionEvent actionEvent) {

        logout();

    }

    public IObserver getObserver(){ return this;}

    public void setServer(IService server) {
        this.service=server;
    }

    @Override
    public void refreshCazuri(Donatie cazuri) throws ServiceException {
        Platform.runLater(()-> {
            setCazuri();
            setDonatori();
            tableView.setItems(model);
                }
        );

        System.out.println("refresh");

    }

    public void logout() {

        try {
            service.logout(actual,this);
            Stage stage = (Stage) butonIesire.getScene().getWindow();
            stage.close();
            loginStage.show();
        } catch (ServiceException e) {
            e.printStackTrace();
        }




    }


}
