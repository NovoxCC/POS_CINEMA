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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateCardControler {
    @FXML
    private TextField idField;
    @FXML
    private TextField totalpayField;


    // Método para obtener el stage padre
    private Stage getParentStage() {
        return (Stage) idField.getScene().getWindow().getScene().getWindow();
    }


    public void OncreateButton(ActionEvent actionEvent) throws IOException{
        // Verificar si el TextField no está vacío
        if (!idField.getText().isEmpty()) {
            // Verificar si el TextField contiene solo números
            if (idField.getText().matches("\\d+")) {
                //verificar si el TextField contiene un id con la longitud valida
                if (idField.getText().length() > 6 ){
                    // El TextField contiene solo números
                    try {
                        // Cargar el nuevo formulario
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/payment-card.fxml"));
                        Parent centerContent = loader.load();

                        // Obtener una referencia al controlador del formulario cargado
                        PaymentCardControler controler = loader.getController();
                        //pasar el valor del total a pagar al controlador
                        controler.setTotal(cleanAndParseInt(totalpayField));

                        // Obtener el BorderPane padre desde el botón que activó el evento
                        Button button = (Button) actionEvent.getSource();
                        BorderPane parentBorderPane = (BorderPane) button.getScene().getRoot();

                        // Establecer el nuevo contenido en el centro del BorderPane padre
                        parentBorderPane.setCenter(centerContent);
                    } catch (IOException e) {
                        // Error al cargar la vista
                        showErrorDialog("Error loading view", "The view could not be loaded");
                    }
                }else{
                    // El TextField no contiene la catidad minima de numeros (6)
                    showErrorDialog("Invalid id","The id isn't valid \n Is too short");
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
    //funcion para limpiar el numero
    public int cleanAndParseInt(TextField textField ){
        String text = textField.getText();
        text = text.replaceAll("\\D", "");
        int number = Integer.parseInt(text);
        return number;
    }

    // Función para mostrar mensaje de error
    public  void showErrorDialog(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            // Obtener el stage padre y establecerlo como propietario de la alerta esto permite
            // dar el mensaje sin que se pierda el foco y cambie de ventana
            Stage parentStage = getParentStage();
            alert.initOwner(parentStage);
            alert.initModality(Modality.WINDOW_MODAL);


            // Mostrar la alerta
            alert.showAndWait();
        });
    }

}
