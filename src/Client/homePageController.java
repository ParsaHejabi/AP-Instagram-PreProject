package Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Created by parsahejabi on 6/23/17.
 */
public class homePageController {
    public void goToProfile1() throws IOException, ClassNotFoundException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("profilePage1.fxml")));
        scene.getStylesheets().add("Stylesheet/style.css");
        ClientUI.sceneChanger(scene, "Set ProfilePicture");
        Client.clientOutputStream.writeUTF("Profile1");
        Client.clientOutputStream.flush();
        Client.refreshOwner();
    }
    public void goToHome() throws IOException, ClassNotFoundException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("homePage.fxml")));
        scene.getStylesheets().add("Stylesheet/style.css");
        ClientUI.sceneChanger(scene, "Set ProfilePicture");
        Client.clientOutputStream.writeUTF("Home");
        Client.clientOutputStream.flush();
        Client.refreshOwner();


    }
}
