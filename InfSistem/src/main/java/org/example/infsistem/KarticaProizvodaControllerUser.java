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


        String kolicina = "SELECT KolicinaProizvoda FROM proizvodi WHERE IMEProizvoda = ?" ;

        Integer kolicinaUBazi = 0;
        String check = "";
        qnt = brojacKolicine.getValue();/// Unos sa spinera


        connect = Database.connectDB();
        prepare = connect.prepareStatement(kolicina);
        prepare.setString(1, imeHraneText.getText());
        result = prepare.executeQuery();

        if(result.next())
        {
            kolicinaUBazi = result.getInt("KolicinaProizvoda");

        }

        Integer isUPStock = kolicinaUBazi - qnt;

        if(kolicinaUBazi ==0)
        {
            String upit2 = "UPDATE proizvodi SET StatusProizvoda = ? WHERE IMEProizvoda = ?";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(upit2);
            prepare.setString(1, "Nedostupan");
            prepare.setString(2, imeHraneText.getText());
            prepare.executeUpdate();
            System.out.printf("SREDUI");

        }

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
        else
        {




            if(kolicinaUBazi < qnt)
            {
                alertMes.failMess("Nema dovoljo proizovda");
                return;

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



                // ali oduzmi iz baze kupljeni prozivod!!!

                String upit2 = "UPDATE proizvodi SET KolicinaProizvoda = ? WHERE IMEProizvoda = ?";
                connect = Database.connectDB();
                prepare = connect.prepareStatement(upit2);
                prepare.setString(1, String.valueOf(isUPStock));
                prepare.setString(2, imeHraneText.getText());

                prepare.executeUpdate();




                AlertMes alertMes = new AlertMes();
                alertMes.successMes("Uspjesno dodato");

                MainFormControllerUser m1= new MainFormControllerUser();
                m1.showData();
                m1.displayMenuTotal();

            }






        }



    }
































    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        setQuant()

        ;
    }
}
