package com.poscinema.pos_cinema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Obtener el tamaño de la pantalla
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("POS CINEMA");
        stage.setScene(scene);
        stage.setMinWidth(459); // Establecer el ancho mínimo
        stage.setMinHeight(620); // Establecer la altura mínima

        //stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}