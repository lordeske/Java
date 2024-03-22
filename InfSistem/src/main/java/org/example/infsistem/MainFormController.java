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
            username.setText("Zdravo "+ user + "(Admin)" );

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
            FormaMeni.setVisible(false);




        }

        public void ispisiUPolja()
        {

            ProductData prd = tabela.getSelectionModel().getSelectedItem();  // vrca objekat producdata
            Integer i = tabela.getSelectionModel().getSelectedIndex(); ///vraca index

            if ((i - 1) < -1 ) return;  // Pocinje od nule ako je manji od nula nista

            imeProiz.setText(prd.getIme());
            cenaProiz.setText(String.valueOf(prd.getCena())); // Kastovanje u string
            kolicinaProiz.setText(String.valueOf(prd.getKolicina()));
            tipProiz.setValue(prd.getTip());
            statusProz.setValue(prd.getStatus());

            //System.out.println(prd.getSlika());

            data.path = prd.getSlika();

            Image image1 = new Image("File:" + data.path,120,165,false,true);
            imgViewer.setImage(image1);




        }


        public void apdejtuj() throws SQLException {

            if (imeProiz.getText().isEmpty() || cenaProiz.getText().isEmpty() || tipProiz.getSelectionModel().getSelectedItem() == null ||
                    statusProz.getSelectionModel().getSelectedItem() == null || imeProiz.getText().isEmpty() || kolicinaProiz.getText().isEmpty() ||
                    data.path.isEmpty()) {
                alertMes.failMess("Molimo unesite sva polja");
                return;

            } else
            {

                String updateSQL = "UPDATE proizvodi SET TIPPROIZVODA=?, IMEProizvoda=?, KolicinaProizvoda=?, CenaProizvoda=?, StatusProizvoda=?, SlikaProizvoda=? WHERE IMEProizvoda=?";

                connection = DatabaseProizvodi.connectionP();

                prepare = connection.prepareStatement(updateSQL);

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Odjava");
                alert.setHeaderText(null);
                alert.setContentText("Da li ste sigurni da zelite da azurirate " + imeProiz.getText() + " proizovd");
                Optional <ButtonType> option = alert.showAndWait();


                if(option.get().equals(ButtonType.OK))
                {
                    prepare.setString(1, tipProiz.getSelectionModel().getSelectedItem());
                    prepare.setString(2, imeProiz.getText());
                    prepare.setString(3, kolicinaProiz.getText());
                    prepare.setString(4, cenaProiz.getText());
                    prepare.setString(5, statusProz.getSelectionModel().getSelectedItem());
                    prepare.setString(6, data.path);
                    prepare.setString(7, imeProiz.getText());

                    alertMes.successMes("Uspejsno ste azurirali proizvod " + imeProiz.getText());

                    prepare.executeUpdate();
                    prikaziTabeluData();
                    ocisti();

                }






            }




        }


        public ObservableList<ProductData> meniGetdata() throws SQLException {

            /// Uzmi iz baze podatke svih prozivda i smjesti ih u listu opet!!


            ObservableList<ProductData> cardListData = FXCollections.observableArrayList();


            String sql = "SELECT * FROM proizvodi";
            connection = DatabaseProizvodi.connectionP();

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

            cardListData.clear();
            cardListData.addAll(meniGetdata());

            Integer col = 0;
            Integer row = 0;

            MeniGridPane.getRowConstraints().clear();
            MeniGridPane.getColumnConstraints().clear();

            for(Integer i = 0; i<cardListData.size(); i++)
            {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("karticaProizvoda.fxml"));
                AnchorPane pane = load.load();
                KarticaProizvodaController kardC = load.getController();
                kardC.setData(cardListData.get(i));


                if(col==3)
                {
                    col=0;
                    row+=1;

                }

                MeniGridPane.add(pane,col++,row);

            }



        }





        public void displayInvertar()
        {
            formaPocetna.setVisible(false);
            FormaInvertar.setVisible(true);
            FormaMeni.setVisible(false);


        }

        public void displayMeni()
        {
            formaPocetna.setVisible(false);
            FormaInvertar.setVisible(false);
            FormaMeni.setVisible(true);


        }

        public void dodajStatuse()
        {


            statusProz.getItems().addAll("Dostupan","Nedostupan");
            tipProiz.getItems().addAll("Pice","Hrana");


        }


        public void dodajUTabelu() throws SQLException, IOException {

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
                    ocisti();
                    alertMes.successMes("Uspjesno ste dodali proizvod");
                    popuniMeni();

                }










            }



        }



        public void ocisti()
        {

            imeProiz.setText("");
            cenaProiz.setText("");
            kolicinaProiz.setText("");
            imgViewer.setImage(null);
            statusProz.getSelectionModel().clearSelection();
            tipProiz.getSelectionModel().clearSelection();
            data.path = null; // cisti objekat


        }


        public void umetni()
        {

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg"));


           File file = fc.showOpenDialog(main_form.getScene().getWindow());


           if(file != null)
           {
                data.path =  file.getAbsolutePath();
                image = new Image(file.toURI().toString(),120,140,false,true);

                imgViewer.setImage(image);


           }



        }




        public void obrisi() throws SQLException, IOException {
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

                if(!result.next())
                {
                    alertMes.failMess("Proizovd " + imeProiz.getText() + " proizvod ne postoji");
                    return;


                }
                else
                {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Odjava");
                    alert.setHeaderText(null);
                    alert.setContentText("Da li ste sigurni da zelite da obrisete proizvod " + imeProiz.getText());
                    Optional <ButtonType> option = alert.showAndWait();


                    if(option.get().equals(ButtonType.OK))
                    {
                        String url = data.path;

                        connection = DatabaseProizvodi.connectionP();

                        String deleteProizvodi = "DELETE FROM proizvodi WHERE IMEProizvoda = ?";

                        prepare = connection.prepareStatement(deleteProizvodi);
                        prepare.setString(1, imeProiz.getText());

                        Integer delRedovi = prepare.executeUpdate();

                        if(delRedovi > 0)
                        {
                            alertMes.successMes("Uspješno ste obrisali proizvod " + imeProiz.getText());
                            prikaziTabeluData();
                            ocisti();
                            popuniMeni();

                        }
                        else
                        {
                            alertMes.failMess("Greska prilikom brisanja proizvoda");

                        }


                    }






                }










            }



        }




        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {


            displayUser();
            dodajStatuse();
            try {
                prikaziTabeluData();
                popuniMeni();

            } catch (SQLException e) {
                throw new RuntimeException(e);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }}








