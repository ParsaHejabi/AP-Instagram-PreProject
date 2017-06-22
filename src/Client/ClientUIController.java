package Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;

import static Client.Client.clientInputStream;

public class ClientUIController {
    @FXML
    Button goToSignUpButton;
    @FXML
    Hyperlink goToLoginHyperLink;
    @FXML
    TextField emailTextField;
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    TextField fullnameTextField;
    @FXML
    TextArea biographyTextArea;
    @FXML
    Button finishSignUpButton;
    @FXML
    TextField emailOrUsernameLoginTextField;
    @FXML
    PasswordField passwordLoginPasswordField;
    @FXML
    Button loginButton;

    public void goToLoginPage() {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("loginPage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.getStylesheets().add("Stylesheet/style.css");
        ClientUI.sceneChanger(scene, "Login");
    }

    public void goToSignUpPage() {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("signupPage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.getStylesheets().add("Stylesheet/style.css");
        ClientUI.sceneChanger(scene, "Sign Up");
    }
    public void signUp() throws IOException {
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        Client.clientOutputStream.writeUTF("Signup");
        Client.clientOutputStream.flush();
        Client.clientOutputStream.writeUTF(email);
        Client.clientOutputStream.flush();
        Client.clientOutputStream.writeUTF(username);
        Client.clientOutputStream.flush();
        Client.clientOutputStream.writeUTF(password);
        Client.clientOutputStream.flush();
        String serverResponse = clientInputStream.readUTF();
        if (serverResponse.equals("Correct")){
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("signupPage2.fxml")));
            scene.getStylesheets().add("Stylesheet/style.css");
            ClientUI.sceneChanger(scene, "Sign Up");
        }
        else if (serverResponse.equals("Username")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Oops!");
            alert.setContentText("this username exists!");
            alert.showAndWait();
        }
        else if (serverResponse.equals("Email")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Oops!");
            alert.setContentText("this email exists!");
            alert.showAndWait();
        }
    }
    public void signUp2() throws IOException, ClassNotFoundException {
        String fullname = fullnameTextField.getText();
        String biography = biographyTextArea.getText();
        Client.clientOutputStream.writeUTF(fullname);
        Client.clientOutputStream.flush();
        Client.clientOutputStream.writeUTF(biography);
        Client.clientOutputStream.flush();
        Client.profileOwner = (Profile) clientInputStream.readObject();
    }
    public void logIn() throws IOException, ClassNotFoundException {
        String usernameOrEmail = emailOrUsernameLoginTextField.getText();
        String password = passwordLoginPasswordField.getText();
        Client.clientOutputStream.writeUTF("Login");
        Client.clientOutputStream.flush();
        Client.clientOutputStream.writeUTF(usernameOrEmail);
        Client.clientOutputStream.flush();
        Client.clientOutputStream.writeUTF(password);
        Client.clientOutputStream.flush();
        boolean serverResponse = Client.clientInputStream.readBoolean();
        if (serverResponse){
            Client.profileOwner = (Profile) clientInputStream.readObject();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Oops!");
            alert.setContentText("email or username or password is incorrect!");
            alert.showAndWait();
        }
    }
}
