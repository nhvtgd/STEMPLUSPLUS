package mit.edu.stemplusplus.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import mit.edu.stemplusplus.StemPlusPlus;
import android.content.Context;
import android.util.Log;

public class ParseDatabase {
	public static void initProject(Context context) {
		Parse.initialize(context, "iqIztkJYN0f0Y8iPYLjhpVYYFpV9zmnpBAoKTP1s",
				"4Z2u2qEfF4NtBq8PyIGjfewuhTU1iC7iEdxapoV5");
		System.out.println("helo");
	}

	/**
	 * Create a ParseObject from a Project
	 * 
	 * @param project
	 *            A Project type
	 * @return A ParseObject
	 */
	public static ParseObject createParseObjectFromProject(Project project) {
		ParseObject projectObject = new ParseObject(StemPlusPlus.PROJECT_PARSE);
		projectObject.put(StemPlusPlus.NAME_PARSE, project.getName());
		projectObject.put(StemPlusPlus.RANKING_PARSE, project.getRank());
		projectObject
				.put(StemPlusPlus.USER_PARSE, project.getUser().toString());

		projectObject.put(StemPlusPlus.COMMENT_PARSE, project.getComments()
				.toString());
		projectObject.put(StemPlusPlus.DESCRIPTION_PARSE,
				project.getDescription());
		projectObject.put(StemPlusPlus.CATEGORY_PARSE, project.getCategory());
		return projectObject;
	}

