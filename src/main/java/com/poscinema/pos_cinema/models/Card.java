package com.poscinema.pos_cinema.models;

import java.util.Random;


public class Card {
    private final String cardNumber;
    private String cvv;
    private String ownerId;
    private int balance;

    // Constructor
    public Card(String cardNumber, String cvv, String ownerId, int balance) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.ownerId = ownerId;
        this.balance = balance;
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
        // Generar un número de tarjeta con un prefijo personalizado
        String customPrefix = "1234"; // Cambia este valor segun el prefijo personalizado
        String cardNumber = generateCardNumber(customPrefix);

        // Generar un CVV aleatorio de 3 dígitos
        String cvv = generateRandomCVV();

        // Crear y devolver una nueva instancia de Card
        return new Card( cardNumber, cvv, ownerId, balance);
    }

    // Método para generar un número de tarjeta aleatorio con un prefijo personalizado
    private static String generateCardNumber(String prefix) {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder(prefix);
        // Agregar los dígitos restantes del número de tarjeta (11 dígitos aleatorios)
        for (int i = 0; i < 11; i++) {
            cardNumber.append(random.nextInt(10)); // Agregar un dígito aleatorio del 0 al 9
        }
        // Calcular la cifra de control y agregarla al número de tarjeta
        String controlDigit = calculateControlDigit(cardNumber.toString());
        cardNumber.append(controlDigit);
        return cardNumber.toString();
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


}