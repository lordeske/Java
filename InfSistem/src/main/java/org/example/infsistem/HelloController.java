package org.example.infsistem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

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



    public void displayLogin(ActionEvent event)
    {

            login_form.setVisible(true);
            admin_form.setVisible(false);
            form_user.setVisible(false);
            login_role.getSelectionModel().clearSelection();

    }




    public void registerAdmin()
    {
        if (admin_username.getText().isEmpty() || admin_password.getText().isEmpty() ||
        admin_repassword.getText().isEmpty())
        {
            alert.failMess("Molimo vas unesite sva polja");
        }
        else
        {
            connect = Database.connectDB();
            String selectedData = "SELECT * FROM korisnici WHERE username = " + admin_username.getText();


            try {

            }
            catch (Exception e)
            {
                try {
                    statement  = connect.createStatement();
                    result = statement.executeQuery(selectedData);

                    if(result.next())
                    {
                        alert.failMess(admin_username.getText() + "vec postoji");
                    }
                    else if (!admin_repassword.getText().equals(admin_repassword.getText())) {
                        alert.failMess("Sifre se ne poklapaju");
                    }
                    else if (admin_password.getText().length() < 8) {
                        alert.failMess("Morate unjeti bar 8 slova");
                    } else
                    {
                        String insertdata = "INESRT INTO korisnici (ime, prezime, lozinka, status) "
                                + "VALUES (?,?,?,?)";


                        prepare = connect.prepareCall(insertdata);
                        prepare.setString(1,admin_username.getText());
                        prepare.setString(2,admin_password.getText());
                        prepare.setString(3,admin_repassword.getText());
                        prepare.setString(4,"Admin");

                        prepare.executeUpdate();

                        alert.successMes("Uspjestno ste kreirali nalog");
                    }




                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        }




    }







}