	/**
	 * Create a ParseObject from a Project
	 * 
	 * @param project
	 *            A Project type
	 * @return A ParseObject
	 */
	public static ParseObject createParseObjectFromBasicProject(Project project) {
		ParseObject projectObject = new ParseObject("project");
		projectObject.put(StemPlusPlus.NAME_PARSE, project.getName());
		JSONArray stepArray = new JSONArray();
		for (int i = 0; i < project.getSteps().size(); i++) {
			JSONObject stepObject = new JSONObject();
			try {
				stepObject.put(Step.DESCRIPTION_STEP, project.getSteps().get(i)
						.getDescription());
				stepObject.put(Step.IMAGE_PATH_STEP, project.getSteps().get(i)
						.getMediaPath());
				stepObject.put(Step.NAME_STEP, project.getSteps().get(i).getName());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stepArray.put(stepObject);

		}

		if (makeCommentArray(project.getComments()) != null)
			projectObject.put(StemPlusPlus.COMMENT_PARSE,
				makeCommentArray(project.getComments()));
		
		projectObject.put(StemPlusPlus.PROJECT_RANKING_PARSE,
				project.getProjectRanking());
		projectObject.put(StemPlusPlus.PROFILE_PARSE,
				project.getProfileImagePath());
		projectObject.put(StemPlusPlus.STEP_PARSE, stepArray);
		projectObject.put(StemPlusPlus.DESCRIPTION_PARSE,
				project.getDescription());
		projectObject.put(StemPlusPlus.CATEGORY_PARSE, project.getCategory());
		return projectObject;
	}

	/**
	 * Retrieve the Project from the ParseObject
	 * 
	 * @param parseObject
	 *            A ParseObject
	 * @return A Project type
	 */
	public static Project getProjectFromParseObject(ParseObject parseObject) {
		String name = parseObject.getString(StemPlusPlus.NAME_PARSE);
		int rank = parseObject.getInt(StemPlusPlus.RANKING_PARSE);
		User user = (User) parseObject.get(StemPlusPlus.USER_PARSE);
		List<Step> step = (List<Step>) parseObject.get(StemPlusPlus.STEP_PARSE);
		List<Comment> comments = (List<Comment>) parseObject
				.get(StemPlusPlus.COMMENT_PARSE);
		String description = parseObject
				.getString(StemPlusPlus.DESCRIPTION_PARSE);
		String category = parseObject.getString(StemPlusPlus.CATEGORY_PARSE);

		Project newProject = new Project();
		newProject.setName(name);
		newProject.setRank(rank);
		newProject.setUser(user);
		newProject.setSteps(step);
		newProject.setComments(comments);
		newProject.setDescription(description);
		newProject.setDate(parseObject.getCreatedAt());
		newProject.setCategory(category);
		return newProject;
	}

	/**
	 * Retrieve the Project from the ParseObject
	 * 
	 * @param parseObject
	 *            A ParseObject
	 * @return A Project type
	 */
	public static Project getBasicProjectFromParseObject(ParseObject parseObject) {
		String name = parseObject.getString(StemPlusPlus.NAME_PARSE);
		JSONArray step = parseObject.getJSONArray(StemPlusPlus.STEP_PARSE);
		String id = parseObject.getObjectId();
		List<Step> steps = new ArrayList<Step>();
		for (int i = 0; i < step.length(); i++) {
			Step newStep = null;
			try {
				newStep = new Step(step.getJSONObject(i).getString(
						Step.DESCRIPTION_STEP), step.getJSONObject(i)
						.getString(Step.IMAGE_PATH_STEP));
				newStep.setName(step.getJSONObject(i).getString(
						Step.NAME_STEP));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			steps.add(newStep);
		}

		JSONArray comments = parseObject
				.getJSONArray(StemPlusPlus.COMMENT_PARSE);
		List<Comment> comment = new ArrayList<Comment>();
		if (comments != null && comments.length() > 0) {
			for (int i = 0; i < comments.length(); i++) {
				Comment newComment = null;
				try {
					newComment = new Comment(comments.getJSONObject(i)
							.getString(Comment.DETAIL_PARSE));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				comment.add(newComment);
			}
		}

		String description = parseObject
				.getString(StemPlusPlus.DESCRIPTION_PARSE);
		String category = parseObject.getString(StemPlusPlus.CATEGORY_PARSE);
		String profilePic = parseObject.getString(StemPlusPlus.PROFILE_PARSE);
		int ranking = parseObject.getInt(StemPlusPlus.PROJECT_RANKING_PARSE);
		Project newProject = new Project();
		newProject.setProjectRanking(ranking);
		newProject.setName(name);
		newProject.setSteps(steps);
		newProject.setDescription(description);
		newProject.setProfileImagePath(profilePic);
		newProject.setDate(parseObject.getCreatedAt());
		newProject.setCategory(category);
		newProject.setComments(comment);
		newProject.setId(id);
		return newProject;
	}

	public static JSONArray makeCommentArray(List<Comment> comments) {
		if (comments != null) {
			JSONArray commentArray = new JSONArray();
			for (int i = 0; i < comments.size(); i++) {
				JSONObject commentObject = new JSONObject();
				try {
					commentObject.put(Comment.TITLE_PARSE, comments.get(i)
							.getUser().getUsername());
					commentObject.put(Comment.DETAIL_PARSE, comments.get(i)
							.getComment());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				commentArray.put(commentObject);

			}
			return commentArray;
		}
		return null;

	}

	/**
	 * Send the ParseObject to the server
	 * 
	 * @param object
	 *            A ParseObject
	 */
	public static void sendParseObjectToServer(ParseObject object) {
		object.saveInBackground();
	}

	/**
	 * Constrain the project found by the ParseQuery based on the ranking
	 * 
	 * @param rank
	 *            A ranking
	 * @return A ParseQuery to query in the parse server
	 */
	public static ParseQuery getProjectWithRanking(Ranking rank) {
		ParseQuery query = new ParseQuery(StemPlusPlus.PROJECT_PARSE);
		query.whereEqualTo(StemPlusPlus.RANKING_PARSE, rank.getRank());
		return query;
	}

	/**
	 * Constrain the project found by the ParseQuery based on the user
	 * 
	 * @param username
	 *            the user's username
	 * @return A ParseQuery to query in the parse server
	 */
	public static ParseQuery getProjectWithUser(String username) {
		ParseQuery query = new ParseQuery(StemPlusPlus.PROJECT_PARSE);
		query.whereEqualTo(StemPlusPlus.USER_PARSE, username);
		return query;
	}

	/**
	 * Constrain the project found by the ParseQuery based on the category
	 * 
	 * @param category
	 *            A category
	 * @return A ParseQuery to query in the parse server
	 */
	public ParseQuery getProjectWithDescription(String category) {
		ParseQuery query = new ParseQuery(StemPlusPlus.PROJECT_PARSE);
		query.whereEqualTo(StemPlusPlus.DESCRIPTION_PARSE, category);
		return query;
	}

	/**
	 * No constraint on the project
	 * 
	 * @return A ParseQuery to query in the parse server
	 */
	public static ParseQuery getAllProjects() {
		ParseQuery query = new ParseQuery(StemPlusPlus.PROJECT_PARSE);
		return query;
	}

	/**
	 * Get a list of all the projects associated with a ranking
	 * 
	 * @param rank
	 *            A ranking
	 * @return An ArrayList of Project objects associated with rank, or null if
	 *         nothing is found
	 * @throws ParseException
	 */
	public ArrayList<Project> getListOfProjectsWithRanking(Ranking rank)
			throws ParseException {
		ParseQuery query = getProjectWithRanking(rank);
		ArrayList<Project> projects = new ArrayList<Project>();
		int total = 0;
		try {
			total = query.count();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (total > 0) {
			for (ParseObject object : query.find()) {
				projects.add(getProjectFromParseObject(object));
			}
		}
		return projects;
	}

	/**
	 * Get a list of all the projects associated with a user
	 * 
	 * @param String
	 *            username the user's username
	 * @return An ArrayList of Project objects associated with user, or null if
	 *         nothing is found
	 * @throws ParseException
	 */
	public static ArrayList<Project> getListOfProjectsOfUser(String username)
			throws ParseException {
		ParseQuery query = getProjectWithUser(username);
		ArrayList<Project> projects = new ArrayList<Project>();
		int total = 0;
		try {
			total = query.count();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (total > 0) {
			for (ParseObject object : query.find()) {
				projects.add(getProjectFromParseObject(object));
			}
		}
		return projects;
	}

	/**
	 * Get a list of all the projects associated with a category
	 * 
	 * @param category
	 *            A category
	 * @return An ArrayList of Project objects associated with a category, or
	 *         null if nothing is found
	 * @throws ParseException
	 */
	public ArrayList<Project> getListOfProjectsWithDescription(String category)
			throws ParseException {
		ParseQuery query = getProjectWithDescription(category);
		ArrayList<Project> projects = new ArrayList<Project>();
		int total = 0;
		try {
			total = query.count();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (total > 0) {
			for (ParseObject object : query.find()) {
				projects.add(getProjectFromParseObject(object));
			}
		}
		return projects;
	}

	/**
	 * Get a list of all the projects in the parse server
	 * 
	 * @return An ArrayList of all Project objects, or null if nothing is found
	 * @throws ParseException
	 */
	public static ArrayList<Project> getListOfAllProjects()
			throws ParseException {
		ParseQuery query = getAllProjects();
		ArrayList<Project> projects = new ArrayList<Project>();
		int total = 0;
		try {
			total = query.count();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (RuntimeException e){
			e.printStackTrace();
			Log.d("total", "" + total);
		}
		if (total > 0) {
			for (ParseObject object : query.find()) {
				projects.add(getBasicProjectFromParseObject(object));
			}
		}
		return projects;
	}
}
