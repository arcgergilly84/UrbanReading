package UrbanReading.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainSelectorController implements Initializable {

    @FXML Label numberOfBooksinCollection;
    @FXML Label welcomeLabel;

    static int userID;
    private ArrayList<String> number = new ArrayList<String>();
    private Controller control = new Controller();
    private Connection con;

    String view = "";
    public void addToCollection(ActionEvent event) throws IOException{
        view = "../Views/AddToCollection.fxml";
        Parent addCollection = FXMLLoader.load(getClass().getResource(view));
        Scene addCollectionScene = new Scene(addCollection);

        Stage collectionStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        collectionStage.setScene(addCollectionScene);
        collectionStage.show();

    }

    public void viewCollection(ActionEvent event) throws IOException{
        view = "../Views/ViewCollection.fxml";
        Parent viewCollection = FXMLLoader.load(getClass().getResource(view));
        Scene viewCollectionScene = new Scene(viewCollection);

        Stage viewCollectionStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        viewCollectionStage.setScene(viewCollectionScene);
        viewCollectionStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){

        welcomeLabel.setText("Welcome " + Controller.username + "! How can I help you today?");
        try{
            getUserID();
            numberOfBooks();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void numberOfBooks() throws SQLException {
        con = control.connectDB();
        PreparedStatement statement = con.prepareStatement("SELECT book_title FROM book_collection WHERE user_id=?");
        statement.setInt(1,userID);

        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            number.add(rs.getString("book_title"));
        }
        numberOfBooksinCollection.setText(Integer.toString(number.size()));
        statement.close();
        con.close();
    }

    public void getUserID() throws SQLException {
        con = control.connectDB();
        PreparedStatement statement = con.prepareStatement("SELECT user_id FROM user_table WHERE userName=?");
        statement.setString(1,Controller.username);

        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            userID = rs.getInt("user_id");
        }
        statement.close();
        con.close();

    }
}







