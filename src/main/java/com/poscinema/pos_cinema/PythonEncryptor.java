package com.poscinema.pos_cinema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonEncryptor {
    private Process process; // Añadimos un campo para almacenar el proceso

    public String encryptPassword(String username, String password) {
        try {
            // Crear el proceso para ejecutar el script de Python
            System.out.println("user " + username);
            System.out.println("password " + password);
            ProcessBuilder builder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\src\\main\\resources\\scripts\\PasswordEncryption.py", username, password);
            Process process = builder.start();
            System.out.println("encripto iniciado");
            // Obtener el lector de entrada para leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.println("lectura de buffer");
            // Leer la salida del proceso línea por línea
            String encryptedPassword = reader.readLine();
            System.out.println("Salida del buffer: " + encryptedPassword);
            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Error en la ejecución del script de Python. Código de salida: " + exitCode);
                return null;
            }
            return encryptedPassword;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void close() {
        // Cerrar el proceso si está abierto
        if (process != null) {
            process.destroy();
        }
    }
}
