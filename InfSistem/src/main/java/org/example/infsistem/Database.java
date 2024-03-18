package org.example.infsistem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//// BAZA ZA USERA

public class Database {

    public static Connection connectDB() {
        Connection connect = null;

        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost/pva_vezba7", "root", "");
        } catch (SQLException e) {
            System.out.println("Greška prilikom uspostavljanja veze sa bazom podataka: " + e.getMessage());
        }

        return connect;
    }
}
