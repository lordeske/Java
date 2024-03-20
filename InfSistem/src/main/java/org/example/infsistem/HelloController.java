package org.example.infsistem;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class HelloController {

    @FXML
    private Button admin_button;

    @FXML
    private AnchorPane admin_form;

    @FXML
    private Hyperlink admin_link;

    @FXML
    private TextField admin_password;

    @FXML
    private TextField admin_repassword;

    @FXML
    private TextField admin_mail;


    @FXML
    private TextField admin_username;

    @FXML
    private AnchorPane form_user;

    @FXML
    private Button login_button;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField login_password;

    @FXML
    private ComboBox<String> login_role;

    @FXML
    private TextField login_username;

    @FXML
    private Button user_button;

    @FXML
    private Hyperlink user_link;

    @FXML
    private TextField user_mail;

    @FXML
    private TextField user_passwrod;

    @FXML
    private TextField user_repassword;

    @FXML
    private TextField user_username;


    private Connection connect;
    private Connection connect1;

    private PreparedStatement prepare;
    private PreparedStatement prepare1;
    private Statement statement;
    private Statement statement1;
    private ResultSet result;
    private ResultSet result1;

    private AlertMes alert = new AlertMes();



    public void swichForm(ActionEvent event)
    {

        if (login_role.getSelectionModel().getSelectedItem().equals("User"))
        {
            form_user.setVisible(true);
            login_form.setVisible(false);
            admin_form.setVisible(false);
            login_role.getSelectionModel().clearSelection();



        } else if (login_role.getSelectionModel().getSelectedItem().equals("Admin")) {

            form_user.setVisible(false);
            login_form.setVisible(false);
            admin_form.setVisible(true);
            login_role.getSelectionModel().clearSelection();


        }
        else
        {
            form_user.setVisible(false);
            login_form.setVisible(true);
            admin_form.setVisible(false);
            login_role.getSelectionModel().clearSelection();

        }


    }



    public void displayLogin(ActionEvent event) {

            login_form.setVisible(true);
            admin_form.setVisible(false);
            form_user.setVisible(false);
            login_role.getSelectionModel().clearSelection();

    }




    public void registerAdmin() throws SQLException {
        if (admin_mail.getText().isEmpty() || admin_password.getText().isEmpty() || admin_repassword.getText().isEmpty() || admin_username.getText().isEmpty())
        {

            alert.failMess("Morate unjeti sva polja");
            return;


        }
        else
        {
            connect = DatabaseAdmin.connectionDBA();



            try {


                statement = connect.createStatement();
                result = statement.executeQuery("SELECT * FROM korisnici WHERE ime = '" + admin_username.getText() + "'");


                if (result.next())
                {
                    alert.failMess(admin_username.getText() + "vec postoji");
                    return;
                }
                else
                {
                    result = statement.executeQuery("SELECT * FROM korisnici WHERE email = '" + admin_username.getText() + "'");

                    if (result.next())
                    {
                        alert.failMess("Admin sa tim mailom vec postoji");
                        return;
                    }
                    else if (!admin_password.getText().equals(admin_repassword.getText())) {

                        alert.failMess("Lozinke se ne poklapaju");

                    }
                    else if (admin_password.getText().length() < 8) {
                        alert.failMess("Lozinka mora sadržati najmanje 8 karaktera");
                        return;
                    }
                    else
                    {
                        String insertdata = "INSERT INTO korisnici (ime,email, prezime, lozinka, status) VALUES (?, ?, ?, ?,?)";
                        prepare = connect.prepareStatement(insertdata);
                        prepare.setString(1,admin_username.getText());
                        prepare.setString(2,admin_mail.getText());
                        prepare.setString(3,"Prezimenovic");
                        prepare.setString(4,admin_password.getText());
                        prepare.setString(5,"Administrator");



                        prepare.executeUpdate();
                        alert.successMes("Uspjesno ste kreirali nalog");


                    }
                }




            }
            catch (Exception e)
            {
                System.out.printf("Nece");
            }

        }






    }



    public void registerUser() {

        if (user_mail.getText().isEmpty() || user_passwrod.getText().isEmpty() || user_repassword.getText().isEmpty() || user_username.getText().isEmpty())
        {

            alert.failMess("Morate unjeti sva polja");
            return;


        }
        else
        {
            connect = Database.connectDB();



            try {


                statement = connect.createStatement();
                result = statement.executeQuery("SELECT * FROM korisnici WHERE ime = '" + user_username.getText() + "'");


                if (result.next())
                {
                    alert.failMess(user_username.getText() + "vec postoji");
                    return;
                }
                else
                {
                    result = statement.executeQuery("SELECT * FROM korisnici WHERE email = '" + user_mail.getText() + "'");

                    if (result.next())
                    {
                        alert.failMess("Korisnik sa tim mailom vec postoji");
                        return;
                    }
                    else if (!user_passwrod.getText().equals(user_repassword.getText())) {

                        alert.failMess("Lozinke se ne poklapaju");

                    }
                    else if (user_passwrod.getText().length() < 8) {
                        alert.failMess("Lozinka mora sadržati najmanje 8 karaktera");
                        return;
                    }
                    else
                    {
                        String insertdata = "INSERT INTO korisnici (ime,email, prezime, lozinka, status) VALUES (?, ?, ?, ?,?)";
                        prepare = connect.prepareStatement(insertdata);
                        prepare.setString(1,user_username.getText());
                        prepare.setString(2,user_mail.getText());
                        prepare.setString(3,"Prezimenovic");
                        prepare.setString(4,user_passwrod.getText());
                        prepare.setString(5,"Korisnik");



                        prepare.executeUpdate();
                        alert.successMes("Uspjesno ste kreirali nalog");


                    }
                }




            }
            catch (Exception e)
            {
                System.out.printf("Nece");
            }

        }






    }



    public void loginAcc() throws IOException {

        if(login_username.getText().isEmpty() || login_password.getText().isEmpty())
        {
            alert.failMess("Molimo vas unesite sve podatke");
        }
        else
        {


            try {
                connect = Database.connectDB();  /// Konektovanje na dvije baze!! *user*
                connect1 = DatabaseAdmin.connectionDBA();

                String upitA = "SELECT * FROM korisnici WHERE ime = ? AND lozinka = ?";
                String UpitU = "SELECT * FROM korisnici WHERE ime = ? AND lozinka = ?";


                prepare = connect.prepareStatement(UpitU);
                prepare1 = connect1.prepareStatement(upitA);


                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_password.getText());

                prepare1.setString(1, login_username.getText());
                prepare1.setString(2, login_password.getText());

                result = prepare.executeQuery();
                result1 = prepare1.executeQuery();


                if (result.next())
                {
                    alert.successMes("Lovoan si");
                    return;
                }
                else if  (result1.next())
                {
                    alert.successMes("Lovan si, cekajte povezivanje na server");


                    data.username = login_username.getText();




                    PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Pauza od 1 sekunde
                    pause.setOnFinished(event -> {
                        try {
                            adminDash();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    pause.play();







                    return;
                }
                else
                {
                    alert.failMess("Netacni podatci");
                }







            } catch (SQLException e) {
                throw new RuntimeException(e);

            }


        }





    }




    public void adminDash() throws IOException {   /// prikazivanje drugoge strane

        try {
            Thread.sleep(2000);
            Parent root = FXMLLoader.load(getClass().getResource("adminui.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


            login_button.getScene().getWindow().hide();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }





}








