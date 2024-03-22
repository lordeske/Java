package org.example.infsistem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class KarticaProizvodaController implements Initializable {

    @FXML
    private Label CenaHraneText;

    @FXML
    private ImageView SlikaHrane;

    @FXML
    private Label imeHraneText;

    private ProductData prodData;

    private Image image;


    public void setData(ProductData prodData) /// Uzimamo nas objekat i podatke iz njega postavljamo u nas card

    {
        this.prodData = prodData;

        imeHraneText.setText(prodData.getIme());
        CenaHraneText.setText(String.valueOf(prodData.getCena()));
        image =new Image("File:" + prodData.getSlika(),190,94,false,true);
        SlikaHrane.setImage(image);



    }




































    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
