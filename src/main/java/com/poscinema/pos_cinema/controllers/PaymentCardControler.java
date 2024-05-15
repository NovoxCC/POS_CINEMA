package com.poscinema.pos_cinema.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
        totalTextField.setText("$ " + total + " COP");
        changeTextField.setText("$ " + change + " COP");
    }

    @FXML
    private TextField changeTextField;

    @FXML
    private TextField cashTextField;

    @FXML
    private Label cashOnHand;

    public int getCash(){
        cash = Integer.parseInt(cashTextField.getText());
        return cash;
    }

    public void OnButton0() {
        addToCashTextField("0");
    }

    public void OnButton1() {
        addToCashTextField("1");
    }

    public void OnButton2() {
        addToCashTextField("2");
    }

    public void OnButton3() {
        addToCashTextField("3");
    }

    public void OnButton4() {
        addToCashTextField("4");
    }

    public void OnButton5() {
        addToCashTextField("5");
    }

    public void OnButton6() {
        addToCashTextField("6");
    }

    public void OnButton7() {
        addToCashTextField("7");
    }

    public void OnButton8() {
        addToCashTextField("8");
    }

    public void OnButton9() {
        addToCashTextField("9");
    }

    public void OnButtonBackspace() {
        String currentText = cashTextField.getText();
        // Verificar si el texto actual tiene al menos un carácter
        if (!currentText.isEmpty()) {
            // Eliminar el último carácter del texto actual
            String newText = currentText.substring(0, currentText.length() - 1);
            // Establecer el nuevo texto en el campo de texto
            cashTextField.setText(newText);
        }
    }

    public void addToCashTextField (String number){
        String currentText = cashTextField.getText();
        currentText += number;

        cashTextField.setText(currentText);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Agregar un ChangeListener al textProperty de cashTextField
        cashTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
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
                    changeTextField.setText("$ " + newChange + " COP");
                }else{
                    changeTextField.setText("$ " + "0" + " COP");
                }
            } catch (NumberFormatException e) {
                // Manejar excepción si el nuevo valor no es un número válido o esta vacio
               showErrorDialog("Invalid character", "Solo se permiten numeros");
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
