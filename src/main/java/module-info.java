module org.example.tela {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens org.example.tela to javafx.fxml;
    exports org.example.tela;
}