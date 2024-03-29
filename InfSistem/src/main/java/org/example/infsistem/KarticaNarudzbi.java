package org.example.infsistem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class KarticaNarudzbi implements Initializable {
    @FXML
    private Label HranaText;
    Connection connect;
    PreparedStatement prepare;
    ResultSet result;

    @FXML
    private Label VlasnikRacunaText;

    @FXML
    private Label datumRacunaText;
    private RacunData racunData;
    @FXML
    private Label idRacuna;


    public void setDataRacun(RacunData racunData)
    {

        this.racunData = racunData;
        idRacuna.setText(String.valueOf(racunData.getIdNarudzbe()));
        HranaText.setText(racunData.getHrana());
        VlasnikRacunaText.setText(racunData.getImeKupca());
        datumRacunaText.setText(racunData.getDatum());


    }

    public void obrisiRacun() throws SQLException {


        String sql = "DELETE FROM racun WHERE idNarudzbe = ?";

        connect = Database.connectDB();
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, idRacuna.getText());


        Integer rows = prepare.executeUpdate();

        if(rows > 0)
        {
            System.out.printf("obrisan je racun");
        }









    }















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
