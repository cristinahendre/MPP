package Controller;

import Domain.CazCaritabil;
import Domain.Donator;
import Domain.Voluntar;
import Service.ServiceException;
import Service.SuperService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;

public class MainController {
    private SuperService service;

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

    public void setServices(SuperService service, Stage loginStage){
        this.service=service;
        setCazuri();
        setDonatori();
        this.loginStage=loginStage;

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

                    service.saveDonatie(caz, salvat, suma);


                }
                Notification.showNotification("Succes!", Alert.AlertType.CONFIRMATION);
                setCazuri();
                setDonatori();
                return;
            }
            else{
                for(CazCaritabil caz: cazuri) {
                    service.saveDonatie(caz, donator, suma);
                }
                Notification.showNotification("Succes!", Alert.AlertType.CONFIRMATION);
                setCazuri();

            }


        }
        catch (Exception ex  ){
            Notification.showNotification("Introduceti date valide!", Alert.AlertType.ERROR);

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

        Stage stage = (Stage) butonIesire.getScene().getWindow();
        stage.close();
        loginStage.show();
    }
}
