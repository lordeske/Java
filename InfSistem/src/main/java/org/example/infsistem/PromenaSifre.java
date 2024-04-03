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
        if (trenutnaSifra.getText().isEmpty() || novaReSifra.getText().isEmpty() || novaSifra.getText().isEmpty()) {
            alertMes.failMess("Unesite Podatke");
            return;
        } else if (novaSifra.getText().length() < 8) {
            alertMes.failMess("Nova šifra mora imati bar 8 karaktera");
            return;
        } else if (!novaSifra.getText().equals(novaReSifra.getText())) {
            alertMes.failMess("Šifre se ne poklapaju!");
            return;
        }

        String sql1 = "SELECT lozinka from korisnici WHERE ime = ?";
        try (Connection connection = Database.connectDB();
             PreparedStatement prepare = connection.prepareStatement(sql1)) {
            prepare.setString(1, data.username);
            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    String lozinka = result.getString("lozinka");
                    System.out.printf(lozinka,trenutnaSifra.getText(),novaReSifra.getText());
                    if (!lozinka.equals(trenutnaSifra.getText())) {
                        alertMes.failMess("Morate unjeti staru sifru tacnu");
                        return;
                    } else {
                        String sql = "UPDATE korisnici SET lozinka = ? WHERE ime = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
                            updateStatement.setString(1, novaSifra.getText());
                            updateStatement.setString(2, data.username);
                            int info = updateStatement.executeUpdate();
                            if (info > 0) {
                                alertMes.successMes("Šifra promenjena");
                                trenutnaSifra.getScene().getWindow().hide();
                            }
                        }
                    }
                } else {
                    alertMes.failMess("Netacni podaci");
                }
            }
        }
    }















}
