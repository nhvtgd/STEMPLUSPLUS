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

	public class CustomizedStepAdapter extends BaseAdapter {
		private Activity activity;
		/** The collections of sellable objects */
		private List<Step> data;
		/** To inflate the view from an xml file */
		private LayoutInflater inflater = null;

		public CustomizedStepAdapter(Activity a, List<Step> steps) {
			activity = a;
			data = steps;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.step_display, null);
				holder.stepIcon = (ImageButton) convertView
						.findViewById(R.id.step_icon_project_description);
				Log.d("get TextView", "ok");
				holder.stepImage = (ImageView) convertView
						.findViewById(R.id.step_image_project_description);

				holder.stepDescription = (TextView) convertView
						.findViewById(R.id.step_name_project_description);
				convertView.setTag(holder);
			}
			// picture is already there
			else {
				holder = (ViewHolder) convertView.getTag();

			}

			Step step = data.get(position);

			Log.d("get project", "ok");
			holder.stepDescription.setText(step.getDescription());
			Log.d("set Text", "ok");
			if (step.getMediaPath() != null) {
				Log.d("step Image", step.getMediaPath());
				holder.stepImage.setImageBitmap(BitmapFactory.decodeFile(step
						.getMediaPath()));
			} else if (step.getMedia() != null)
				holder.stepImage.setImageBitmap((Bitmap) step.getMedia());
			else {
				Log.d("Step Image", "Not good for demo");
			}
			Log.d("set Image", "ok");
			holder.stepImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}

			});

			return convertView;
		}
	}

	/**
	 * The view holder to store the check box and imageView
	 * 
	 * @author trannguyen
	 * 
	 */
	private class ViewHolder {
		private ImageButton stepIcon;
		private TextView stepDescription;
		private ImageView stepImage;
		private int id;

		public void setId(int id) {
			this.id = id;
		}
	}

}
