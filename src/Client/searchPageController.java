package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Movahed on 6/23/2017.
 */
public class searchPageController implements Initializable{
    @FXML
    TextField searchField;

    static ArrayList<Profile> searchedProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Client.refreshOwner();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("searchPageController refresh failed");
        }
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            Client.sendMessage("Profile:"+newValue);
            if(newValue.length()>2) {
                try {
                    searchedProfile = ((ArrayList<Profile>) Client.clientInputStream.readObject());
                    showSearchRes();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void showSearchRes()
    {
        System.out.println("search res ->" );
        for (Profile p :
                searchedProfile) {
            System.out.println("\t"+p.fullName + " is full name and username is"+ p.username);
        }
    }
}
