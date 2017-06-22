package Client;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by parsahejabi on 6/21/17.
 */
public class Profile implements Serializable{
    String email;
    String password;
    String username;
    String fullName;
    String biography;
    File profilePicture;

    ArrayList<Profile> following;
    ArrayList<Profile> followers;
    ArrayList<Post> posts;

    public Profile(String email, String password, String username, String fullName, String biography, File profilePicture) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.fullName = fullName;
        this.biography = biography;
        this.profilePicture = profilePicture;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        posts = new ArrayList<>();
    }
}