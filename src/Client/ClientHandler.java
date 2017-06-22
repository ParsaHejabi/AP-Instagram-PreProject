package Client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by parsahejabi on 6/21/17.
 */
public class ClientHandler implements Runnable{
    Socket clientSocket;
    ObjectOutputStream clientOutputStream;
    ObjectInputStream clientInputStream;
    String clientMessage;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean isEmailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientInputStream = new ObjectInputStream(clientSocket.getInputStream());

            do {
                clientMessage = clientInputStream.readUTF();
                System.out.println(Thread.currentThread().getName() + " said: " + clientMessage);
                if (clientMessage.equals("Login")){
                    System.out.println("Logging in...");
                    String usernameOrEmail = clientInputStream.readUTF();
                    String password = clientInputStream.readUTF();
                    if (isEmailValid(usernameOrEmail)){
                        if (loginChecker(usernameOrEmail,password,true)){
                            clientOutputStream.writeBoolean(true);
                            clientOutputStream.flush();
                            login(usernameOrEmail);
                        }
                        else{
                            clientOutputStream.writeBoolean(false);
                            clientOutputStream.flush();
                        }
                    }
                    else {
                        if (loginChecker(usernameOrEmail,password)){
                            clientOutputStream.writeBoolean(true);
                            clientOutputStream.flush();
                            login(usernameOrEmail);
                        }
                        else{
                            clientOutputStream.writeBoolean(false);
                            clientOutputStream.flush();
                        }
                    }
                }
                else if (clientMessage.equals("Signup")){
                    System.out.println("Singing up...");
                    String email = clientInputStream.readUTF();
                    String username = clientInputStream.readUTF();
                    String password = clientInputStream.readUTF();
                    String registerStatus = registerChecker(email, username);
                    if (registerStatus.equals("Correct")){
                        clientOutputStream.writeUTF("Correct");
                        clientOutputStream.flush();
                        String fullName = clientInputStream.readUTF();
                        String biography = clientInputStream.readUTF();
                        String pictureStatus = clientInputStream.readUTF();

                        Profile profile = null;
                        if (pictureStatus.equals("Pic"))
                        {
                            File dir = new File(Server.profilesDir, username+"/");
                            dir.mkdirs();
                            File profilePic = new File(dir,"profilePic");
                            profilePic.createNewFile();
                            byte[] bytes = ((byte[]) clientInputStream.readObject());
                            Files.write(profilePic.toPath(),bytes );
                            profile = new Profile(email,password,username,fullName,biography, profilePic);
                        }
                        else if (pictureStatus.equals("Skip"))
                        {
                            File file = new File(Server.profilesDir, username+"/");
                            File profilePic = new File(file,"profilePic");
                            File defaultProfilePic = new File("Assets/defaultProfilePicture.png");
                            Files.write(profilePic.toPath(), Files.readAllBytes(defaultProfilePic.toPath()));
                            profile = new Profile(email,password,username,fullName,biography, profilePic);
                        }

                        Server.profiles.add(profile);
                        Server.serialize(profile);
                        login(username);
                    }
                    else if (registerStatus.equals("Email")){
                        clientOutputStream.writeUTF("Email");
                        clientOutputStream.flush();
                    }
                    else if (registerStatus.equals("Username")){
                        clientOutputStream.writeUTF("Username");
                        clientOutputStream.flush();
                    }
                }

            }while (!clientMessage.equals("Exit"));
            System.out.println(Thread.currentThread().getName() + " is closed!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void login(String usernameOrEmail) throws IOException {
        clientOutputStream.writeObject(profileFinder(usernameOrEmail));
        clientOutputStream.flush();
    }

    private String registerChecker(String email, String username) throws IOException {
        for (Profile p:Server.profiles){
            if (email.equals(p.email)){
                return "Email";
            }
            else if (username.equals(p.username)){
                return "Username";
            }
        }
        return "Correct";
    }

    private boolean loginChecker(String username, String password) {
        for (Profile p:Server.profiles){
            if (username.equals(p.username) && password.equals(p.password)){
                return true;
            }
        }
        return false;
    }

    private boolean loginChecker(String email, String password, boolean nothing) {
        for (Profile p:Server.profiles){
            if (email.equals(p.email) && password.equals(p.password)){
                return true;
            }
        }
        return false;
    }

    private Profile profileFinder(String usernameOrEmail){
        for (Profile p:Server.profiles){
            if (isEmailValid(usernameOrEmail)){
                if (usernameOrEmail.equals(p.email)){
                    return p;
                }
            }
            else{
                if (usernameOrEmail.equals(p.username)){
                    return p;
                }
            }
        }
        return null;
    }

}
