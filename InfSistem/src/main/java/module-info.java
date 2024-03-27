module org.example.infsistem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires layout;
    requires kernel;
    requires java.desktop;


    opens org.example.infsistem to javafx.fxml;
    exports org.example.infsistem;

}