module org.example.infsistem {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.infsistem to javafx.fxml;
    exports org.example.infsistem;
}