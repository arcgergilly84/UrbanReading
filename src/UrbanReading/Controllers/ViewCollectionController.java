package UrbanReading.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import UrbanReading.Book;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class ViewCollectionController implements Initializable{

    @FXML Label collection_id_label;
    @FXML Label title_label;
    @FXML Label subject_label;
    @FXML Label isbn_label;
    @FXML Label description_label;
    @FXML ImageView imageView;
    @FXML Button next_button;

    ArrayList<Book> books = new ArrayList<Book>();
    String filename;
    int pdfId;

    public void nextBook(ActionEvent event){
            int i = 1;
            title_label.setText(books.get(i).getTitle());
            isbn_label.setText(books.get(i).getISBN());
            subject_label.setText(books.get(i).getSubject());
            description_label.setText(books.get(i).getDescription());
            collection_id_label.setText(Integer.toString(books.get(i).getBook_id()));
            imageView.setImage(new Image("File:" + books.get(i).getImage()));
            pdfId = books.get(i).getPdf_id();
            i++;
            if (i == books.size()){
                next_button.setDisable(true);
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        next_button.setDisable(true);
        try{ getCollection();} catch (Exception e){
            System.out.println(e);
        }
        showBook();
    }

    public void getCollection() throws SQLException{

        Controller control = new Controller();
        Connection con = control.connectDB();
        PreparedStatement stat = con.prepareStatement("SELECT * FROM book_collection WHERE user_id=?");
        stat.setInt(1, MainSelectorController.userID);

        ResultSet rs = stat.executeQuery();

        while (rs.next()){
            String title = rs.getString("book_title");
            String ISBN = rs.getString("isbn");
            String subject = rs.getString("subject");
            String description = rs.getString("description");
            String image = rs.getString("file");
            int book_id = rs.getInt("collection_id");
            int pdfId = rs.getInt("pdf_id");
            Book book = new Book(title,ISBN, subject, description,image,book_id,pdfId);
            books.add(book);
        }
    }

    public void showBook(){

           title_label.setText(books.get(0).getTitle());
           isbn_label.setText(books.get(0).getISBN());
           subject_label.setText(books.get(0).getSubject());
           description_label.setText(books.get(0).getDescription());
           collection_id_label.setText(Integer.toString(books.get(0).getBook_id()));
           imageView.setImage(new Image("File:" + books.get(0).getImage()));
           pdfId = books.get(0).getPdf_id();
           if (books.size() > 1){
               next_button.setDisable(false);
           }
    }

    public void addToCollection(ActionEvent event) throws IOException{
        MainSelectorController ms = new MainSelectorController();
        ms.addToCollection(event);
    }

    public void viewPdf(ActionEvent event) throws IOException, SQLException {

        byte[] fileBytes;

        Controller control = new Controller();
        Connection con = control.connectDB();
        PreparedStatement statement  = con.prepareStatement("SELECT pdf, filename FROM files WHERE pdf_id =?");
        statement.setInt(1, pdfId);

        ResultSet rs = statement.executeQuery();
        if (rs.next()){
            fileBytes = rs.getBytes("pdf");
            filename = rs.getString("filename");
            OutputStream newFile = new FileOutputStream("D:/pdfs/" + filename);
            newFile.write(fileBytes);
            newFile.close();

        }
        Desktop.getDesktop().open(new File("D:/pdfs/" + filename));

        statement.close();
        con.close();

    }
}
