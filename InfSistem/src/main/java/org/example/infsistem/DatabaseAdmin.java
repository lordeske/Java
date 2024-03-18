package org.example.infsistem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAdmin {

    //// BAZA ZA ADMINA

    public static Connection connectionDBA() throws SQLException {

        Connection connection = null;



        connection = DriverManager.getConnection("jdbc:mysql://localhost/pva_vezba10", "root", "");


        return  connection;

    }











}
