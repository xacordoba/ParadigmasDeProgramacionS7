package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FlagsMaster"); // Título de la ventana

        // Crear un botón para jugar con estilo de juego
        Button playButton = new Button("JUGAR");
        
        playButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.start(primaryStage);
        });

        // Aplicar estilos y efectos visuales al botón
        playButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 20px; -fx-background-radius: 20; -fx-padding: 0 40 0 10;"); // Agregar padding para mover el texto hacia la izquierda
        playButton.setPrefWidth(150);
        playButton.setPrefHeight(60);
        playButton.setEffect(new DropShadow());

        // Asignar una imagen de ícono al botón (reemplaza la ruta con la ubicación de tu imagen)
        Image iconImage = new Image("file:///C:/Users/braya/OneDrive/Imágenes/game-icon.png");
        ImageView iconImageView = new ImageView(iconImage);
        iconImageView.setFitWidth(30);
        iconImageView.setFitHeight(30);
        playButton.setGraphic(iconImageView);

        // Crear una etiqueta para el título con la fuente "Gamer"
        Label titleLabel = new Label(primaryStage.getTitle());
        titleLabel.setFont(Font.loadFont("file:///C:/Users/braya/Downloads/gamer2/Gamer.ttf", 100));
        titleLabel.setTextFill(Color.WHITE); // Establecer el color del texto en blanco

        // Agregar efecto de sombra con contorno negro al texto
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        titleLabel.setEffect(dropShadow);

        // Configurar el diseño personalizado del BorderPane para centrar el título y el botón
        BorderPane layout = new BorderPane();
        layout.setCenter(playButton);
        layout.setTop(titleLabel);
        BorderPane.setAlignment(playButton, Pos.CENTER);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new javafx.geometry.Insets(30, 0, 0, 0)); // Margen superior de 30 píxeles

        // Agregar imagen de fondo al BorderPane
        Image backgroundImage = new Image("file:///C:/Users/braya/OneDrive/Imágenes/OIG (2).jpeg"); // Cambia la ruta a la ubicación de la imagen de fondo
        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
        layout.setBackground(new Background(background));

        // Crear la escena principal
        Scene scene = new Scene(layout, 800, 600); // Tamaño de la ventana

        // Ajustar el tamaño del título al tamaño de la ventana
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> titleLabel.setPrefWidth((double) newWidth));
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> titleLabel.setPrefHeight((double) newHeight));

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
