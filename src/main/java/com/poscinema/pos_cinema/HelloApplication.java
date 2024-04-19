package com.poscinema.pos_cinema;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.application.Application.launch;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            if (fxmlLoader.getLocation() == null) {
                throw new IOException("No se pudo encontrar el archivo FXML.");
            }
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("POS CINEMA");
            stage.setScene(scene);
            stage.setMinWidth(459); // Establecer el ancho mínimo
            stage.setMinHeight(620); // Establecer la altura mínima

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Error de carga", "No se pudo cargar la interfaz de usuario.");
        }
    }

    // Método para mostrar un cuadro de diálogo de error
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
