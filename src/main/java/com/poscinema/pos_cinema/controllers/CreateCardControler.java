package com.poscinema.pos_cinema.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateCardControler {
    @FXML
    private TextField idField;
    @FXML
    private TextField totalpayField;

    private BorderPane mainMenu; // Referencia al BorderPane principal

    // Método para establecer la referencia al BorderPane principal
    public void setMainMenu(BorderPane mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void OncreateButton(ActionEvent actionEvent) throws IOException{

        // Verificar si el TextField no está vacío
        if (!idField.getText().isEmpty()) {
            // El TextField no está vacío
            // Verificar si el TextField contiene solo números
            if (idField.getText().matches("\\d+")) {
                // El TextField contiene solo números
                try {
                    // Cargar el nuevo formulario
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/payment-card.fxml"));
                    Parent centerContent = loader.load();

                    // Obtener el BorderPane padre desde el botón que activó el evento
                    Button button = (Button) actionEvent.getSource();
                    BorderPane parentBorderPane = (BorderPane) button.getScene().getRoot();

                    // Establecer el nuevo contenido en el centro del BorderPane padre
                    parentBorderPane.setCenter(centerContent);
                } catch (IOException e) {
                    showErrorDialog("Error loading view", "The view could not be loaded");
                    // Manejo de errores
                }
            } else {
                // El TextField contiene caracteres que no son números
                showErrorDialog("Invalid characters","Owner id can only contain numbers ");
            }
        } else {
            // El TextField está vacío
            showErrorDialog("Empty field", "owner id is empty");
        }
    }

    // Función para mostrar mensaje de error
    public static void showErrorDialog(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            // Configurar la ventana de alerta para que siempre esté encima
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.setAlwaysOnTop(true);

            alert.showAndWait();
        });
    }

}
