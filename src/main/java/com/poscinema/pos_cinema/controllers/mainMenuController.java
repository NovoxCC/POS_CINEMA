package com.poscinema.pos_cinema.controllers;


import com.poscinema.pos_cinema.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

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


}

