package mit.edu.stemplusplus.helper;

/**
 *  Project.java - Represents a project created by a user
 *  @author Ashley Smith
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {
	private String name;
	private Ranking rank;
	private User user;
	private List<Step> steps;
	private List<Comment> comments;
	private String description;
	private Date date;
	// should this hold the original date or the date of the most recent change?
	private String category;
	
	public Project(){
		
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
	}
	
	

	public void setName(String name) {
		this.name = name;
	}

	public void setRank(Ranking rank) {
		this.rank = rank;
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
}
