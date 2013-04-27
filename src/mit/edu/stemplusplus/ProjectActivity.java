package mit.edu.stemplusplus;

import java.util.ArrayList;
import java.util.List;

import mit.edu.stemplusplus.helper.Comment;
import mit.edu.stemplusplus.helper.History;
import mit.edu.stemplusplus.helper.ParseDatabase;
import mit.edu.stemplusplus.helper.Project;
import mit.edu.stemplusplus.helper.Step;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
    private CustomizedStepAdapterforPosting stepAdapter;
    private EditText text;
    private Step currentStep;
    private EditText nameText;
    private EditText desText;
    private EditText step1Text;
    private ImageView step1Image;
    private Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_project);
        ParseDatabase.initProject(this);
        Log.d("load layout", "Ok");

        currentStep = new Step();
        currentProject = new Project();
        steps = new ArrayList<Step>();
        text = (EditText) findViewById(R.id.project_descriptions_post_project);
        nameText = (EditText) findViewById(R.id.project_name_post_project);
        desText = (EditText) findViewById(R.id.project_description_post_project);
        step1Text = (EditText) findViewById(R.id.project_descriptions_post_project);
        step1Image = (ImageView) findViewById(R.id.image_button_post_project);
        Log.d("load text", "txt ok");

        ImageView profile = (ImageView) findViewById(R.id.profile_image_post_project);
        profile.setOnClickListener(onclick);

        Button addSteps = (Button) findViewById(R.id.add_step_post_project);
        addSteps.setOnClickListener(onclick);

        Button commitButton = (Button) findViewById(R.id.commit_post_project);
        commitButton.setOnClickListener(onclick);

        Button previewButton = (Button) findViewById(R.id.preview_post_project);
        previewButton.setOnClickListener(onclick);

        ImageView mediaButton = (ImageView) findViewById(R.id.image_button_post_project);
        mediaButton.setOnClickListener(onclick);

        stepListView = (ListView) findViewById(R.id.stepsList_post_project);
        stepAdapter = new CustomizedStepAdapterforPosting(ProjectActivity.this,
                steps);
        stepListView.setAdapter(stepAdapter);

    }

    private OnClickListener onclick = new OnClickListener() {

        @SuppressWarnings("deprecation")
        public void onClick(View v) {

            switch (v.getId()) {
            case R.id.profile_image_post_project:
                Intent intentprofile = new Intent(v.getContext(),
                        CustomizedGallery.class);

                startActivityForResult(intentprofile, 1000);
                clicked = v;
                break;

            case R.id.image_button_post_project:
                Intent intent = new Intent(v.getContext(),
                        CustomizedGallery.class);
                startActivityForResult(intent, 1000);
                clicked = v;
                break;

            case R.id.add_step_post_project:
                currentStep.setDescription(text.getText().toString());
                steps.add(currentStep);
                stepAdapter.notifyDataSetChanged();
                currentStep = new Step();
                clicked = v;
                break;

            case R.id.commit_post_project:
                name = nameText.getText().toString();
                currentProject.setName(name);

                description = desText.getText().toString();
                
                // when hitting commit button, if the user type in something in the project description, 
                // it should record the answer.
                currentStep.setDescription(text.getText().toString());
                steps.add(currentStep);
                stepAdapter.notifyDataSetChanged();
                
                currentProject.setDescription(description);
                currentProject.setSteps(steps);

                // Tran
                ParseObject parseObject = ParseDatabase
                        .createParseObjectFromBasicProject(currentProject);
                if (parseObject == null) {
                    Log.d("I'm doom", "zzzz");
                }
                currentProject.setId(parseObject.getObjectId());
                parseObject.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException arg0) {
                        Toast.makeText(act, "Succesfully", Toast.LENGTH_LONG)
                                .show();

                    }
                });

                // TODO
                // / better to use service here
                Intent store = new Intent(ProjectActivity.this,
                        AllProjectDisplayActivity.class);
                // when all objects are implementing Parcelable, it would be
                // easy to send them to the next intent
                // intent.putExtra("project", transfer);
                startActivity(store);
                // there needs to be a way of checking whether the commit is
                // successful or not
                /*
                 * boolean success = true; Intent messageIntent = new
                 * Intent(ProjectActivity.this, commitMessageActivity.class);
                 * 
                 * if(success){ messageIntent.putExtra("commit Message",
                 * "Your project commitment was successful!");
                 * 
                 * } else{ messageIntent.putExtra("commit Message",
                 * "Your project commitment failed!"); }
                 * startActivity(messageIntent);
                 */
                break;

            // case R.id.preview_post_project:
            // name = nameText.getText().toString();
            // description = desText.getText().toString();
            // setContentView(R.layout.activity_project_preview);
            // TextView pname = (TextView) findViewById(R.id.pproject_name);
            // pname.setText(name);
            // ImageView pimage = (ImageView) findViewById(R.id.pprofile_image);
            // if (currentProject.getProfilePic() == null) {
            // Log.d("image", "null");
            // }
            // pimage.setImageBitmap(currentProject.getProfilePic());
            // TextView pdescription = (TextView)
            // findViewById(R.id.pproject_description);
            // pdescription.setText(description);
            // if (step1Text.getText() != null) {
            // TextView step1Descrption = (TextView)
            // findViewById(R.id.pfirst_step);
            // step1Descrption.setText(step1Text.getText().toString());
            // ImageView stepImage = (ImageView)
            // findViewById(R.id.pimage_button);
            // stepImage.setImageBitmap(step1Image.getDrawingCache());
            // }
            // CustomizedStepAdapter previewStepAdapter = new
            // CustomizedStepAdapter(
            // ProjectActivity.this, steps);
            // ListView previewStepListView = (ListView)
            // findViewById(R.id.pstepsList);
            // previewStepListView.setAdapter(previewStepAdapter);
            // break;
            }
        }
    };

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1000) {
                if (resultCode == RESULT_OK) {
                    ArrayList<String> imagePath = data
                            .getStringArrayListExtra(GALLERY_INTENT);
                    // currentStep.setmedia(bmp);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // Find dimension of the picture
                    options.inJustDecodeBounds = true;
                    Bitmap bmp = BitmapFactory.decodeFile(imagePath.get(0));
                    if (bmp == null)
                        Log.d("doom", "bad");
                    ImageView image = (ImageView) findViewById(clicked.getId());
                    if (image == null)
                        Log.d("imageStep", "good");
                    switch (clicked.getId()) {
                    case R.id.profile_image_post_project:
                        image.setImageBitmap(bmp);
                        // Log.d("check2", "" + image.getId());
                        image.setBackground(null);

                        currentProject.setProfilePic(bmp);
                        currentProject.setProfileImagePath(imagePath.get(0));
                        break;
                    case R.id.image_button_post_project:
                        image.setImageBitmap(bmp);
                        // Log.d("check2", "" + image.getId());
                        image.setBackground(null);
                        currentStep.setMediaPath(imagePath.get(0));
                        break;
                    default:
                        Log.d("check1", "" + clicked.getTag());
                        image = (ImageView) stepListView
                                .findViewWithTag(clicked.getTag());
                        if (image == null) {
                            Log.d("findView", "error");
                        }
                        image.setImageBitmap(bmp);
                        image.setBackground(null);
                        currentStep.setMediaPath(imagePath.get(0));
                        break;
                    }

                }
                if (resultCode == RESULT_CANCELED) {
                    // what do we do if result is canceled?
                }
            }
        } catch (Exception e) {
            Log.e("Error Selecting Picture", e.getMessage());
        }
    }

    public void changeBack() {

    }

}

