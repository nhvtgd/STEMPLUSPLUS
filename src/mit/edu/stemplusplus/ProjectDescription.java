package mit.edu.stemplusplus;

import java.util.ArrayList;
import java.util.List;

import mit.edu.stemplusplus.helper.Project;
import mit.edu.stemplusplus.helper.Step;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProjectDescription extends Activity {
	Project selectedProject;
	String projectDescription;
	ArrayList<Step> projectSteps;
	CustomizedStepAdapter stepAdapter;
	ListView stepListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_description);

		/** get the project selected earlier **/
		Intent getProjectIntent = getIntent();
		selectedProject = (Project) getProjectIntent
				.getSerializableExtra(StemPlusPlus.PROJECT_INTENT);
		setUpView();
	}

	private void setUpView() {
		TextView projectDescription = (TextView) findViewById(R.id.description_project_description);
		projectDescription.setText(selectedProject.getDescription());

		Button upVote = (Button) findViewById(R.id.upvote_project_description);
		upVote.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				upVote(selectedProject);

			}
		});

		Button downVote = (Button) findViewById(R.id.downvote_project_description);
		downVote.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				downVote(selectedProject);

			}
		});

		List<Step> step = selectedProject.getSteps();
		stepAdapter = new CustomizedStepAdapter(this, step);
		stepListView = (ListView) findViewById(R.id.step_listView_project_description);
		stepListView.setAdapter(stepAdapter);
	}

	private void upVote(Project project) {
		project.incrementRank();
	}

	private void downVote(Project project) {
		project.decrementRank();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_project_description, menu);
		return true;
	}


}
