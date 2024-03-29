package org.example.infsistem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class KarticaNarudzbi implements Initializable {
    @FXML
    private Label HranaText;

    @FXML
    private Label VlasnikRacunaText;

    @FXML
    private Label datumRacunaText;
    private RacunData racunData;


    public void setDataRacun(RacunData racunData)
    {

        this.racunData = racunData;

        HranaText.setText(racunData.getHrana());
        VlasnikRacunaText.setText(racunData.getImeKupca());
        datumRacunaText.setText(racunData.getDatum());


    }















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
