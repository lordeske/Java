package org.example.infsistem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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


    public void promeni()
    {

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
        else
        {
            


        }

    }
















}
