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

import static com.poscinema.pos_cinema.models.Card.*;

public class RechargeCardController {

    @FXML
    private TextField cardNumberTextField;
    @FXML
    private TextField  toRechargeTextField;
    @FXML
    private TextField cvvTextField;

    public void OnPayButton(ActionEvent actionEvent) {
        // Verificar si el TextField no está vacío
        if (!cardNumberTextField.getText().isEmpty() && !toRechargeTextField.getText().isEmpty() && !cvvTextField.getText().isEmpty()) {
            // Verificar si el TextField contiene solo números
            if (cardNumberTextField.getText().matches("\\d+") && toRechargeTextField.getText().matches("\\d+") && cvvTextField.getText().matches("\\d+")) {
                //verificar si el TextField contiene un id con la longitud valida
                int rechargeAmount = Math.toIntExact(Long.parseLong(toRechargeTextField.getText()));
                if(rechargeAmount >= 50000){
                    if (cardNumberTextField.getText().length() == 16 && cvvTextField.getText().length() == 3){
                        // El TextField contiene solo números)
                        if (isCardNumberInDatabase(cardNumberTextField.getText())){
                            //se encontro la tarjeta en al base de datos
                            if(isCvvValidForCard(cardNumberTextField.getText(), cvvTextField.getText())){
                                try {
                                    //se verifico que el cvv es corecto para la tarjeta
                                    // Cargar el nuevo formulario
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/payment-card.fxml"));
                                    Parent centerContent = loader.load();

                                    // Obtener una referencia al controlador del formulario cargado
                                    PaymentCardController controler = loader.getController();
                                    //pasar el valor del total a pagar al controlador
                                    controler.setTotalAndCashOnHand((Integer.parseInt(toRechargeTextField.getText())));
                                    controler.setCardNumberInput(cardNumberTextField.getText());
                                    controler.setRechargeCardBill();

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
                        }else{
                            // El TextField no contiene la catidad minima de numeros (6)
                            showErrorDialog("Invalid card","the card does not exist");
                        }
                    }else{
                        // El TextField no contiene la catidad minima de numeros (6)
                        showErrorDialog("Invalid card or CVV","The card must have 16 numbers and CVV 3 ");
                    }
                }else{
                    // monto de recarga menor a 50000
                    showErrorDialog("Error", "The minimum recharge amount is 50000");
                }
            } else {
                // El TextField contiene caracteres que no son números
                showErrorDialog("Invalid characters","The fields can only contain numbers ");
            }
        } else {
            // El TextField está vacío
            showErrorDialog("Empty field", "The fields cannot be empty");
        }


    }
    // Método para obtener el stage padre
    private Stage getParentStage() {
        return (Stage) cardNumberTextField.getScene().getWindow().getScene().getWindow();
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
