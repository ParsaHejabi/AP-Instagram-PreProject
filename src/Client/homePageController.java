package Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by parsahejabi on 6/23/17.
 */
public class homePageController implements Initializable {
    @FXML
    ListView<VBox> posts;

    static ArrayList<Post> postArrayList;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            Client.refreshOwner();
            postArrayList = ((ArrayList<Post>) Client.clientInputStream.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Post p:postArrayList){
            VBox post = new VBox(20);
            HBox postOwnerHBox = new HBox(30);
            Circle postOwnerProfilePicture = new Circle(35,new ImagePattern(new Image(p.owner.profilePicture.toURI().toString())));
            Hyperlink postOwnerUsername = new Hyperlink(p.owner.username);
            postOwnerHBox.getChildren().addAll(postOwnerProfilePicture,postOwnerUsername);
            ImageView postImageView = new ImageView(new Image(p.image.toURI().toString()));
            postImageView.setFitWidth(500);
            postImageView.setFitHeight(500);
            HBox postButtonsHBox = new HBox(10);
            ImageView likeButtonImageView = new ImageView("Client/Assets/likeButton.png");
            if (p.canComment){
                ImageView commentButtonImageView = new ImageView("Client/Assets/commentButton.png");
                postButtonsHBox.getChildren().addAll(likeButtonImageView, commentButtonImageView);
            }
            else {
                postButtonsHBox.getChildren().add(likeButtonImageView);
            }
            Hyperlink likesHyperlink = new Hyperlink(p.liked.size() + " likes");
            Hyperlink postOwnerUsernameHyperLink = new Hyperlink(p.owner.username);
            TextArea postCaptionTextArea = new TextArea(p.caption);
            //TODO age khastim ye kari konim ke date alano begire menhaye oon moghe kone
            Label postDateLabel = new Label(p.uploadDate.toString());
            if (!postCaptionTextArea.getText().isEmpty()){
                if (p.canComment){
                    Hyperlink commentHyperlink = new Hyperlink("View all " + p.comments.size() + " comments");
                    post.getChildren().addAll(postOwnerHBox, postImageView, postButtonsHBox, likesHyperlink, postOwnerUsernameHyperLink, postCaptionTextArea, commentHyperlink, postDateLabel);
                }
                else{
                    post.getChildren().addAll(postOwnerHBox, postImageView, postButtonsHBox, likesHyperlink, postOwnerUsernameHyperLink, postCaptionTextArea, postDateLabel);
                }
            }
            else{
                if (p.canComment){
                    Hyperlink commentHyperlink = new Hyperlink("View all " + p.comments.size() + " comments");
                    post.getChildren().addAll(postOwnerHBox, postImageView, postButtonsHBox, likesHyperlink, postOwnerUsernameHyperLink, commentHyperlink, postDateLabel);
                }
                else{
                    post.getChildren().addAll(postOwnerHBox, postImageView, postButtonsHBox, likesHyperlink, postOwnerUsernameHyperLink, postDateLabel);
                }
            }
            posts.getItems().addAll(post);
        }
    }
}
