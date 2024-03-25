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
    void OnsignInButton(ActionEvent actionEvent) {
        String user = usernameField.getText();
        PythonEncryptor encryptor = new PythonEncryptor();
        String p =  passwordField.getText();
        String hp = encryptor.encryptPassword(user, p);
        encryptor.close();
        DatabaseConnection connectionNow = new DatabaseConnection();
        try {
            Connection connectDB = connectionNow.getConnection();
            if (connectDB != null) {
                System.out.println("Conexión establecida.");// para saber si inicia el boton
                // Aquí  lógica de autenticación y navegación a otra escena
                String query = "SELECT COUNT(*) FROM Users WHERE username = ? AND password_hash = ?";
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, user);
                statement.setString(2, hp);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count == 1) {
                        // Usuario autenticado,  navegar a otra escena aquí
                        System.out.println("Usuario autenticado. Navegar a otra escena...");
                    } else {
                        // Usuario no autenticado, muestra un mensaje de error
                        showErrorDialog("Error de autenticación", "Usuario y/o contraseña incorrectos.\n");
                    }
                }
                // Cerrar la conexión
                resultSet.close();
                statement.close();
                connectionNow.closeConnection();
            } else {
                // Mostrar ventana de error si no se pudo establecer la conexión
                showErrorDialog("Error de conexión", "No se pudo establecer la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            // Mostrar ventana de error si ocurre una excepción SQL
            showErrorDialog("Error de SQL", "Ocurrió un error al intentar conectarse a la base de datos:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}