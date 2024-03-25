package org.example.infsistem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    AlertMes alertMes = new AlertMes();

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
        pr =prodData.getCena();



    }


    public void setQuant()
    {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        brojacKolicine.setValueFactory(spin);

    }

    private double total;
    private double pr;

    public void UmetniUKasu() throws SQLException {

        //////////// PRVO PROVJERAVAMO KOLICINU NJEU I DOSTUPONOST ZA SVAKI PROIZCVOD


        qnt = brojacKolicine.getValue();/// Unos sa spinera
        String check = "";

        connect = Database.connectDB();
        String dosputpnost = "SELECT StatusProizvoda FROM proizvodi WHERE IMEProizvoda = ?";
        prepare = connect.prepareStatement(dosputpnost);
        prepare.setString(1, imeHraneText.getText());
        result = prepare.executeQuery();



        if(result.next())
        {
         check = result.getString("StatusProizvoda");

        }

        if(!check.equals("Dostupan") || qnt==0)
        {
            AlertMes alertMes = new AlertMes();
            alertMes.failMess("Greska");
            return;
        }

        //// AKO JE DOSTUPAN I NIE 0 RADI SLEDECE!!!

        else
        {
            String kolicina = "SELECT KolicinaProizvoda FROM proizvodi WHERE IMEProizvoda = ?" ;
            Integer kolicinaUBazi = 0;


            connect = Database.connectDB();
            prepare = connect.prepareStatement(kolicina);
            prepare.setString(1, imeHraneText.getText());
            result = prepare.executeQuery();

            if(result.next())
            {
                kolicinaUBazi = result.getInt("KolicinaProizvoda");

            }


            if(kolicinaUBazi < qnt)
            {
                alertMes.failMess("Nema dovoljo proizovda");

            }
            else
            {


                //// AKo je sve ok, ima u bazi i korisnik je izabrao umetni u bazu narudzbe ali oduzmi iz baze kupljeni prozivod!!!
                String upit = "INSERT INTO porudzbine (ime,kolicina,cena,imeCovjeka) VALUES (?,?,?,?)";

                connect = Database.connectDB();
                prepare = connect.prepareStatement(upit);
                prepare.setString(1, imeHraneText.getText());
                prepare.setString(2, String.valueOf(qnt));
                total = (qnt* pr);
                prepare.setString(3, String.valueOf(total));
                prepare.setString(4, data.username);
                prepare.executeUpdate();


                Integer isUPStock = kolicinaUBazi - qnt;

                // ali oduzmi iz baze kupljeni prozivod!!!

                String upit2 = "UPDATE proizvodi SET KolicinaProizvoda = ? WHERE IMEProizvoda = ?";
                connect = Database.connectDB();
                prepare = connect.prepareStatement(upit2);
                prepare.setString(1, String.valueOf(isUPStock));
                prepare.setString(2, imeHraneText.getText());

                prepare.executeUpdate();




                AlertMes alertMes = new AlertMes();
                alertMes.successMes("Uspjesno dodato");

            }






        }



    }
































    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        setQuant()

        ;
    }
}
