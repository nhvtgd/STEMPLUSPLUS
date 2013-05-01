package mit.edu.stemplusplus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mit.edu.stemplusplus.helper.Comment;
import mit.edu.stemplusplus.helper.History;
import mit.edu.stemplusplus.helper.ParseDatabase;
import mit.edu.stemplusplus.helper.Project;
import mit.edu.stemplusplus.helper.Step;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class ProjectActivity extends StemPlusPlus {
	private Project currentProject;
	static View clicked;
	private String name;
	private Bitmap profilePic;
	private String description;
	private List<Step> steps;
	private ArrayList<Comment> comments;
	private ArrayList<History> historyList;
	private ListView stepListView;

	private Activity act = this;
	EditText projectName;
	EditText projectDescription;
	ImageView profilePicture;
	LinearLayout stepLayout;
	Button addStepBttn;
	Button commitProject;

	private static final int ADD_STEP_CODE = 10;
	private static final int ADD_PROFILE_CODE = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_project);
		ParseDatabase.initProject(this);
		Log.d("load layout", "Ok");

		currentProject = new Project();
		projectName = (EditText) findViewById(R.id.project_name_post_project);
		projectDescription = (EditText) findViewById(R.id.project_description_post_project);
		profilePicture = (ImageView) findViewById(R.id.profile_image_post_project);

		profilePicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), CustomizedGallery.class);
				startActivityForResult(i, ADD_PROFILE_CODE);

			}
		});

		steps = new ArrayList<Step>();
		addStepBttn = (Button) findViewById(R.id.add_step_post_project);
		addStepBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), StepDisplay.class);
				startActivityForResult(i, ADD_STEP_CODE);

			}
		});

		commitProject = (Button) findViewById(R.id.commit_post_project);
		commitProject.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (projectName.getText().length() <= 0) {
					Toast.makeText(v.getContext(),
							"Please Provide name for your project",
							Toast.LENGTH_LONG).show();
				}
				if (projectDescription.getText().length() <= 0) {
					Toast.makeText(v.getContext(),
							"Please Provide Some Description for your project",
							Toast.LENGTH_LONG).show();
				}

				if (steps.size() <= 0) {
					Toast.makeText(
							v.getContext(),
							"Please Provide at least one step for your project",
							Toast.LENGTH_LONG).show();
				}

				if (projectName.getText().length() > 0
						&& projectDescription.getText().length() > 0
						&& steps.size() > 0) {
					currentProject.setName(projectName.getText().toString());
					currentProject.setDescription(projectDescription.getText()
							.toString());
					currentProject.setSteps(steps);
					ParseObject parseObject = ParseDatabase
							.createParseObjectFromBasicProject(currentProject);
					if (parseObject == null) {
						Log.d("I'm doom", "zzzz");
					}
					currentProject.setId(parseObject.getObjectId());
					parseObject.saveInBackground(new SaveCallback() {

						@Override
						public void done(ParseException arg0) {
							Toast.makeText(act, "Succesfully",
									Toast.LENGTH_LONG).show();
							Intent i = new Intent(ProjectActivity.this, AllProjectDisplayActivity.class);
							startActivity(i);
							finish();

						}
					});

				}

			}
		});

	}

	private void addStepDynamically(Step step) {
		LinearLayout ll = (LinearLayout) findViewById(R.id.linear_layout_add_step_post_project);
		View v = getLayoutInflater().inflate(R.layout.step_display, null);
		TextView editText = (TextView) v
				.findViewById(R.id.step_name_project_description);
		editText.setText(step.getName());
		editText.setEnabled(false);

		ImageView stepPicture = (ImageView) v
				.findViewById(R.id.step_image_project_description);
		if (step.getMediaPath() != null
				&& new File(step.getMediaPath()).exists()) {
			stepPicture.setImageBitmap(BitmapFactory.decodeFile(step
					.getMediaPath()));

		}
		ll.addView(v);
	}

	/*
	 * currentStep = new Step(); currentProject = new Project(); steps = new
	 * ArrayList<Step>(); text = (EditText)
	 * findViewById(R.id.project_descriptions_post_project); nameText =
	 * (EditText) findViewById(R.id.project_name_post_project); desText =
	 * (EditText) findViewById(R.id.project_description_post_project); step1Text
	 * = (EditText) findViewById(R.id.project_descriptions_post_project);
	 * step1Image = (ImageView) findViewById(R.id.image_button_post_project);
	 * Log.d("load text", "txt ok");
	 * 
	 * ImageView profile = (ImageView)
	 * findViewById(R.id.profile_image_post_project);
	 * profile.setOnClickListener(onclick);
	 * 
	 * Button addSteps = (Button) findViewById(R.id.add_step_post_project);
	 * addSteps.setOnClickListener(onclick);
	 * 
	 * Button commitButton = (Button) findViewById(R.id.commit_post_project);
	 * commitButton.setOnClickListener(onclick);
	 */

	/*
	 * Button previewButton = (Button) findViewById(R.id.preview_post_project);
	 * previewButton.setOnClickListener(onclick);
	 * 
	 * ImageView mediaButton = (ImageView)
	 * findViewById(R.id.image_button_post_project);
	 * mediaButton.setOnClickListener(onclick);
	 * 
	 * stepListView = (ListView) findViewById(R.id.stepsList_post_project);
	 * stepAdapter = new CustomizedStepAdapterforPosting(ProjectActivity.this,
	 * steps); stepListView.setAdapter(stepAdapter);
	 */

	/*
	 * private OnClickListener onclick = new OnClickListener() {
	 * 
	 * @SuppressWarnings("deprecation") public void onClick(View v) {
	 * 
	 * switch (v.getId()) { case R.id.profile_image_post_project: Intent
	 * intentprofile = new Intent(v.getContext(), CustomizedGallery.class);
	 * 
	 * startActivityForResult(intentprofile, 1000); clicked = v; break;
	 * 
	 * case R.id.image_button_post_project: Intent intent = new
	 * Intent(v.getContext(), CustomizedGallery.class);
	 * startActivityForResult(intent, 1000); clicked = v; break;
	 * 
	 * case R.id.add_step_post_project: Intent i = new Intent(act,
	 * StepDisplay.class); startActivityForResult(i, ADD_STEP_CODE);
	 * 
	 * break;
	 * 
	 * case R.id.commit_post_project: name = nameText.getText().toString();
	 * currentProject.setName(name);
	 * 
	 * description = desText.getText().toString();
	 * 
	 * // when hitting commit button, if the user type in something in // the
	 * project description, // it should record the answer.
	 * currentStep.setDescription(text.getText().toString());
	 * steps.add(currentStep); stepAdapter.notifyDataSetChanged();
	 * 
	 * currentProject.setDescription(description);
	 * currentProject.setSteps(steps);
	 * 
	 * // Tran ParseObject parseObject = ParseDatabase
	 * .createParseObjectFromBasicProject(currentProject); if (parseObject ==
	 * null) { Log.d("I'm doom", "zzzz"); }
	 * currentProject.setId(parseObject.getObjectId());
	 * parseObject.saveInBackground(new SaveCallback() {
	 * 
	 * @Override public void done(ParseException arg0) { Toast.makeText(act,
	 * "Succesfully", Toast.LENGTH_LONG) .show();
	 * 
	 * } });
	 * 
	 * // TODO // / better to use service here Intent store = new
	 * Intent(ProjectActivity.this, AllProjectDisplayActivity.class); // when
	 * all objects are implementing Parcelable, it would be // easy to send them
	 * to the next intent // intent.putExtra("project", transfer);
	 * startActivity(store); // there needs to be a way of checking whether the
	 * commit is // successful or not
	 * 
	 * boolean success = true; Intent messageIntent = new
	 * Intent(ProjectActivity.this, commitMessageActivity.class);
	 * 
	 * if(success){ messageIntent.putExtra("commit Message",
	 * "Your project commitment was successful!");
	 * 
	 * } else{ messageIntent.putExtra("commit Message",
	 * "Your project commitment failed!"); } startActivity(messageIntent);
	 * 
	 * break;
	 * 
	 * // case R.id.preview_post_project: // name =
	 * nameText.getText().toString(); // description =
	 * desText.getText().toString(); //
	 * setContentView(R.layout.activity_project_preview); // TextView pname =
	 * (TextView) findViewById(R.id.pproject_name); // pname.setText(name); //
	 * ImageView pimage = (ImageView) findViewById(R.id.pprofile_image); // if
	 * (currentProject.getProfilePic() == null) { // Log.d("image", "null"); //
	 * } // pimage.setImageBitmap(currentProject.getProfilePic()); // TextView
	 * pdescription = (TextView) // findViewById(R.id.pproject_description); //
	 * pdescription.setText(description); // if (step1Text.getText() != null) {
	 * // TextView step1Descrption = (TextView) //
	 * findViewById(R.id.pfirst_step); //
	 * step1Descrption.setText(step1Text.getText().toString()); // ImageView
	 * stepImage = (ImageView) // findViewById(R.id.pimage_button); //
	 * stepImage.setImageBitmap(step1Image.getDrawingCache()); // } //
	 * CustomizedStepAdapter previewStepAdapter = new // CustomizedStepAdapter(
	 * // ProjectActivity.this, steps); // ListView previewStepListView =
	 * (ListView) // findViewById(R.id.pstepsList); //
	 * previewStepListView.setAdapter(previewStepAdapter); // break; } } };
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ADD_PROFILE_CODE) {
			if (resultCode == RESULT_OK) {
				ArrayList<String> image = data
						.getStringArrayListExtra(GALLERY_INTENT);
				if (image.size() > 0 && new File(image.get(0)).exists()) {
					profilePicture.setImageBitmap(BitmapFactory
							.decodeFile(image.get(0)));
					currentProject.setProfileImagePath(image.get(0));
				}
			}
		}

		else if (requestCode == ADD_STEP_CODE) {
			if (resultCode == RESULT_OK) {
				String stepName = data.getStringExtra(STEP_NAME);
				String stepDetail = data.getStringExtra(STEP_DETAIL);
				String stepImage = data.getStringExtra(STEP_IMAGE);
				Step step = new Step(stepDetail, stepImage);
				step.setName(stepName);
				steps.add(step);
				addStepDynamically(step);

			}
		}
	}
}

/*
 * @SuppressLint("NewApi")
 * 
 * @Override public void onActivityResult(int requestCode, int resultCode,
 * Intent data) { super.onActivityResult(requestCode, resultCode, data); try {
 * if (requestCode == 1000) { if (resultCode == RESULT_OK) { ArrayList<String>
 * imagePath = data .getStringArrayListExtra(GALLERY_INTENT); //
 * currentStep.setmedia(bmp); BitmapFactory.Options options = new
 * BitmapFactory.Options(); // Find dimension of the picture
 * options.inJustDecodeBounds = true; Bitmap bmp =
 * BitmapFactory.decodeFile(imagePath.get(0)); if (bmp == null) Log.d("doom",
 * "bad"); ImageView image = (ImageView) findViewById(clicked.getId()); if
 * (image == null) Log.d("imageStep", "good"); switch (clicked.getId()) { case
 * R.id.profile_image_post_project: image.setImageBitmap(bmp); //
 * Log.d("check2", "" + image.getId()); image.setBackground(null);
 * 
 * currentProject.setProfilePic(bmp);
 * currentProject.setProfileImagePath(imagePath.get(0)); break; case
 * R.id.image_button_post_project: image.setImageBitmap(bmp); // Log.d("check2",
 * "" + image.getId()); image.setBackground(null);
 * currentStep.setMediaPath(imagePath.get(0)); break; default: Log.d("check1",
 * "" + clicked.getTag()); image = (ImageView) stepListView
 * .findViewWithTag(clicked.getTag()); if (image == null) { Log.d("findView",
 * "error"); } image.setImageBitmap(bmp); image.setBackground(null);
 * currentStep.setMediaPath(imagePath.get(0)); break; }
 * 
 * } if (resultCode == RESULT_CANCELED) { // what do we do if result is
 * canceled? }
 * 
 * } else if (requestCode == ADD_STEP_CODE) { if (resultCode == RESULT_OK) {
 * currentStep.setDescription(text.getText().toString());
 * steps.add(currentStep); stepAdapter.notifyDataSetChanged(); currentStep = new
 * Step();
 * 
 * } } } catch (Exception e) { Log.e("Error Selecting Picture", e.getMessage());
 * } }
 * 
 * public void changeBack() {
 * 
 * }
 * 
 * }
 */

