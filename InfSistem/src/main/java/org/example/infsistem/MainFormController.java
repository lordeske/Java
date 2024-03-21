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
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;
    import org.example.infsistem.DatabaseProizvodi;
    import org.example.infsistem.ProductData;
    import org.example.infsistem.data;

    import java.io.File;
    import java.io.IOException;
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
        private AnchorPane FormaInvertar;


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
        private AnchorPane main_form;

        @FXML
        private Button umetni;

        @FXML
        private Label username;

        private Alert alert;


        private Connection connection;
        private  PreparedStatement prepare;
        private Statement statement;
        private ResultSet result;
        private Image image;

        AlertMes alertMes = new AlertMes();



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
            FormaInvertar.setVisible(false);


        }


        public void displayInvertar()
        {
            formaPocetna.setVisible(false);
            FormaInvertar.setVisible(true);


        }

        public void dodajStatuse()
        {


            statusProz.getItems().addAll("Dostupan","Nedostupan");
            tipProiz.getItems().addAll("Pice","Hrana");


        }


        public void dodajUTabelu() throws SQLException {

            if (imeProiz.getText().isEmpty() || cenaProiz.getText().isEmpty() || tipProiz.getSelectionModel().getSelectedItem() == null ||
            statusProz.getSelectionModel().getSelectedItem() == null || imeProiz.getText().isEmpty() ||  kolicinaProiz.getText().isEmpty() ||
            data.path.isEmpty())
            {
                alertMes.failMess("Molimo unesite sva polja");
                return;

            }
            else
            {
                connection = DatabaseProizvodi.connectionP();

                String checkName = "SELECT * FROM proizvodi WHERE IMEProizvoda = ?" ;

                prepare = connection.prepareStatement(checkName);
                prepare.setString(1, imeProiz.getText());
                result = prepare.executeQuery();

                if(result.next())
                {
                    alertMes.failMess("Proizovd " + imeProiz.getText() + " vec postoji");
                    return;


                }
                else
                {
                    String url = data.path;

                    connection = DatabaseProizvodi.connectionP();

                    String insertProizvodi = "INSERT INTO proizvodi " + "(TIPPROIZVODA, IMEProizvoda, KolicinaProizvoda, CenaProizvoda, StatusProizvoda, SlikaProizvoda)" + "VALUES(?,?,?,?,?,?)";

                    prepare = connection.prepareStatement(insertProizvodi);
                    prepare.setString(1, tipProiz.getSelectionModel().getSelectedItem());
                    prepare.setString(2, imeProiz.getText());
                    prepare.setString(3, kolicinaProiz.getText());
                    prepare.setString(4, cenaProiz.getText());
                    prepare.setString(5, statusProz.getSelectionModel().getSelectedItem());
                    prepare.setString(6, url);
                    prepare.executeUpdate();

                    prikaziTabeluData();

                }










            }



        }


        public void umetni()
        {

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg"));


           File file = fc.showOpenDialog(main_form.getScene().getWindow());


           if(file != null)
           {
                data.path =  file.getAbsolutePath();
                image = new Image(file.toURI().toString(),120,165,false,true);

                imgViewer.setImage(image);


           }



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
