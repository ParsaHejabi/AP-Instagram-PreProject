package Client;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by parsahejabi on 6/21/17.
 */
public class Post {
    Profile owner;
    Date uploadDate;
    File image;
    ArrayList<Profile> liked;
    ArrayList<Comment> comments;
    Comment caption;

}
