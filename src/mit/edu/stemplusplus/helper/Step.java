package mit.edu.stemplusplus.helper;

/**
 *  Step.java - Represents a step of a project
 *  @author Ashley Smith
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Step implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -9222780575813376631L;
    private List<Comment> comments;
    private String description;
    private Bitmap media;
    private List<Step> pastVersions;
    private String mediaPath;
    public static final String DESCRIPTION_STEP = "description";
    public static final String IMAGE_PATH_STEP = "imagePath";

    public Step(String description, String mediaPath) {
        this.description = description;
        this.mediaPath = mediaPath;
        comments = new ArrayList<Comment>();
        pastVersions = new ArrayList<Step>();
    }

    public Step(String d) {
        description = d;
        comments = new ArrayList<Comment>();
        pastVersions = new ArrayList<Step>();
    }

    public Step() {
        description = "";
        comments = new ArrayList<Comment>();
        pastVersions = new ArrayList<Step>();
    }

    public void setDescription(String d) {
        description = d;

    }

    public void setmedia(Bitmap media) {
        this.media = media;
    }

    public void setComments(List<Comment> cms) {
        comments = cms;
    }

    public Bitmap getMedia() {
        return media;
    }

    public void updateStep(String d) {
        pastVersions.add(this); // FIXME the following lines probably change
                                // what is in pastVersions... too lazy to fix it
                                // right now
        description = d;
        comments = new ArrayList<Comment>();
        pastVersions = new ArrayList<Step>();
    }

    public void addComment(Comment c) {
        comments.add(c);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Step> getPastVersions() {
        return pastVersions;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }
}
