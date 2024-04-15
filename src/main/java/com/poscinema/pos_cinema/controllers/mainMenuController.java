package com.poscinema.pos_cinema.controllers;


import com.poscinema.pos_cinema.HelloApplication;
import com.poscinema.pos_cinema.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class mainMenuController {
    private User user;

    public void setUser(User user) {
        this.user = user;
        setTextToLabel();
    }

    @FXML
    private Label labelusername;

    // Método para establecer el texto del Label
    private void setTextToLabel() {
        if (user != null) {
            labelusername.setText(user.getUsername());
        }
    }



    @FXML
    private ButtonBar buttonBar;

    @FXML
    public void OnsignOffButton(ActionEvent actionEvent) throws IOException {
        // Cargar la nueva escena del menú principal
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/poscinema/pos_cinema/login-view.fxml"));
        Parent root = loader.load();

        // Crear una nueva escena con la vista cargada
        Scene scene = new Scene(root);

        // Obtener el BorderPane desde el FXML
        BorderPane borderPane = (BorderPane) root;

        // Obtener el escenario actual desde la ventana principal
        Stage stage = (Stage) labelusername.getScene().getWindow();

        // Establecer la nueva escena en el escenario y mostrarla
        stage.setScene(scene);

        stage.centerOnScreen();

        stage.show();
    }

}

