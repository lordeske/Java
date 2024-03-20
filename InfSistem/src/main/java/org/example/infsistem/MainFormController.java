    package org.example.infsistem;

    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.Stage;

    import java.io.IOException;
    import java.net.SocketTimeoutException;
    import java.net.URL;
    import java.sql.*;
    import java.util.*;

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
        private ComboBox<String> statusProz;

        @FXML
        private TableView<ProductData> tabela;

        @FXML
        private TableColumn<ProductData,String> tabelacena;

        @FXML
        private TableColumn<ProductData,String> tabelaid;

        @FXML
        private TableColumn<ProductData,String> tabelakolicina;

        @FXML
        private TableColumn<ProductData,String> tabelastatus;

        @FXML
        private TableColumn<ProductData,String> tabelatip;

        @FXML
        private TableColumn<ProductData,String> tabelaslika;

        @FXML
        private TableColumn<ProductData,String> tebalaime;

        @FXML
        private ComboBox<String> tipProiz;

        @FXML
        private Button umetni;

        @FXML
        private Label username;

        private Alert alert;


        private Connection connection;
        private  PreparedStatement prepare;
        private Statement statement;
        private ResultSet result;



        public ObservableList <ProductData> inventoryDataList() throws SQLException {


          ObservableList<ProductData> listdata = FXCollections.observableArrayList();

            String sql = "SELECT * FROM proizvodi";

            connection = DatabaseProizvodi.connectionP();

            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData produktdata;

            while (result.next())
            {
                produktdata = new ProductData(result.getInt("id"), result.getInt("KolicinaProizvoda"),result.getString("StatusProizvoda"),result.getString("SlikaProizvoda"),
                        result.getDouble("CenaProizvoda"),result.getString("TIPPROIZVODA"),result.getString("IMEProizvoda"));

                listdata.add(produktdata);

            }



            return listdata;
        }

        private ObservableList <ProductData> InvListData;

        public void  prikaziTabeluData() throws SQLException {
            InvListData = inventoryDataList();

            tabelaid.setCellValueFactory(new PropertyValueFactory<>("id"));
            tabelakolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
            tabelastatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            tabelaslika.setCellValueFactory(new PropertyValueFactory<>("slika"));
            tabelacena.setCellValueFactory(new PropertyValueFactory<>("cena"));
            tabelatip.setCellValueFactory(new PropertyValueFactory<>("tip"));
            tebalaime.setCellValueFactory(new PropertyValueFactory<>("ime"));
            tabela.setItems(InvListData);




        }



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




        public void displayPocetna()
        {
            formaPocetna.setVisible(true);
            formaZaposleni.setVisible(false);


        }


        public void displayZaspoleni()
        {
            formaPocetna.setVisible(false);
            formaZaposleni.setVisible(true);


        }

        public void dodajStatuse()
        {


            statusProz.getItems().addAll("Dostupan","Nedostupan");
            tipProiz.getItems().addAll("Pice","Hrana");


        }









        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {


            displayUser();
            dodajStatuse();
            try {
                prikaziTabeluData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }





    }
