package com.poscinema.pos_cinema.controllers;


import com.poscinema.pos_cinema.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
        user.singOff((Stage) labelusername.getScene().getWindow());
    }

    @FXML
    private BorderPane mainMenu;

    public void OnviewSchedulesButton(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/day-schedule.fxml"));
            Parent centerContent = loader.load();
            mainMenu.setCenter(centerContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }


    public void Onnewcard(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/create-card.fxml"));
            Parent centerContent = loader.load();
            mainMenu.setCenter(centerContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }
}

