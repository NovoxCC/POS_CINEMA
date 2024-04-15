package com.poscinema.pos_cinema.controllers;


import com.poscinema.pos_cinema.models.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
    private void handleButtonClick(MouseEvent event) throws IOException {
        User.singOut();
    }

}

