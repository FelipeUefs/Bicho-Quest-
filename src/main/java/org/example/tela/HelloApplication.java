package org.example.tela;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Launcher launcher = new Launcher();
        launcher.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}

