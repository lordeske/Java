package org.example.infsistem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseProizvodi {

    //// BAZA ZA ADMINA

    public static Connection connectionP() throws SQLException {

        Connection connection = null;



        connection = DriverManager.getConnection("jdbc:mysql://localhost/prozivodi", "root", "");


        return  connection;

    }











}
