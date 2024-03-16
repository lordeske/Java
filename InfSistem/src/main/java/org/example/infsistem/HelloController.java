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
                    
                }




            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }


    }







}