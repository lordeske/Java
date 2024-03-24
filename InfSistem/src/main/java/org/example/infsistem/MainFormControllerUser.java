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
    import javafx.scene.layout.GridPane;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;

    import java.io.File;
    import java.io.IOException;
    import java.net.URL;
    import java.sql.*;
    import java.util.Optional;
    import java.util.ResourceBundle;

    public class MainFormControllerUser implements Initializable {


        @FXML
        private Label UkupnoTekst;
        @FXML
        private Button btnObrisi;

        @FXML
        private Button btnPlati;


        @FXML
        private Button btnRecept;

        @FXML
        private TableColumn<?, ?> cenaProizovdaKolona;

        @FXML
        private TableColumn<?, ?> imeProizvodaKolona;

        @FXML
        private TableColumn<?, ?> kolicinaProizovdaKolona;

        @FXML
        private TableView<?> tabelaKorisnik;

        @FXML
        private TextField uplacenoPolje;




        @FXML
        private Label povratText;







        @FXML
        private Button azuriraj;
        @FXML
        private GridPane MeniGridPane;

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
        private AnchorPane FormaMeni;


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

        ObservableList<ProductData> cardListData = FXCollections.observableArrayList();


        private Connection connection;
        private  PreparedStatement prepare;
        private Statement statement;
        private ResultSet result;
        private Image image;

        AlertMes alertMes = new AlertMes();



        private ObservableList <ProductData> InvListData;





        public void displayUser()
        {

            String user = data.username;
            username.setText("Zdravo "+ user + "(User)" );

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
            FormaMeni.setVisible(false);




        }







        public ObservableList<ProductData> meniGetdata() throws SQLException {

            /// Uzmi iz baze podatke svih prozivda i smjesti ih u listu opet!!


            ObservableList<ProductData> cardListData = FXCollections.observableArrayList();


            String sql = "SELECT * FROM proizvodi";
            connection = Database.connectDB();

            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData productData1;


            while (result.next())
            {
                productData1 = new ProductData(result.getString("IMEProizvoda"),result.getDouble("CenaProizvoda"),result.getString("SlikaProizvoda"));
                cardListData.add(productData1);
            }

            return cardListData;
        }



        public void popuniMeni() throws SQLException, IOException {
            MeniGridPane.getChildren().clear();

            cardListData.clear();
            cardListData.addAll(meniGetdata());

            Integer col = 0;
            Integer row = 0;

            MeniGridPane.getRowConstraints().clear();
            MeniGridPane.getColumnConstraints().clear();

            for(Integer i = 0; i<cardListData.size(); i++)
            {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("karticaProizvodaUser.fxml"));
                AnchorPane pane = load.load();
                KarticaProizvodaControllerUser kardC = load.getController();
                kardC.setData(cardListData.get(i));

                MeniGridPane.add(pane,col++,row);

                if(col==2)
                {
                    col=0;
                    row+=1;

                }

                System.out.printf("OBRISANOO");





            }



        }





        public void displayInvertar()
        {
            formaPocetna.setVisible(false);

            FormaMeni.setVisible(false);


        }

        public void displayMeni() throws SQLException, IOException {

            formaPocetna.setVisible(false);

            FormaMeni.setVisible(true);


        }





























        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {


            displayUser();

            try {
                popuniMeni();

            } catch (SQLException e) {
                throw new RuntimeException(e);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }}








