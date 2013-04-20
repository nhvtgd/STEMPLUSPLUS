package mit.edu.stemplusplus.helper;

import java.io.Serializable;

/**
 *  Comment.java - Represents a comment for either a step or a project as a whole
 *  @author Ashley Smith
 */
public class Comment implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 822728201398074772L;
	private User user;
    private String comment;
    
    public static final String TITLE_PARSE = "title";
    public static final String DETAIL_PARSE = "details";
    
    public Comment(User u, String c) {
        user = u; comment = c;
    }
    
    public Comment(String comment){
    	this(new User("User"), comment);
    }
    public User getUser() { return user; }
    public String getComment() { return comment; }
    public void setComment(String s) { comment = s; }
    public void deleteComment() { comment = "Comment deleted."; }
}
