package com.poscinema.pos_cinema.controllers;

import com.poscinema.pos_cinema.models.Card;
import com.poscinema.pos_cinema.models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentCardController implements Initializable {
    int total = 0, change = 0, cash = 0, id , amount = 0;
    //usan logica para pago que diferencia el tipo de transaccion
    boolean createCard = false, rechargeCard = false ;

    @FXML
    private TextField totalTextField;

    public void setTotalAndCashOnHand(int total){
        this.total = total ;
        totalTextField.setText("$ " + total + " COP");
        changeTextField.setText("$ " + change + " COP");
        cashOnHand.setText("Cash on hand: $ " + User.balance + " COP");
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCreateCardBill(){
        this.createCard = true;
    }

    public void setRechargeCardBill(){
        this.rechargeCard = true;
    }

    // Métodos auxiliares para obtener inputs (debes implementarlos según tu UI)
    public void setCardNumberInput(int setCardNumber) {
        this.id = setCardNumber;
    }

    public void setRechargeAmountInput(int setAmount) {
        this.amount = setAmount;
    }
    @FXML
    private TextField changeTextField;

    @FXML
    private TextField cashTextField;

    @FXML
    private Label cashOnHand;


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

    @FXML
    private BorderPane totalBillMenu;

    public void OnButtonPay() {
        if (createCard) {
            if (cash >= total) {
                try {
                    // Crear una nueva tarjeta y guardarla en la base de datos
                    Card card = Card.createCard(String.valueOf(id), total);
                    card.saveCardToDatabase();


                    // Actualizar el balance del usuario y la interfaz gráfica
                    User.balance += total;
                    cashOnHand.setText("Cash on hand: $ " + User.balance + " COP");
                    total = 0;
                    showPaymentSuccessAlert();
                    totalBillMenu.setVisible(false);
                } catch (Exception e) {
                    showErrorDialog("Error", "Failed to create and charge the card: " + e.getMessage());
                }
            } else {
                showErrorDialog("Invalid payment", "Payment must be total, not partial.");
            }
        } else if (rechargeCard) {
            // Implementar lógica de recargar la tarjeta
            String cardNumber = String.valueOf(id); // Obtener el número de tarjeta de algún input de la UI
            int toCharge = amount; // Obtener el monto de recarga de algún input de la UI

            try {
                Card.reloadCard(cardNumber, toCharge);

                // Actualizar el balance del usuario y la interfaz gráfica si es necesario
                User.balance += amount;
                cashOnHand.setText("Cash on hand: $ " + User.balance + " COP");
            } catch (Exception e) {
                showErrorDialog("Error", "Failed to reload the card: " + e.getMessage());
            }
        } else {
            showErrorDialog("Error", "Error assigning transaction type");
        }
    }

    public void OnButtonCancel(ActionEvent actionEvent) {
        try {
            // Cargar el nuevo formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/create-card.fxml"));
            Parent centerContent = loader.load();

            // Obtener el BorderPane padre desde el botón que activó el evento
            Button button = (Button) actionEvent.getSource();
            BorderPane parentBorderPane = (BorderPane) button.getScene().getRoot();

            // Establecer el nuevo contenido en el centro del BorderPane padre
            parentBorderPane.setCenter(centerContent);
        }catch (IOException e){
            // Error al cargar la vista
            showErrorDialog("Error loading view", "The view could not be loaded");
        }
    }

    private void addToCashTextField (String number){
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
                cash = Integer.parseInt(newValue.replace("$ ", "").replace(" COP", ""));

                // Actualizar changeTextField con el nuevo cambio
                if(cash >= total){
                    // Calcular el cambio
                    change = cash - total;
                    changeTextField.setText("$ " + change + " COP");
                }else{
                    changeTextField.setText("$ " + "0" + " COP");
                }
            } catch (NumberFormatException e) {
                // Manejar excepción si el nuevo valor no es un número válido o esta vacio
               showErrorDialog("Invalid character", "Only numbers are allowed");
            }
        });
    }

    // Método para obtener el stage padre
    private Stage getParentStage() {
        return (Stage) totalTextField.getScene().getWindow().getScene().getWindow();
    }

    // Función para mostrar mensaje de error
    private  void showErrorDialog(String title, String message) {
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

    private   void showPaymentSuccessAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Successful");
            alert.setHeaderText(null);
            alert.setContentText("Payment has been successfully processed.");

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
