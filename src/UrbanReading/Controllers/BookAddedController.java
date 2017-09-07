package UrbanReading.Controllers;

import javafx.event.ActionEvent;


import java.io.IOException;

public class BookAddedController {

    MainSelectorController msc = new MainSelectorController();

    public void addAnotherBook(ActionEvent event) throws IOException{
        msc.addToCollection(event);
    }

    public void viewCollection(ActionEvent event) throws IOException{
        msc.viewCollection(event);
    }
}
