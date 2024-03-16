package org.example.infsistem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Database {


    public static Connection connectDB(){

        try{

            Connection connect = DriverManager.getConnection("jdbc:mysql:localhost/pva_k2","root","");

            return connect;
        }
        catch (Exception e)
        {



        }


        return null;
    }





}
