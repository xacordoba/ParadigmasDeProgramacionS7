package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class LoginScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FlagsMaster - Iniciar sesión");

        // Crear la imagen de fondo
        Image backgroundImage = new Image("file:///C:/Users/braya/OneDrive/Imágenes/OIG (2).jpeg");
        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);

        // Crear el contenedor principal
        BorderPane layout = new BorderPane();
        layout.setBackground(new Background(background));

        // Crear el contenedor para el formulario
        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(50));
        formContainer.setAlignment(Pos.CENTER);

        // Crear el título
        Label titleLabel = new Label("FlagsMaster");
        titleLabel.setFont(Font.loadFont("file:///C:/Users/braya/Downloads/gamer2/Gamer.ttf", 100));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setEffect(new DropShadow(10, Color.BLACK));
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        // Crear los campos de texto
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nombre de usuario");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        // Crear el botón de inicio de sesión
        Button loginButton = new Button("Iniciar sesión");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setPrefWidth(150);
        loginButton.setOnAction(e -> {
            // Aquí puedes agregar la lógica para validar las credenciales del usuario
            boolean isAuthenticated = authenticate(usernameField.getText(), passwordField.getText());

            if (isAuthenticated) {
                startRoulette(primaryStage);
            } else {
                // Mostrar mensaje de error de inicio de sesión incorrecto
                showAlert(Alert.AlertType.ERROR, "Inicio de sesión fallido", "Credenciales inválidas");
            }
        });

        // Agregar los elementos al contenedor del formulario
        formContainer.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton);

        // Agregar el contenedor del formulario al contenedor principal
        layout.setCenter(formContainer);

        // Crear la escena principal
        Scene scene = new Scene(layout, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean authenticate(String username, String password) {
        // Aquí puedes implementar la lógica de autenticación
        // Comprueba si las credenciales son válidas y devuelve true si el inicio de sesión es exitoso, de lo contrario, devuelve false
        return username.equals("usuario") && password.equals("contraseña");
    }

    private void startRoulette(Stage primaryStage) {
        primaryStage.close();

        Stage rouletteStage = new Stage();
        Roulette roulette = new Roulette();
        roulette.start(rouletteStage);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