class CustomizedStepAdapterforPosting extends BaseAdapter {
    private ProjectActivity activity;
    /** The collections of sellable objects */
    private List<Step> data;
    /** To inflate the view from an xml file */
    private LayoutInflater inflater = null;
    private ViewHolder holder;

    public CustomizedStepAdapterforPosting(ProjectActivity a, List<Step> steps) {
        activity = a;
        data = steps;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        holder = new ViewHolder();
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

    public ViewHolder getHolder() {
        return holder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int tag = position;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.hidden, null);
            Log.d("get TextView", "ok");
            holder.stepImage = (ImageView) convertView
                    .findViewById(R.id.image_button_hidden);
            holder.stepDescription = (EditText) convertView
                    .findViewById(R.id.project_description_hidden);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Step step = data.get(position);
        Log.d("get project", "ok");
        holder.stepDescription.setHint("step " + (position + 2)
                + " description");
        Log.d("set Text", "ok");
        Log.d("set Image", "ok");
        holder.stepImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStep = new Intent(v.getContext(),
                        CustomizedGallery.class);
                Log.d("position", "" + tag);
                ProjectActivity.clicked.setTag(tag);
                ProjectActivity.clicked = v;
                Log.d("check3", "" + ProjectActivity.clicked.getTag());
                activity.startActivityForResult(intentStep, 1000);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        private EditText stepDescription;
        private ImageView stepImage;
        private int id;

        public void setId(int id) {
            this.id = id;
        }
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}
