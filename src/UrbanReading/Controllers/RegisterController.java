package UrbanReading.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
    /*
        FXML FX:IDs Mapping.
     */
    @FXML private TextField firstName_field;
    @FXML private TextField lastName_field;
    @FXML private TextField email_field;
    @FXML private TextField registerPassword_field;
    @FXML private TextField registerPasswordConfirm_field;
    @FXML private TextField username_field;
    @FXML private Label validationLabel;
    @FXML private Label passwordMatch;
    @FXML private Label emailValidation;

    boolean passValidation = false;
    boolean emailValid = false;

     /*
        When Register button pushed data validated and persisted to DB when correct and Email confirmation
        sent to user with username.
     */
    public void registerUser(ActionEvent event) throws SQLException {
        validationLabel.setText("");
        boolean validate = validation();
        if (validate && emailValid && passValidation) {
            connectToDB();
            try {
                gotoUserCreation(event);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    /*
        Clear all fields.
     */
    public void clearData(ActionEvent event) {
        String clear = "";
        firstName_field.setText(clear);
        lastName_field.setText(clear);
        username_field.setText(clear);
        email_field.setText(clear);
        registerPassword_field.setText(clear);
        registerPasswordConfirm_field.setText(clear);
     }

     public void passwordValidation(String password, String confirmPassword) {

         if (password.equals(confirmPassword)) {
             passwordMatch.setText("Match!");
             passValidation = true;
         } else {
             passwordMatch.setText("passwords do not match!");
             passValidation = false;
         }
     }

     public void emailValidation(String email){
         if(email.contains("@")){
             emailValid = true;
         } else {
             emailValidation.setText("not an Email");
             emailValid = false;
         }
     }

     public boolean validation(){
         if (firstName_field.getText().equals("") || lastName_field.getText().equals("") || email_field.getText().equals("") || registerPasswordConfirm_field.getText().equals("") || registerPassword_field.getText().equals("") || username_field.getText().equals("")){
             validationLabel.setText("Please complete all fields");
             return false;
         } else {
             emailValidation(email_field.getText());
             passwordValidation(registerPassword_field.getText(),registerPasswordConfirm_field.getText());
             return true;
         }
     }

    public void connectToDB(){
        Controller control = new Controller();
        try {
            Connection con = control.connectDB();
            PreparedStatement statement  = con.prepareStatement("INSERT INTO user_table(firstName, lastName, userName, email, pass) VALUES (?,?,?,?,?)");
            statement.setString(1,firstName_field.getText());
            statement.setString(2,lastName_field.getText());
            statement.setString(3,username_field.getText());
            statement.setString(4,email_field.getText());
            statement.setString(5,registerPasswordConfirm_field.getText());
            statement.execute();

            statement.close();
            con.close();
        } catch (SQLException e){

       }

    }

    public void gotoUserCreation(ActionEvent event) throws IOException {
        Parent userCreation = FXMLLoader.load(getClass().getResource("../Views/UserCreation.fxml"));
        Scene userScene = new Scene(userCreation);

        Stage userStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        userStage.setScene(userScene);
        userStage.show();
    }


}
