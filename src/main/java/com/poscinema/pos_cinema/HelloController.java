package com.poscinema.pos_cinema;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController{
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;

    // funcion para mostrar mensaje de error
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void OnsignInButton(ActionEvent actionEvent) throws SQLException {
        String user = usernameField.getText();
        String password = passwordField.getText();

        // Instanciar Encryptor para registrar y verificar usuarios
        Encryptor encryptor = new Encryptor();
        //encryptor.registrarusuario(user, password, 2);
        // Verificar si el usuario y la contraseña coinciden
        boolean isAuthenticated = encryptor.verificarPassword(user, password);

        if (isAuthenticated) {
            // Usuario autenticado, navegar a otra escena
            System.out.println("Usuario autenticado. Navegar a otra escena...");
        } else {
            // Usuario no autenticado, mostrar un mensaje de error
            showErrorDialog("Error de autenticación", "Usuario y/o contraseña incorrectos.");
        }
    }


}