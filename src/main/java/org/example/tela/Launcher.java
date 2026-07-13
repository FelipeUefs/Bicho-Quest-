package org.example.tela;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher {
    private Stage primeiraTela;

    public void start(Stage tela) {
        this.primeiraTela = tela;
        exibirmenu();
    }

    private void exibirmenu() {
            StackPane menu = new StackPane();
            Image imagem = new Image("Caminho da Imagem");
            ImageView imageView = new ImageView(imagem);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);
            Button play = new Button("Jogar");
            Button options = new Button("Opções");
            Button exit = new Button("Sair");
            ScaleTransition aumenta = new ScaleTransition(javafx.util.Duration.millis(150), play);
            aumenta.setToX(1.1);
            aumenta.setToY(1.1);
            ScaleTransition diminui = new ScaleTransition(javafx.util.Duration.millis(150), play);
            diminui.setToX(1.0);
            diminui.setToY(1.0);
            play.setOnMouseEntered(e -> {
                aumenta.stop();
                diminui.play();
            });
            play.setOnMouseExited(e -> {
                diminui.stop();
                aumenta.play();
            });
            play.setOnAction(e -> {
                jogo();
            });
            menu.getChildren().addAll(imageView, play, options, exit);
            Scene Cenamenu = new Scene(menu, 800, 600);
            primeiraTela.setTitle("Bicho Quest - Menu");
            primeiraTela.setScene(Cenamenu);
            primeiraTela.show();
    }

    public void jogo() {
            StackPane game = new StackPane();
            Image gameimagem = new Image("Caminho da Imagem");
            ImageView imageView = new ImageView(gameimagem);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);
            Button play = new Button("Voltar ao Menu");
            play.setOnAction(e -> {
                exibirmenu();
            });
            game.getChildren().addAll(imageView, play);
            Scene Cenagame = new Scene(game, 800, 600);
            primeiraTela.setTitle("Bicho Quest - Jogo");
            primeiraTela.setScene(Cenagame);
            primeiraTela.show();
    }

}
