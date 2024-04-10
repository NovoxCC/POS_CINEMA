package com.poscinema.pos_cinema.models;

import com.poscinema.pos_cinema.controllers.DatabaseConnection;
import com.poscinema.pos_cinema.controllers.Encryptor;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String username;
    private Integer roleId;

    public User() {
        this.username = null;
        this.roleId = null;
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

    public static boolean login(String username, String password) throws IOException {
        // Instanciar Encryptor para registrar y/o verificar usuario
        Encryptor encryptor = new Encryptor();
        // Verificar si el usuario y la contraseña coinciden\
        boolean isverified = encryptor.verifyPassword(username, password);
        if(isverified){
            System.out.println("verifico usuario true");
            return  true;
        }else{
            // Usuario no autenticado, mostrar un mensaje de error
            showErrorDialog("Error de autenticación", "Usuario y/o contraseña incorrectos.");
        }
        return false;
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
            e.printStackTrace();
        }
        return role;
    }

    // funcion para mostrar mensaje de error
    public static void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
