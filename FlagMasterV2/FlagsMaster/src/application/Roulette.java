package application;

import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import java.util.Random;
import javafx.scene.layout.GridPane;

public class Roulette extends Application {

    private static final int NUM_DIVISIONS = 7;
    private static final double START_ANGLE = 90;
    private static final double ARC_LENGTH = 360.0 / NUM_DIVISIONS;

    private TextField resultTextField;
    private String[] continents = {
            "América",
            "Europa",
            "África",
            "Asia",
            "Oceanía",
            "Antártida",
            "América del Norte"
    };

    private Label continentTitleLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ruleta");

        BorderPane layout = new BorderPane();

        Image backgroundImage = new Image("file:///C:/Users/braya/OneDrive/Imágenes/OIG (2).jpeg");
        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
        layout.setBackground(new Background(background));

        Group divisionsGroup = createDivisions();

        Button spinButton = new Button("Girar");
        spinButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        spinButton.setPrefWidth(150);
        spinButton.setOnAction(e -> spinRoulette(divisionsGroup));

        resultTextField = new TextField();
        resultTextField.setEditable(false);
        resultTextField.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        resultTextField.setStyle("-fx-text-inner-color: white; -fx-opacity: 1;");
        resultTextField.setBackground(null);
        resultTextField.setAlignment(Pos.CENTER);

        GridPane table = createTable();

        Scene scene = new Scene(layout, 800, 600);

        Label titleLabel = new Label(primaryStage.getTitle());
        titleLabel.setFont(Font.loadFont("file:///C:/Users/braya/Downloads/gamer2/Gamer.ttf", 100));
        titleLabel.setTextFill(Color.WHITE);

        Arc arrow = createArrow();

        layout.setCenter(divisionsGroup);
        layout.setBottom(spinButton);
        layout.setTop(resultTextField);
        layout.setLeft(titleLabel);
        layout.setRight(table);
        BorderPane.setAlignment(divisionsGroup, Pos.CENTER);
        BorderPane.setAlignment(spinButton, Pos.CENTER);
        BorderPane.setAlignment(resultTextField, Pos.CENTER);
        BorderPane.setAlignment(titleLabel, Pos.CENTER_LEFT);

        arrow.centerXProperty().bind(divisionsGroup.layoutXProperty().add(divisionsGroup.getBoundsInParent().getWidth() / 2));
        arrow.centerYProperty().bind(divisionsGroup.layoutYProperty().add(divisionsGroup.getBoundsInParent().getHeight() / 2));

        layout.getChildren().add(arrow);

        continentTitleLabel = new Label();
        continentTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        continentTitleLabel.setTextFill(Color.WHITE);
        continentTitleLabel.setAlignment(Pos.CENTER);
        continentTitleLabel.setTranslateY(20);

        layout.getChildren().add(continentTitleLabel);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Group createDivisions() {
        Group group = new Group();

        Arc centerArc = new Arc();
        centerArc.setCenterX(0);
        centerArc.setCenterY(0);
        centerArc.setRadiusX(20);
        centerArc.setRadiusY(20);
        centerArc.setStartAngle(0);
        centerArc.setLength(360);
        centerArc.setType(ArcType.ROUND);
        centerArc.setFill(Color.BLACK);
        group.getChildren().add(centerArc);

        for (int i = 0; i < NUM_DIVISIONS; i++) {
            Arc arc = new Arc();
            arc.setCenterX(0);
            arc.setCenterY(0);
            arc.setRadiusX(200);
            arc.setRadiusY(200);
            arc.setStartAngle(START_ANGLE + i * ARC_LENGTH);
            arc.setLength(ARC_LENGTH);
            arc.setType(ArcType.ROUND);
            arc.setFill(getDivisionColor(i));
            arc.setStroke(Color.BLACK);
            arc.setStrokeWidth(2);

            group.getChildren().add(arc);
        }

        return group;
    }

    private Color getDivisionColor(int index) {
        Color[] colors = {
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE,
                Color.ORANGE,
                Color.PURPLE,
                Color.CYAN
        };

        return colors[(colors.length - 1 - index) % colors.length];
    }

    private void spinRoulette(Group divisionsGroup) {
        Random random = new Random();
        double rotateAngle = 360 * random.nextDouble() + 720;

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), divisionsGroup);
        rotateTransition.setByAngle(rotateAngle);
        rotateTransition.setOnFinished(e -> checkResult(divisionsGroup));
        rotateTransition.play();
    }

    private void checkResult(Group divisionsGroup) {
        Arc arrow = null;
        for (Node node : divisionsGroup.getChildren()) {
            if (node instanceof Arc) {
                Arc arc = (Arc) node;
                if (arc.getFill() != null && !arc.getFill().equals(Color.BLACK)) {
                    arrow = arc;
                    break;
                }
            }
        }

        if (arrow != null) {
            double currentAngle = divisionsGroup.getRotate() % 360;
            double normalizedAngle = (currentAngle - START_ANGLE + 360) % 360;
            int selectedIndex = (int) Math.floor(normalizedAngle / ARC_LENGTH);
            String selectedContinent = getContinentAtIndex(selectedIndex);
            resultTextField.setText(selectedContinent);
            continentTitleLabel.setText(selectedContinent);
            continentTitleLabel.setTextFill(Color.WHITE);
            continentTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 100));
            continentTitleLabel.setAlignment(Pos.CENTER);
            resultTextField.setStyle("-fx-text-fill: " + ((Color) arrow.getFill()).toString().replace("0x", "#") + ";");
        }
    }

    private String getContinentAtIndex(int index) {
        if (index >= 0 && index < continents.length) {
            return continents[index];
        } else {
            return "Desconocido";
        }
    }

    private Arc createArrow() {
        Arc arrow = new Arc();
        arrow.setRadiusX(220);
        arrow.setRadiusY(220);
        arrow.setStartAngle(START_ANGLE);
        arrow.setLength(1);
        arrow.setType(ArcType.OPEN);
        arrow.setStrokeWidth(4);
        arrow.setStroke(Color.WHITE);
        arrow.setFill(null);
        arrow.getStrokeDashArray().addAll(10.0, 10.0);

        return arrow;
    }

    private GridPane createTable() {
        GridPane table = new GridPane();
        table.setHgap(10);
        table.setVgap(10);
        table.setAlignment(Pos.CENTER);

        for (int i = 0; i < continents.length; i++) {
            Label colorLabel = new Label();
            colorLabel.setPrefWidth(30);
            colorLabel.setPrefHeight(30);
            colorLabel.setStyle("-fx-background-color: " + getDivisionColor(i).toString().replace("0x", "#") + ";");

            Label continentLabel = new Label(continents[i]);
            continentLabel.setStyle("-fx-text-fill: white;");

            table.add(colorLabel, 0, i);
            table.add(continentLabel, 1, i);
        }

        return table;
    }
}