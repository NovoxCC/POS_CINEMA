package com.poscinema.pos_cinema.controllers;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.security.SecureRandom;
import java.util.Base64;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Encryptor {
    //falta tener en cuenta a
    public void registerUser(String user, String password, int roleid) {
        try {
            // Generar una sal aleatoria
            String salt = generateSalt(28);

            // Encriptar la contraseña con la sal
            String hash = encryptPassword(password, salt);

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
            // Manejar cualquier excepción SQL que pueda ocurrir
            e.printStackTrace();
        }
    }

    public boolean verifyPassword(String user, String password) {
        String storedHash = null;
        String storedSalt = null;
        try {
            // Establecer la conexión a la base de datos
            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectDB = connectionNow.getConnection();

            // Preparar la consulta SQL para obtener el hash y la sal asociados al usuario
            String query = "SELECT password_hash, salt FROM Users WHERE username = ?";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, user);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Verificar si se encontró un resultado
            if (resultSet.next()) {
                // Obtener el hash y la sal del resultado
                storedHash = resultSet.getString("password_hash");
                storedSalt = resultSet.getString("salt");
            }

            // Cerrar la conexión y liberar los recursos
            resultSet.close();
            statement.close();
            connectDB.close();

            // Verificar si se encontró el hash y la sal
            if (storedHash != null && storedSalt != null) {
                // Verificar si la contraseña ingresada coincide con el hash almacenado
                Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                return argon2.verify(storedHash, password + storedSalt);
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que pueda ocurrir
            e.printStackTrace();
        }

        // Si no se encontró el hash y la sal, o si ocurrió un error, retornar falso
        return false;
    }

    private String encryptPassword(String password, String salt) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(10, 65536, 4, password + salt);
        System.out.println("encryptPassword hash " + hash);
        return hash;
    }

    private static String generateSalt(int length) {
        // Generar una sal aleatoria utilizando SecureRandom
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[length];
        random.nextBytes(saltBytes);

        // Codificar la sal en Base64
        String base64String = Base64.getUrlEncoder().withoutPadding().encodeToString(saltBytes);

        // Truncar la cadena si es más larga que la longitud deseada
        if (base64String.length() > length) {
            base64String = base64String.substring(0, length);
        }

        return base64String;
    }
}
