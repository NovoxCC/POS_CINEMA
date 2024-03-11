package com.poscinema.pos_cinema;

import  java.sql.Connection;
import  java.sql.DriverManager;

public class DatabaseConnection {
    public Connection  databaselink;

    public Connection getConnection() {
        String databaseName = "pos_cinema";
        String databaseUser = "root";
        String databasePassword = "123456789";
        String url = "jdbc:mysql://localhost/" +  databaseName;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);

        }catch (Exception e){
            e.printStackTrace();
        }
        return databaselink;
    }
}
