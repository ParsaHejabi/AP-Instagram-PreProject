package Client;

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

    ArrayList<Profile> following;
    ArrayList<Profile> followers;
    ArrayList<Post> posts;

    public Profile(String email, String password, String username, String fullName, String biography) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.fullName = fullName;
        this.biography = biography;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        posts = new ArrayList<>();
    }
}