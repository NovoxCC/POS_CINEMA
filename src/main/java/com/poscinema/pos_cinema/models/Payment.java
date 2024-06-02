package com.poscinema.pos_cinema.models;

public abstract class Payment {
        protected int reservationId; // ID de la reserva asociada al pago
        protected int userId; // ID del usuario que realiza el pago
        protected int amount; // Monto del pago

        // Constructor, getters y setters

        // Método abstracto para procesar el pago
        public abstract void processPayment();

    public class cardPayment extends Payment {
        private String cardNumber; // Número de tarjeta
        private String cvv; // Código de seguridad (CVV)
        private String ownerId; // ID del propietario de la tarjeta
        private int balance; // Saldo de la tarjeta

        // Constructor, getters y setters

        // Implementación del método procesarPago para PagoTarjeta
        @Override
        public void processPayment() {

        }
    }

    public class cashPayment extends Payment {
        private String paymentMethod; // Método de pago (efectivo, transferencia, etc.)

        // Constructor, getters y setters

        // Implementación del método procesarPago para PagoEfectivo
        @Override
        public void processPayment() {
            // Implementación específica para procesar un pago en efectivo
        }
    }
}
