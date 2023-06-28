package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends Application {
    private static final int GRID_SIZE = 4;
    private static final int NUM_PAIRS = 8;
    private static final int TIME_LIMIT = 60; // Tiempo límite en segundos

    private List<Button> buttons;
    private Button selectedButton;
    private int pairsFound;
    private int cardsVisible;
    private int timeRemaining;
    private Timeline timeline;
    private Label timerLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Memory Game");

        // Crear las imágenes de las banderas
        List<Image> flagImages = createFlagImages();

        // Crear los botones con las imágenes
        buttons = createButtons(flagImages);

        // Barajar los botones
        Collections.shuffle(buttons);

        // Crear el panel de cuadrícula
        GridPane gridPane = createGridPane();

        // Configurar el evento de clic para los botones
        setupButtonClickEvent();

        // Crear el temporizador
        timerLabel = new Label();
        timerLabel.setStyle("-fx-font-size: 18px;");
        timerLabel.setText("Tiempo restante: " + TIME_LIMIT);

        // Crear la caja horizontal para el temporizador
        HBox timerBox = new HBox(timerLabel);
        timerBox.setAlignment(Pos.CENTER);

        // Crear la caja vertical para el temporizador y la cuadrícula
        VBox mainBox = new VBox(timerBox, gridPane);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(10);

        // Crear la escena principal
        Scene scene = new Scene(mainBox, 400, 450);

        primaryStage.setScene(scene);
        primaryStage.show();

        startGame();
    }

    private List<Image> createFlagImages() {
        List<Image> flagImages = new ArrayList<>();

        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/in.png.png"));
        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/io.png.png"));
        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/iq.png.png"));
        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/ir.png.png"));
        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/is.png.png"));
        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/it.png.png"));
        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/je.png.png"));
        flagImages.add(new Image("file:///C:/Users/braya/OneDrive/Imágenes/banderas/jm.png.png"));

        return flagImages;
    }

    private List<Button> createButtons(List<Image> flagImages) {
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < NUM_PAIRS; i++) {
            Image flagImage = flagImages.get(i);

            Button button1 = createButtonWithImage(flagImage);
            Button button2 = createButtonWithImage(flagImage);

            buttons.add(button1);
            buttons.add(button2);
        }

        return buttons;
    }

    private Button createButtonWithImage(Image image) {
        Button button = new Button();
        button.setPrefSize(80, 80);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        imageView.setVisible(false); // Ocultar la imagen inicialmente

        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2px;");

        return button;
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Button button = buttons.get(index);
                GridPane.setRowIndex(button, row);
                GridPane.setColumnIndex(button, col);
                gridPane.getChildren().add(button);
                index++;
            }
        }

        return gridPane;
    }

    private void setupButtonClickEvent() {
        for (Button button : buttons) {
            button.setOnAction(e -> handleButtonClick(button));
        }
    }

    private void handleButtonClick(Button button) {
        if (selectedButton == null) {
            selectedButton = button;
            showCard(button);
        } else {
            if (button != selectedButton) {
                showCard(button);
                if (isPairMatch(selectedButton, button)) {
                    disableButtons(selectedButton, button);
                    pairsFound++;
                    if (pairsFound == NUM_PAIRS || cardsVisible == NUM_PAIRS * 2) {
                        showVictoryMessage();
                        stopGame();
                    }
                } else {
                    hideCards(selectedButton, button);
                }
                selectedButton = null;
            }
        }

        if (timeRemaining == 0) {
            stopGame();
            Platform.runLater(() -> {
                showGameOverMessage();
            });
        }
    }



    private void showCard(Button button) {
        button.setDisable(true);
        ImageView imageView = (ImageView) button.getGraphic();
        imageView.setVisible(true);
        cardsVisible++;
    }

    private void hideCards(Button button1, Button button2) {
        button1.setDisable(false);
        button2.setDisable(false);
        ImageView imageView1 = (ImageView) button1.getGraphic();
        ImageView imageView2 = (ImageView) button2.getGraphic();
        if (imageView1 != null) {
            imageView1.setVisible(false);
        }
        if (imageView2 != null) {
            imageView2.setVisible(false);
        }
    }

    private void disableButtons(Button button1, Button button2) {
        button1.setDisable(true);
        button2.setDisable(true);
    }

    private boolean isPairMatch(Button button1, Button button2) {
        ImageView imageView1 = (ImageView) button1.getGraphic();
        ImageView imageView2 = (ImageView) button2.getGraphic();
        return imageView1.getImage().equals(imageView2.getImage());
    }

    private void showVictoryMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText(null);
        alert.setContentText("¡Has ganado el juego!");
        alert.showAndWait();
    }

    private void startGame() {
        pairsFound = 0;
        cardsVisible = 0;
        selectedButton = null;
        startTimer();
    }

    private void startTimer() {
        timeRemaining = TIME_LIMIT;
        timerLabel.setText("Tiempo restante: " + timeRemaining);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            timerLabel.setText("Tiempo restante: " + timeRemaining);
            if (timeRemaining == 0) {
                showGameOverMessage();
                stopGame();
            }
        }));
        timeline.setCycleCount(TIME_LIMIT + 1); // Modificado para que el timeline se detenga al llegar a 0
        timeline.play();
    }

    private void showGameOverMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Fin del juego!");
        alert.setHeaderText(null);
        alert.setContentText("Se ha agotado el tiempo. ¡Inténtalo de nuevo!");
        alert.showAndWait();
    }

    private void stopGame() {
        timeline.stop();
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }
}