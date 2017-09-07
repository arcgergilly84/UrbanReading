package UrbanReading.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class AddToCollectionController implements Initializable{

    @FXML TextField title_field;
    @FXML TextField isbn_field;
    @FXML ChoiceBox subject_box;
    @FXML Label file_upload_label;
    @FXML TextArea description_field;
    @FXML Label pdf_label;

    String filePathName = "";
    FileChooser fc = null;
    int pdfId = 0;
    private byte[] pdf = null;


    public void uploadFile(ActionEvent event){

        fc = new FileChooser();
        fc.setTitle("upload File");
        File filePath = fc.showOpenDialog(new Stage());
        filePathName = filePath.getPath().replace("\\","/");
        file_upload_label.setText(filePathName);
    }

    public void defaultFile(ActionEvent event){
        file_upload_label.setText("C:/Users/david/Pictures/genericBookCover.jpg");
    }

    public void AddBook(ActionEvent event) throws SQLException, IOException{
        Controller control = new Controller();
        Connection con = control.connectDB();
        PreparedStatement statement  = con.prepareStatement("INSERT INTO book_collection(book_title, isbn, subject, description, user_id,file, pdf_id) VALUES (?,?,?,?,?,?,?)");
        statement.setString(1,title_field.getText());
        statement.setString(2,isbn_field.getText());
        statement.setString(3,subject_box.getSelectionModel().getSelectedItem().toString());
        statement.setString(4,description_field.getText());
        statement.setInt(5, MainSelectorController.userID);
        statement.setString(6,file_upload_label.getText());
        statement.setInt(7,pdfId);
        statement.executeUpdate();

        addBookPopUp(event);
        statement.close();
        con.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        subject_box.setItems(FXCollections.observableArrayList("Science",
                "Web Design",
                "Programming",
                "Photography",
                "Mathematics",
                "Sci-Fi",
                "Fiction",
                "Non-Fiction",
                "Cyber Security",
                "Psychology",
                "Other..."));
    }

    private void addBookPopUp(ActionEvent event) throws IOException{
        Parent bookPop = FXMLLoader.load(getClass().getResource("../Views/BookAdded.fxml"));
        Scene bookPopView = new Scene(bookPop);

        Stage bookPopWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        bookPopWindow.setScene(bookPopView);
        bookPopWindow.show();
    }

    public void uploadPdf(ActionEvent event) throws IOException, SQLException{

        fc = new FileChooser();
        fc.setTitle("upload PDF");
        File pdf = fc.showOpenDialog(new Stage());
        filePathName = pdf.getPath().replace("\\","/");
        pdf_label.setText(filePathName);

        FileInputStream fis = new FileInputStream(pdf);
        int len = (int)pdf.length();
        Controller control = new Controller();
        Connection con = control.connectDB();
        PreparedStatement statement  = con.prepareStatement("INSERT INTO files(filename, filesize, pdf) VALUES (?,?,?)");
        statement.setString(1,pdf.getName());
        statement.setInt(2,len);
        statement.setBinaryStream(3,fis,len);
        statement.executeUpdate();

        getID(pdf.getName());

        statement.close();
        con.close();
    }

    private int getID(String name) throws SQLException{
        Controller control = new Controller();
        Connection con = control.connectDB();
        PreparedStatement statement  = con.prepareStatement("SELECT pdf_id FROM files WHERE filename = ?");
        statement.setString(1,name);

        ResultSet rs = statement.executeQuery();
        if(rs.next()) pdfId = rs.getInt("pdf_id");
        statement.close();
        con.close();
        return pdfId;

    }



}
