package mit.edu.stemplusplus.helper;

/**
 *  Step.java - Represents a step of a project
 *  @author Ashley Smith
 */

import java.util.ArrayList;
import java.util.List;

public class Step {
    private List<Comment> comments;
    private String description;
    private List<Step> pastVersions;
    
    public Step(String d) {
        description = d;
        comments = new ArrayList<Comment>();
        pastVersions = new ArrayList<Step>();
    }
    public void updateStep(String d){
        pastVersions.add(this); //FIXME the following lines probably change what is in pastVersions... too lazy to fix it right now
        description = d;
        comments = new ArrayList<Comment>();
        pastVersions = new ArrayList<Step>();
    }
    public void addComment(Comment c) { comments.add(c); }
    public List<Comment> getComments() { return comments; }
    public List<Step> getPastVersions() { return pastVersions; }
    public String getDescription() { return description; }
}
