package org.example.infsistem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class KarticaProizvodaControllerUser implements Initializable {

    @FXML
    private Label CenaHraneText;

    @FXML
    private ImageView SlikaHrane;

    @FXML
    private Label imeHraneText;

    private ProductData prodData;

    private Image image;

    @FXML
    private Spinner<Integer> brojacKolicine;

    @FXML
    private Button btnDodajUkasu;

    private SpinnerValueFactory<Integer> spin;

    private Integer qnt;

    Connection connect;
    PreparedStatement prepare;
    ResultSet result;


    public void setData(ProductData prodData) /// Uzimamo nas objekat i podatke iz njega postavljamo u nas card

    {
        this.prodData = prodData;

        imeHraneText.setText(prodData.getIme());
        CenaHraneText.setText(String.valueOf(prodData.getCena()));
        image =new Image("File:" + prodData.getSlika(),190,94,false,true);
        SlikaHrane.setImage(image);



    }


    public void setQuant()
    {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        brojacKolicine.setValueFactory(spin);

    }



    public void UmetniUKasu() throws SQLException {

        qnt = brojacKolicine.getValue();

        String dosputpnost ="SELECT StatusProizvoda from proizvodi where IMEProizvoda "+ imeHraneText ;
        // Udmah na logu svaka stavka mijenja svoje ime i mi dobijamo od svake


        connect = Database.connectDB();

        prepare = connect.prepareStatement(dosputpnost);
        result = prepare.executeQuery();

        if(result.next())
        {





        }

        

    }
































    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
