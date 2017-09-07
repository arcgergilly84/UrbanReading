package UrbanReading.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.io.IOException;

public class UserCreationController {

    public void acceptcreation(ActionEvent event) throws IOException{
        Parent loginView = FXMLLoader.load(getClass().getResource("../Views/Main.fxml"));
        Scene loginViewScene = new Scene(loginView);

        Stage loginViewWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginViewWindow.setScene(loginViewScene);
        loginViewWindow.show();
    }
}
