package Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by parsahejabi on 6/23/17.
 */
public class peoplePageController implements Initializable {
    static Profile profile;
    @FXML
    private Circle profilePicture;
    @FXML
    private Label fullName;
    @FXML
    private Label biography;
    @FXML
    private Label followingNum;
    @FXML
    private Label followerNum;
    @FXML
    private Label postNum;
    @FXML
    private Label username;
    @FXML
    private Button followUnfollowButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            profile = (Profile) Client.clientInputStream.readObject();
            Client.refreshOwner();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        profilePicture.setFill(new ImagePattern(new Image(profile.profilePicture.toURI().toString())));
        fullName.setText(profile.fullName);
        biography.setText(profile.biography);
        followerNum.setText(Integer.toString(profile.followers.size()));
        followingNum.setText(Integer.toString(profile.following.size()));
        postNum.setText(Integer.toString(profile.posts.size()));
        username.setText(profile.username);
        if (Client.profileOwner.following.contains(profile)){
            followUnfollowButton.setText("Following");
            followUnfollowButton.setStyle("-fx-background-color:#f4f4f4;" +
                    "-fx-text-fill:black;" +
                    "-fx-border-color:black;");
        }
        else {
            followUnfollowButton.setText("Follow");
            followUnfollowButton.setStyle("-fx-background-color:#3897f0;" +
                    "-fx-text-fill:white;" +
                    "-fx-border-color:#3897f0");
        }
        followUnfollowButton.setOnAction(event -> {
            if (Client.profileOwner.following.contains(profile)){
                try {
                    Client.clientOutputStream.writeUTF("Unfollow");
                    Client.clientOutputStream.flush();
                    Client.refreshOwner();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                followUnfollowButton.setText("Follow");
                followUnfollowButton.setStyle("-fx-background-color:#3897f0;" +
                        "-fx-text-fill:white;" +
                        "-fx-border-color:#3897f0");
            }
            else {
                try {
                    Client.clientOutputStream.writeUTF("Follow");
                    Client.clientOutputStream.flush();
                    Client.refreshOwner();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                followUnfollowButton.setText("Following");
                followUnfollowButton.setStyle("-fx-background-color:#f4f4f4;" +
                        "-fx-text-fill:black;" +
                        "-fx-border-color:black;");
            }
        });
    }

    public void goToProfile1() throws IOException, ClassNotFoundException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("profilePage1.fxml")));
        scene.getStylesheets().add("Stylesheet/style.css");
        ClientUI.sceneChanger(scene, "Profile");
        Client.clientOutputStream.writeUTF("Profile1");
        Client.clientOutputStream.flush();
        Client.refreshOwner();
    }
    public void goToHome() throws IOException, ClassNotFoundException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("homePage.fxml")));
        scene.getStylesheets().add("Stylesheet/style.css");
        ClientUI.sceneChanger(scene, "Home");
        Client.clientOutputStream.writeUTF("Home");
        Client.clientOutputStream.flush();
        Client.refreshOwner();


    }

    public void goToSearch() throws IOException, ClassNotFoundException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("searchPage.fxml")));
        scene.getStylesheets().add("Stylesheet/style.css");
        ClientUI.sceneChanger(scene, "Search");
        Client.clientOutputStream.writeUTF("Search");
        Client.clientOutputStream.flush();
        Client.refreshOwner();
    }
}
