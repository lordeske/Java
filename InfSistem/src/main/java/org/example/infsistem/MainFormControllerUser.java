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
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
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
        private TableColumn<ProductData, String> cenaProizovdaKolona;

        @FXML
        private TableColumn<ProductData, String> imeProizvodaKolona;

        @FXML
        private TableColumn<ProductData, String> kolicinaProizovdaKolona;

        @FXML
        private TableView<ProductData> tabelaKorisnik;

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
            showData();
            displayMenuTotal();


        }







        public ObservableList<ProductData> displayMenuOrder() throws SQLException {

            ObservableList<ProductData> dataList = FXCollections.observableArrayList();


            String SQL = "SELECT * FROM porudzbine";
            connection = Database.connectDB();
            prepare = connection.prepareStatement(SQL);
            result = prepare.executeQuery();

            while (result.next())
            {

                ProductData pr = new ProductData(result.getString("ime"),result.getDouble("cena"),result.getInt("kolicina"));


                dataList.add(pr);

            }



            return dataList;

        }





        public ObservableList<ProductData>MenuListData;


        public void showData() throws SQLException { /// Prikazivanje u korpu

            MenuListData = displayMenuOrder();

            imeProizvodaKolona.setCellValueFactory(new PropertyValueFactory<>("ime"));
            kolicinaProizovdaKolona.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
            cenaProizovdaKolona.setCellValueFactory(new PropertyValueFactory<>("cena"));


            tabelaKorisnik.setItems(MenuListData);



        }

        public double totalP;
        public void displayMenuTotal() throws SQLException {

            String sql = "SELECT SUM(cena) AS ukupna_cijena FROM porudzbine WHERE imeCovjeka = ?";

            connection = Database.connectDB();
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, data.username);
            result = prepare.executeQuery();



            if(result.next())
            {

              totalP = result.getDouble("ukupna_cijena");

            }
            UkupnoTekst.setText(String.valueOf(totalP)+"€");




        }

        public void obrisiIzTabelice() throws SQLException {

            ProductData pr = tabelaKorisnik.getSelectionModel().getSelectedItem();


            if(pr == null)
            {
                alertMes.failMess("Morate izabrati proizvod");
                return;
            }



            String sql = "DELETE FROM porudzbine WHERE idPrdzine = ? AND cena = ? AND kolicina = ?";
            connection = Database.connectDB();
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, pr.getIme());
            prepare.setDouble(2, pr.getCena());
            prepare.setInt(3, pr.getKolicina());
            prepare.executeUpdate();


            showData();
            displayMenuTotal();








        }







        public void  posaljiRecept() throws SQLException {

            /// dobijanje racuna
            String sql =  "SELECT * FROM racun WHERE imeKupca =?";

            connection =Database.connectDB();
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, data.username);
            result = prepare.executeQuery();

            if(result.next())
            {
                String datum = result.getString("datum");
                String ukupnaCena = result.getString("ukupnaCena");

                String tesktZaSlanje = "Postovani " +data.username + " dana " + datum + " narucili ste hranu u vrijednosti od "
                        + ukupnaCena;


                /// dobijanje maila
                String sql2 =  "SELECT email FROM korisnici WERE ime =?";
                connection =Database.connectDB();
                prepare = connection.prepareStatement(sql);
                prepare.setString(1, data.username);
                result = prepare.executeQuery();

                String email =  result.getString("email");


                EmailSender.sendEmail(email,"Porudzbina",tesktZaSlanje);
                alertMes.successMes("Racun vam je stigao na mail");


            }



        }



        private  Double povrat;
        public void uplata() throws SQLException {

            if(uplacenoPolje.getText().isEmpty())
            {
                alertMes.failMess("Molimo vas unesite kolicinu za uplatu");
                return;
            }
            else if(Integer.parseInt(uplacenoPolje.getText() ) < totalP)
            {
                alertMes.failMess("Nemate dovoljno novca!! ");
                return;
            }

            else
            {

                Double povrat =  totalP -  Integer.parseInt(uplacenoPolje.getText()) ;

                povratText.setText(String.valueOf(Math.abs(povrat)) + "€");


            }
        }



        public void plati() throws SQLException {
            if (totalP == 0) {
                alertMes.failMess("Narucite prozivod prvo");
                return;

            } else if (uplacenoPolje.getText().isEmpty()) {
                alertMes.failMess("Uplatite novac");
                return;
            }
            else
            {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Povrda");
                alert.setHeaderText(null);
                alert.setContentText("Da li li zelite da platite?");
                Optional<ButtonType> opt = alert.showAndWait();


                if(opt.get().equals(ButtonType.OK))
                {
                    LocalDate trenutniDatum = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formatiraniDatum = trenutniDatum.format(formatter);

                    String plati  = "INSERT INTO racun (imeKupca,ukupnaCena, datum) VALUES (?,?,?)";
                    connection = Database.connectDB();
                    prepare = connection.prepareStatement(plati);
                    prepare.setString(1, data.username);
                    prepare.setString(2, String.valueOf(totalP));
                    prepare.setString(3, formatiraniDatum);
                    prepare.executeUpdate();
                    alertMes.successMes("Uspjesno ste narucili narudzbu");


                    ///// OCISTI CART
                    String obrisiNarudbu = "DELETE FROM porudzbine WHERE imeCovjeka = ?";
                    connection = Database.connectDB();

                    prepare = connection.prepareStatement(obrisiNarudbu);
                    prepare.setString(1, data.username);
                    prepare.executeUpdate();

                    showData();
                    displayMenuTotal();
                    povratText.setText("0.00");
                    uplacenoPolje.setText(null);

                    posaljiRecept();








                }
                else
                {
                    alertMes.failMess("Otkazano");
                }








            }


        }














        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {


            displayUser();

            try {
                popuniMeni();
                showData();
                displayMenuTotal();

            } catch (SQLException e) {
                throw new RuntimeException(e);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }}








