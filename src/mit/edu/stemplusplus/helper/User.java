package mit.edu.stemplusplus.helper;

import java.io.Serializable;

/**
 *  User.java - Represents a user
 *  @author Ashley Smith
 */


public class User implements Serializable{
    private String username; //email address
    public User(String u) {
        username = u;
    }
    public String getUsername() { return username; }
}
