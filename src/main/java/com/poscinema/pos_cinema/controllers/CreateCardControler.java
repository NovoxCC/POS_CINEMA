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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateCardControler {
    @FXML
    private TextField idField;
    @FXML
    private TextField totalpayField;


    // Método para obtener el stage padre
    private Stage getParentStage() {
        return (Stage) idField.getScene().getWindow().getScene().getWindow();
    }

    public void OncreateButton(ActionEvent actionEvent) {
        // Verificar si el TextField no está vacío
        if (!idField.getText().isEmpty()) {
            // Verificar si el TextField contiene solo números
            if (idField.getText().matches("\\d+")) {
                //verificar si el TextField contiene un id con la longitud valida
                if (idField.getText().length() > 8 && idField.getText().length() <= 10){
                        // El TextField contiene solo números
                        try {
                            if(verifyid(Long.parseLong(idField.getText()))){
                                // Cargar el nuevo formulario
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/payment-card.fxml"));
                                Parent centerContent = loader.load();

                                // Obtener una referencia al controlador del formulario cargado
                                PaymentCardController controler = loader.getController();
                                //pasar el valor del total a pagar al controlador
                                controler.setTotalAndCashOnHand(cleanAndParseInt(totalpayField));
                                controler.setId(Integer.parseInt(idField.getText()));
                                controler.setCreateCardBill();

                                // Obtener el BorderPane padre desde el botón que activó el evento
                                Button button = (Button) actionEvent.getSource();
                                BorderPane parentBorderPane = (BorderPane) button.getScene().getRoot();

                                // Establecer el nuevo contenido en el centro del BorderPane padre
                                parentBorderPane.setCenter(centerContent);
                            }

                        } catch (IOException e) {
                            // Error al cargar la vista
                            showErrorDialog("Error loading view", "The view could not be loaded");
                        }
                }else{
                    // El TextField no contiene la catidad minima de numeros (6)
                    showErrorDialog("Invalid id","The id isn't valid \n  It must be between 8 to 10 characters");
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
    private int cleanAndParseInt(TextField textField ){
        String text = textField.getText();
        text = text.replaceAll("\\D", "");
        return Integer.parseInt(text);
    }

    private boolean verifyid(long id) {
        try {
            // Obtener conexión a la base de datos
            ResultSet resultSet = getResultSet(id);

            // Verificar si se encontró algún resultado
            if (resultSet.next()) {
                // El id ya existe en la base de datos
                showErrorDialog("Error", "Owner id already have a card.");
                return false;
            } else {
                // El id no existe en la base de datos, se puede proceder con la inserción
                return true;
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL que pueda ocurrir
            showErrorDialog("Error", "Failed to verify id");
            return false;
        }
    }

    private static ResultSet getResultSet(long id) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        // Preparar la consulta SQL para buscar el id en la base de datos
        String query = "SELECT owner_id FROM Cards WHERE owner_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);

        // Ejecutar la consulta de selección
        return statement.executeQuery();
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
