package com.poscinema.pos_cinema.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class CreateCardControler {

    private BorderPane mainMenu; // Referencia al BorderPane principal

    // Método para establecer la referencia al BorderPane principal
    public void setMainMenu(BorderPane mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void OncreateButton(ActionEvent actionEvent) throws IOException{
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
            e.printStackTrace();
            // Manejo de errores
        }
    }

}
