package com.poscinema.pos_cinema.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentCardControler implements Initializable {
    int total = 0, change = 0, cash = 0;

    @FXML
    private TextField totalTextField;

    public void setTotal(int total){
        this.total = total ;
        totalTextField.setText("$ " + String.valueOf(total) + " COP");
        changeTextField.setText("$ " + String.valueOf(change) + " COP");
    }

    @FXML
    private TextField changeTextField;


    @FXML
    private TextField cashTextField;

    public int getCash(){
        cash = Integer.parseInt(cashTextField.getText());
        return cash;
    }

    public void OnButton1(ActionEvent actionEvent) {
        String currentText = cashTextField.getText();
        currentText += "1";

        cashTextField.setText(currentText);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Agregar un ChangeListener al textProperty de cashTextField
        cashTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    // Verificar si el nuevo valor está vacío
                    if (newValue.isEmpty()) {
                        changeTextField.setText("$ 0 COP");
                        return;
                    }

                    // Obtener el nuevo valor de cash desde cashTextField
                    int newCash = Integer.parseInt(newValue.replace("$ ", "").replace(" COP", ""));

                    // Calcular el cambio
                    int newChange = newCash - total;

                    // Actualizar changeTextField con el nuevo cambio
                    if(newCash >= total){
                        changeTextField.setText("$ " + String.valueOf(newChange) + " COP");
                    }else{
                        changeTextField.setText("$ " + String.valueOf("0") + " COP");
                    }
                } catch (NumberFormatException e) {
                    // Manejar excepción si el nuevo valor no es un número válido
                    // Aquí puedes mostrar un mensaje de error o realizar otra acción según tus necesidades
                   showErrorDialog("Invalid character", "Solo se permiten numeros");
                }
            }
        });
    }

    // Método para obtener el stage padre
    private Stage getParentStage() {
        return (Stage) totalTextField.getScene().getWindow().getScene().getWindow();
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
