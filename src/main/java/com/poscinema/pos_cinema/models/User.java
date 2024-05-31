package com.poscinema.pos_cinema.models;

import com.poscinema.pos_cinema.HelloApplication;
import com.poscinema.pos_cinema.controllers.DatabaseConnection;
import com.poscinema.pos_cinema.controllers.Encryptor;
import com.poscinema.pos_cinema.controllers.loginController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class User {
    private String username;
    private Integer roleId;
    public static int balance;

    public User() {
        this.username = null;
        this.roleId = null;
        balance = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public  int getBalance(){
        return balance;
    }

    public void setBalance(int balance) {
        User.balance = balance;
    }

    public static boolean login(String username, String password) {
        // Instanciar Encryptor para registrar y/o verificar usuario
        Encryptor encryptor = new Encryptor();
        // Verificar si el usuario y la contraseña coinciden\
        boolean isverified = encryptor.verifyPassword(username, password);
        if(isverified){
            return  true;
        }else{
            // Usuario no autenticado, mostrar un mensaje de error
            Platform.runLater(() -> {
                showErrorDialog("Error de autenticación", "Usuario y/o contraseña incorrectos.");
            });

        }
        return false;
    }

    public void singOff(Stage currentStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/poscinema/pos_cinema/login-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Establecer la nueva escena en la ventana actual y mostrarla
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    public void registerUser(String user, String password, int roleid) {
        try {
            // Generar una sal aleatoria
            String salt = Encryptor.generateSalt(28);

            // Encriptar la contraseña con la sal
            String hash = Encryptor.encryptPassword(password, salt);

            // Establecer la conexión a la base de datos
            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectDB = connectionNow.getConnection();

            // Preparar la consulta SQL para insertar el usuario en la base de datos
            String query = "INSERT INTO Users (username, password_hash, salt, role_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, user);
            statement.setString(2, hash);
            statement.setString(3, salt);
            statement.setString(4, String.valueOf(roleid));

            // Ejecutar la consulta de inserción
            statement.executeUpdate();

            // Cerrar la conexión y liberar los recursos
            statement.close();
            connectDB.close();
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que pueda ocurrir mejorar cuando se implemente
            e.printStackTrace();
        }
    }
    public  static  Integer getRoleId(String username) {
        Integer role = null;
        try {
            // Establecer la conexión a la base de datos
            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectDB = connectionNow.getConnection();

            // Preparar la consulta SQL para obtener el roleid
            String query = "SELECT role_id, salt FROM Users WHERE username = ?";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, username);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Verificar si se encontró un resultado
            if (resultSet.next()) {
                // Obtener el roleid
                role = Integer.valueOf(resultSet.getString("role_id"));
            }

            // Cerrar la conexión y liberar los recursos
            resultSet.close();
            statement.close();
            connectDB.close();

            // Verificar si se encontró el roleid
            if (role != null) {
                return role;
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que pueda ocurrir
            Logger logger = Logger.getLogger(loginController.class.getName());
            logger.log(Level.SEVERE, "Error al obtener el rol del usuario", e);
        }
        return role;
    }

    // funcion para mostrar mensaje de error
    public static void showErrorDialog(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
