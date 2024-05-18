package com.poscinema.pos_cinema.models;

import com.poscinema.pos_cinema.controllers.DatabaseConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Card {
    private final String cardNumber;
    private String cvv;
    private String ownerId;
    private int balance;

    // Constructor
    public Card(String ownerId, int balance) {
        this.cardNumber = generateUniqueCardNumber("1234"); // Cambia el prefijo personalizado si es necesario
        this.setCvv(generateRandomCVV());
        this.setOwnerId(ownerId);
        this.setBalance(balance);
    }

    // Getters y Setters
    public String getCardNumber() {
        return cardNumber;
    }

    // No se proporciona un setter para el número de tarjeta, ya que se genera automáticamente en el constructor

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    // Método para crear una tarjeta con generación aleatoria de CVV
    public static Card createCard(String ownerId, int balance) {
        return new Card(ownerId, balance);
    }

    // Método para generar un número de tarjeta único
    private static String generateUniqueCardNumber(String prefix) {
        String cardNumber;
        do {
            cardNumber = generateCardNumber(prefix);
        } while (isCardNumberInDatabase(cardNumber));
        return cardNumber;
    }

    // Método para generar un número de tarjeta aleatorio con un prefijo personalizado
    private static String generateCardNumber(String prefix) {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder(prefix);
        for (int i = 0; i < 11; i++) {
            cardNumber.append(random.nextInt(10)); // Agregar un dígito aleatorio del 0 al 9
        }
        String controlDigit = calculateControlDigit(cardNumber.toString());
        cardNumber.append(controlDigit);
        return cardNumber.toString();
    }

    // Método para verificar si un CVV ya existe en la base de datos para un número de tarjeta específico
    public static boolean isCvvValidForCard(String cardNumber, String cvv) {
        boolean exists = false;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String query = "SELECT COUNT(*) FROM Cards WHERE card_number = ? AND cvv = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cardNumber);
            statement.setString(2, cvv);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                exists = true;
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            showErrorDialog("Error", "Failed to check CVV in database");
        }
        return exists;
    }
    // Método para verificar si un número de tarjeta ya existe en la base de datos
    public static boolean isCardNumberInDatabase(String cardNumber) {
        boolean exists = false;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String query = "SELECT COUNT(*) FROM Cards WHERE card_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cardNumber);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                exists = true;
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            showErrorDialog("Error", "Failed to check card number in database");
        }
        return exists;
    }

    // Método para generar un CVV aleatorio de 3 dígitos
    private static String generateRandomCVV() {
        Random random = new Random();
        int cvv = random.nextInt(1000); // Genera un número aleatorio entre 0 y 999
        return String.format("%03d", cvv); // Formatea el número para que tenga 3 dígitos (rellena con ceros a la izquierda si es necesario)
    }

    // Método para calcular la cifra de control utilizando el algoritmo de Luhn
    private static String calculateControlDigit(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        int controlDigit = (10 - (sum % 10)) % 10;
        return String.valueOf(controlDigit);
    }

    // Método para guardar una tarjeta en la base de datos
    public void saveCardToDatabase() {
        try {
            // Obtener conexión a la base de datos
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            // Preparar la consulta SQL para insertar la tarjeta en la base de datos
            String query = "INSERT INTO Cards (card_number, cvv, owner_id, balance) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.getCardNumber());
            statement.setString(2, this.getCvv());
            statement.setString(3, this.getOwnerId());
            statement.setInt(4, this.getBalance());

            // Ejecutar la consulta de inserción
            statement.executeUpdate();

            // Cerrar la conexión y liberar los recursos
            statement.close();
            connection.close();
        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL que pueda ocurrir
            showErrorDialog("Error", "Failed to save card to database");
        }
    }

    // Método para recargar una tarjeta existente
    public static boolean reloadCard(String cardNumber, int amount) {
        boolean success = false;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            // Preparar la consulta SQL para actualizar el saldo de la tarjeta
            String query = "UPDATE Cards SET balance = balance + ? WHERE card_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, amount);
            statement.setString(2, cardNumber);

            // Ejecutar la consulta de actualización
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

            // Cerrar la conexión y liberar los recursos
            statement.close();
            connection.close();
        } catch (SQLException e) {
            success = false;
        }
        return success;
    }

    // Función para mostrar mensaje de error
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
