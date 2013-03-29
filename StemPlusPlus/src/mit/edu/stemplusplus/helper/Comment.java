package mit.edu.stemplusplus.helper;

/**
 *  Comment.java - Represents a comment for either a step or a project as a whole
 *  @author Ashley Smith
 */
public class Comment {
    private User user;
    private String comment;
    
    public Comment(User u, String c) {
        user = u; comment = c;
    }
    public User getUser() { return user; }
    public String getComment() { return comment; }
    public void setComment(String s) { comment = s; }
    public void deleteComment() { comment = "Comment deleted."; }
}
