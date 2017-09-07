package UrbanReading.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/*
   Main Controller that deals with LoginPage.
 */
public class Controller{
    /*
        FXML FX:IDs mapping
     */
    @FXML public TextField username_field;
    @FXML public PasswordField password_field;
    @FXML private Label credentialsValidationLabel;
    @FXML public Button login_button;

    /*
            Instance variables to help validation.
    */
    public static String username;
    private String password;
    private int count = 0; //used to create a lock on failed attempts.
    public static Connection con;
    /*
        Makeshift Authentication on pressing Login.
     */
    public void loginButton(ActionEvent event) throws Exception {
        retrieveLoginfromDB();

        if(username_field.getText().equals(username) && password_field.getText().equals(password)){
            count = 0;
            mainSelectorButton(event);
        } else {
            count += 1;
            if(count == 3)
                credentialsValidationLabel.textProperty().set("Too many tries, Account Locked");
            else
                credentialsValidationLabel.textProperty().set("Credentials Incorrect");
        }
    }

    /*
        Switch to register form when Register Button pushed.
     */
    public void registerButton(ActionEvent event) throws IOException{

        Parent registerView = FXMLLoader.load(getClass().getResource("../Views/Register.fxml"));
        Scene registerScene = new Scene(registerView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(registerScene);
        window.show();

    }

    public void retrieveLoginfromDB(){
        try {
            connectDB();
            PreparedStatement statement = con.prepareStatement("SELECT userName,pass FROM user_table WHERE userName=? AND pass=?");
            statement.setString(1, username_field.getText());
            statement.setString(2, password_field.getText());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                username = rs.getString("userName");
                password = rs.getString("pass");
            }
            statement.close();
            con.close();
        }catch (SQLException e){

        }

    }

    public void mainSelectorButton(ActionEvent event) throws IOException{

        Parent selectorView = FXMLLoader.load(getClass().getResource("../Views/MainSelector.fxml"));
        Scene selectorScene = new Scene(selectorView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(selectorScene);
        window.show();

    }

    public Connection connectDB() throws SQLException{
        String host = "jdbc:mysql://127.0.0.1:55556/librarycollection";
        return con = DriverManager.getConnection(host,"root","password");
    }


    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username_field.requestFocus();
            }
        });

    }


}
