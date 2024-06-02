package com.poscinema.pos_cinema.models;

import java.util.List;

public class Reservations {
        private String customerId;
        private String paymentStatus;
        private int scheduleId;
        private String paymentMethod;
        private int totalAmount;
        private List<Integer> seatIds; // Lista de IDs de asientos reservados
        private Payment payment; // Relación de asociación con la clase Pago

        // Constructor, getters y setters


        // Método para procesar el pago asociado a la reserva
        public void processPayment() {
            payment.processPayment();

        }
        // Método para hacer una reserva y guardarla en la base de datos
        public void makeReservation(String customerId, int scheduleId , int totalAmount, List<Integer> seatIds) {
                // Establecer los datos de la reserva
                this.customerId = customerId;
                this.scheduleId = scheduleId;
                this.totalAmount = totalAmount;
                this.seatIds = seatIds;
                // Procesar el pago asociado a la reserva

                processPayment();

                // Guardar la reserva en la base de datos (aquí se debería implementar la lógica para guardar la reserva en la base de datos)
                saveReservationInDatabase();
        }

        // Método privado para guardar la reserva en la base de datos
        private void saveReservationInDatabase() {
                // Implementación para guardar la reserva en la base de datos
        }

}

