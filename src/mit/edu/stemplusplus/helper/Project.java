package mit.edu.stemplusplus.helper;

/**
 *  Project.java - Represents a project created by a user
 *  @author Ashley Smith
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;

public class Project implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8564171545740507368L;
	private String name;
	private Ranking rank;
	private Bitmap profilePic;
	private User user;
	private List<Step> steps;
	private List<Comment> comments;
	private String description;
	private Date date;
	private String category;
	private ArrayList<? extends Object> images;
	private String id;
	private String profilePicPath;
	private int projectRanking;

	public Project() {
		rank = null;
		comments = null;
		date = new Date();
		category = "science";
		description = "Nothing much";
		user = null;
		name = "Tran";
		this.profilePicPath = "";
		this.projectRanking = 0;
	}
	
	public Project(String name, String description) {
		rank = null;
		comments = null;
		date = new Date();
		category = "science";
		this.description = description;
		user = null;
		this.name = name;
		this.projectRanking = 0;
	}

	/** this is for testing only, don't use */
	public Project(ArrayList<? extends Object> imagePath, String description) {
		this.images = imagePath;
		this.description = description;
		steps = new ArrayList<Step>();
		this.projectRanking = 0;
	}

	public Project(String n, User u, String d, String c) {
		name = n;
		rank = new Ranking(this, u);
		user = u;
		steps = new ArrayList<Step>();
		comments = new ArrayList<Comment>();
		description = d;
		date = new Date();
		category = c;
		this.projectRanking = 0;
	}
	
	
	public Project(String n, User u, String d, String c,
			ArrayList<? extends Object> images) {
		this(n, u, d, c);
		this.images = images;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRank(Ranking rank) {
		this.rank = rank;
	}
	
	public void setProfilePic(Bitmap bmp){
	    profilePic = bmp;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void updateProject() {
		// TODO not sure how this method was intended to be implemented
		// Shirley should handle this method since she is handling editing of
		// projects
	}

	public List<Step> getSteps() {
		return steps;
	}
	public Bitmap getProfilePic(){
	    return profilePic;
	}
	public void addStep(Step s) {
		steps.add(s);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void addComment(Comment c) {
		comments.add(c);
	}

	public String getName() {
		return name;
	}

	public User getUser() {
		return user;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public String getCategory() {
		return category;
	}

	public void incrementRank() {
		rank.incrementRank();
	}

	public void decrementRank() {
		rank.decrementRank();
	}

	public void setRank(int r) {
		rank.setRank(r);
	}

	public int getRank() {
		return rank.getRank();
	}

	public ArrayList<? extends Object> getImages() {
		return images;
	}

	public void setImages(ArrayList<? extends Object> images) {
		this.images = images;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setProfileImagePath(String profilePath){
		this.profilePicPath = profilePath;
	}
	
	public String getProfileImagePath(){
		return this.profilePicPath;
		
	}

	public int getProjectRanking() {
		return projectRanking;
	}

	public void setProjectRanking(int projectRanking) {
		this.projectRanking = projectRanking;
	}
}
