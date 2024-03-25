package com.poscinema.pos_cinema;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection databaselink;

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(DatabaseConfig.DATABASE_URL, DatabaseConfig.DATABASE_USER, DatabaseConfig.DATABASE_PASSWORD);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Fallo en la conexion a la base de datos\n" + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
        return databaselink;
    }

    public void closeConnection() {
        if (databaselink != null) {
            try {
                databaselink.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
