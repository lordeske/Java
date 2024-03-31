package org.example.infsistem;
import javafx.fxml.FXMLLoader;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PromenaSifre {

    @FXML
    private TextField novaReSifra;

    @FXML
    private TextField novaSifra;

    @FXML
    private Button promeniSifruBTN;

    @FXML
    private AnchorPane promjenaForma;

    @FXML
    private TextField trenutnaSifra;

    AlertMes alertMes = new AlertMes();

    Connection connection;
    ResultSet result;
    PreparedStatement prepare;




    public void promeni() throws SQLException {

        if(trenutnaSifra.getText().isEmpty() || novaReSifra.getText().isEmpty() || novaSifra.getText().isEmpty())
        {
            alertMes.failMess("Unesite Podatke");
            return;
        }
        else if (novaSifra.getText().length() < 8 )
        {
            alertMes.failMess("Nova sifra mora imati bar 8 karaktera");
            return;
        }
        else if (!novaSifra.getText().equals(novaReSifra.getText()))
        {
            alertMes.failMess("Sifre se ne poklapaju!");
            return;

        }
        else
        {
            String sql1 = "SELECT lozinka from korisnici WHERE ime = ?";
            connection = Database.connectDB();
            prepare = connection.prepareStatement(sql1);
            prepare.setString(1,data.username);
            result = prepare.executeQuery();

            String lozinka = null;

            if (result.next())
            {
                lozinka = result.getString("lozinka");
                System.out.printf(lozinka);

            }

            if(lozinka.equals(trenutnaSifra.getText()))
            {
                alertMes.failMess("Ne moze ista sifra");
                return;
            }

            if (lozinka.equals(trenutnaSifra.getText()))
            {
                String sql = "UPDATE korisnici SET lozinka = ? WHERE ime = ?";

                connection = Database.connectDB();
                prepare = connection.prepareStatement(sql);
                prepare.setString(1,novaSifra.getText());
                prepare.setString(2,data.username);

                Integer info = prepare.executeUpdate();

                if(info > 0)
                {
                    alertMes.successMes("Sifra promenjena");
                    trenutnaSifra.getScene().getWindow().hide();


                }

            }
            else
            {
                alertMes.failMess("Netacna trenunta  sifra");
            }






        }

    }
















}
