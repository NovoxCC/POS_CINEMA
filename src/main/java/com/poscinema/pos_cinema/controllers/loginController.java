package com.poscinema.pos_cinema.controllers;

import com.poscinema.pos_cinema.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class loginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    void OnsignInButton() {
        User user = new User();
        if(User.login(usernameField.getText(),passwordField.getText())){
            user.setUsername(usernameField.getText());
            user.setRoleId(User.getRoleId(user.getUsername()));
                try {
                    // Cargar la nueva escena del menú principal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/main-Menu-view.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la nueva escena
                    mainMenuController mainMenuController = loader.getController();

                    // Pasar el objeto User al controlador del menú principal
                    mainMenuController.setUser(user);
                    // Crear una nueva escena con la vista cargada
                    Scene scene = new Scene(root);

                    // Obtener el escenario actual
                    Stage stage = (Stage) usernameField.getScene().getWindow();

                    // Establecer la nueva escena en el escenario y mostrarla
                    stage.setScene(scene);

                    // Maximizar la ventana
                    stage.setMaximized(true);
                    stage.setFullScreen(true);

                    stage.show();

                }catch (IOException e){
                    Logger logger = Logger.getLogger(loginController.class.getName());
                    logger.log(Level.SEVERE, "Error al cargar la nueva escena", e);
                }
        }
    }
}