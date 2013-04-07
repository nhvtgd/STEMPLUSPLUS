package mit.edu.stemplusplus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StemPlusPlus extends Activity {
	public static final String[] PROJECT_PARSE = {"project"};
	public static String USERNAME_PARSE = "username";
	public static String PASSWORD_PARSE = "password";
	public static int REPUTATION_POINTS_PARSE = 0;
	public static final String RANKING_PARSE = "rank";
	public static final String DESCRIPTION_PARSE = "description";
	public static final String COMMENT_PARSE = "comment";
	public static final String CATEGORY_PARSE = "category";
	public static final String USER_PARSE = "user";
	public static final String STEP_PARSE = "step";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stem_plus_plus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_stem_plus_plus, menu);
		return true;
	}

}