/*
 * class CustomizedStepAdapterforPosting extends BaseAdapter { private
 * ProjectActivity activity;
 *//** The collections of sellable objects */
/*
 * private List<Step> data;
 *//** To inflate the view from an xml file */
/*
 * private LayoutInflater inflater = null; private ViewHolder holder;
 * 
 * public CustomizedStepAdapterforPosting(ProjectActivity a, List<Step> steps) {
 * activity = a; data = steps; inflater = (LayoutInflater) activity
 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); holder = new
 * ViewHolder(); }
 * 
 * @Override public int getCount() { // TODO Auto-generated method stub return
 * data.size(); }
 * 
 * @Override public Object getItem(int position) { // TODO Auto-generated method
 * stub return data.get(position); }
 * 
 * public ViewHolder getHolder() { return holder; }
 * 
 * @Override public View getView(final int position, View convertView, ViewGroup
 * parent) { final int tag = position; if (convertView == null) { holder = new
 * ViewHolder(); convertView = inflater.inflate(R.layout.hidden, null);
 * Log.d("get TextView", "ok"); holder.stepImage = (ImageView) convertView
 * .findViewById(R.id.image_button_hidden); holder.stepDescription = (EditText)
 * convertView .findViewById(R.id.project_description_hidden);
 * convertView.setTag(holder); } else { holder = (ViewHolder)
 * convertView.getTag(); } Step step = data.get(position); Log.d("get project",
 * "ok"); holder.stepDescription.setHint("step " + (position + 2) +
 * " description"); Log.d("set Text", "ok"); Log.d("set Image", "ok"); if
 * (step.getName().toString().length() > 0) {
 * holder.stepDescription.setText(step.getName().toString());
 * 
 * }
 * 
 * if (step.getMediaPath().length() > 0 && new
 * File(step.getMediaPath()).exists()) {
 * holder.stepImage.setImageBitmap(BitmapFactory.decodeFile(step
 * .getMediaPath())); } else {
 * holder.stepImage.setImageBitmap(BitmapFactory.decodeResource(
 * activity.getResources(), R.drawable.test_image)); }
 * holder.stepImage.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { Intent intentStep = new
 * Intent(v.getContext(), CustomizedGallery.class);
 * 
 * Log.d("position", "" + tag); .clicked.setTag(tag); ProjectActivity.clicked =
 * v; Log.d("check3", "" + ProjectActivity.clicked.getTag());
 * 
 * intentStep.putExtra("position", position);
 * activity.startActivityForResult(intentStep, 1000); } });
 * 
 * return convertView; }
 * 
 * public class ViewHolder { private EditText stepDescription; private ImageView
 * stepImage; private int id;
 * 
 * public void setId(int id) { this.id = id; } }
 * 
 * @Override public long getItemId(int position) { // TODO Auto-generated method
 * stub return position; }
 * 
 * }
 */
