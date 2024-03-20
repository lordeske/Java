package org.example.infsistem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @FXML
    private Button azuriraj;

    @FXML
    private Button btnInvertar;

    @FXML
    private Button btnMeni;

    @FXML
    private Button btnNarudzbine;

    @FXML
    private Button btnPocetna;

    @FXML
    private Button btnZaposleni;

    @FXML
    private TextField cenaProiz;

    @FXML
    private Button dodaj;

    @FXML
    private AnchorPane formaPocetna;

    @FXML
    private AnchorPane formaZaposleni;

    @FXML
    private TextField idProiz;

    @FXML
    private TextField imeProiz;

    @FXML
    private ImageView imgViewer;

    @FXML
    private TextField kolicinaProiz;

    @FXML
    private Button obrisi;

    @FXML
    private Button ocisti;

    @FXML
    private Button odjava;


    @FXML
    private ComboBox<?> statusProz;

    @FXML
    private TableView<?> tabela;

    @FXML
    private TableColumn<?, ?> tabelacena;

    @FXML
    private TableColumn<?, ?> tabelaid;

    @FXML
    private TableColumn<?, ?> tabelakolicina;

    @FXML
    private TableColumn<?, ?> tabelastatus;

    @FXML
    private TableColumn<?, ?> tabelatip;

    @FXML
    private TableColumn<?, ?> tebalaime;

    @FXML
    private ComboBox<?> tipProiz;

    @FXML
    private Button umetni;

    @FXML
    private Label username;

    private Alert alert;



    public void displayUser()
    {

        String user = data.username;
        username.setText("Zdravo "+ user );

    }


    public void logOut() throws IOException {

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Odjava");
        alert.setHeaderText(null);
        alert.setContentText("Da li zelite da se odjavite");
        Optional <ButtonType> option = alert.showAndWait();

        if(option.get().equals(ButtonType.OK))
        {

            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));


            odjava.getScene().getWindow().hide();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Prijavna strana");

            stage.setScene(scene);
            stage.show();





        }



    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        displayUser();



    }





}
