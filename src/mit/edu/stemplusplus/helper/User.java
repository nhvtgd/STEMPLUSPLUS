package mit.edu.stemplusplus.helper;

/**
 *  User.java - Represents a user
 *  @author Ashley Smith
 */

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username; //email address
    private String password; //not sure if this should be stored here
    private Ranking rank;
    private List<Project> projects;
    public User(String u, String p) {
        username = u; password = p;
        projects = new ArrayList<Project>();
        rank = new Ranking(this);
    }
    public String getUsername() { return username; }
    public boolean signIn(String pass) { return (pass.equals(password)) ? true : false;}
    public int getRank() { return rank.getRank(); }
    public List<Project> getProjects() { return projects; }
}
