package org.example.infsistem;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMes {

    Alert alert;

    public void successMes(String Mess)
    {

       alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Informacijska poruka");

       alert.setHeaderText(null);
       alert.setContentText(Mess);
       alert.show();


    }


    public void failMess(String Mess)
    {
        alert  = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacijska poruka");
        alert.setHeaderText(null);
        alert.setContentText(Mess);
        alert.show();

    }



    public boolean confirmMess(String Mess)
    {
        alert  = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Poruka sa potvrdom");
        alert.setHeaderText(null);
        alert.setContentText(Mess);
        alert.show();

       Optional<ButtonType> option = alert.showAndWait();

       return option.get().equals(ButtonType.OK);

    }


}
