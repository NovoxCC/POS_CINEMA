package com.poscinema.pos_cinema.controllers;

import com.poscinema.pos_cinema.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;

    @FXML
    void OnsignInButton() throws IOException {
        User user = new User();
        if(User.login(usernameField.getText(),passwordField.getText())){
            user.setUsername(usernameField.getText());
            user.setRoleId(User.getRoleId(user.getUsername()));
            // Usuario autenticado, navegar a otra escena
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poscinema/pos_cinema/main-Menu-view.fxml"));
            Parent root = loader.load();
            // Crear una nueva escena con la vista cargada
            Scene scene = new Scene(root);
            // Obtener el escenario actual
            Stage stage = (Stage) usernameField.getScene().getWindow();
            // Establecer la nueva escena en el escenario y mostrarla
            stage.setScene(scene);
            stage.show();
        }


    }

}