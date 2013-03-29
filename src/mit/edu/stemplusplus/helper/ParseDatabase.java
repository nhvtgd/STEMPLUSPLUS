package mit.edu.stemplusplus.helper;

import java.util.List;

import mit.edu.stemplusplus.StemPlusPlus;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseDatabase {
	public void initProject(Context context) {
		Parse.initialize(context, "iqIztkJYN0f0Y8iPYLjhpVYYFpV9zmnpBAoKTP1s",
				"4Z2u2qEfF4NtBq8PyIGjfewuhTU1iC7iEdxapoV5");
	}
	
	public ParseObject createParseObjectFromProject(Project project){
		ParseObject parseObject = new ParseObject(StemPlusPlus.PROJECT_PARSE);
		parseObject.put(StemPlusPlus.CATEGORY_PARSE, project.getCategory());
		parseObject.put(StemPlusPlus.COMMENT_PARSE, project.getComments());
		parseObject.put(StemPlusPlus.DESCRIPTION_PARSE, project.getDescription());
		parseObject.put(StemPlusPlus.USER_PARSE, project.getUser());
		parseObject.put(StemPlusPlus.RANKING_PARSE, project.getRank());
		parseObject.put(StemPlusPlus.STEP_PARSE, project.getSteps());
		return parseObject;		
	}
	
	public Project getProjectFromParse(ParseObject parseObject){
		String category = (String) parseObject.get(StemPlusPlus.PROJECT_PARSE);		
		List<Comment> comment =  (List<Comment>) parseObject.get(StemPlusPlus.COMMENT_PARSE);
		String description = (String) parseObject.get(StemPlusPlus.DESCRIPTION_PARSE);
		User user = (User) parseObject.get(StemPlusPlus.USER_PARSE);
		int rank = parseObject.getInt(StemPlusPlus.RANKING_PARSE);
		List<Step> step = (List<Step>) parseObject.get(StemPlusPlus.STEP_PARSE);
		Project newProject = new Project();
		newProject.setUser(user);
		newProject.setDate(parseObject.getCreatedAt());
		newProject.setDescription(description);
		newProject.setRank(rank);
		newProject.setComments(comment);
		newProject.setSteps(step);
		newProject.setCategory(category);
		return newProject;
		
		
	}
}
