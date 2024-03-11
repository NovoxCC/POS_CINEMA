package com.poscinema.pos_cinema;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controlsfx.control.action.Action;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.util.ResourceBundle;

public class HelloController{
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;

    public void  signInButton (ActionEvent event){
        DatabaseConnection connectionNow = new  DatabaseConnection();
        Connection connectDB = connectionNow.getConnection();
        String user = usernameField.getText();
        String password = passwordField.getText();


        String connectQuery = "";
    }

}