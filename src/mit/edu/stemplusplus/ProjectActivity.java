package mit.edu.stemplusplus;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import mit.edu.stemplusplus.helper.*;

public class ProjectActivity extends Activity {
    private User user;
    private String name;
    private String description;
    private List<Step> steps;
    private ArrayList<Comment> comments;
    private ArrayList<History> historyList;
    private ListView stepListView;
    private CustomizedStepAdapterforPosting stepAdapter;
    private EditText text;
    private Button mediaButton;
    private Step currentStep;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_project);
        Log.d("load layout", "Ok");

        currentStep = new Step();
        steps = new ArrayList<Step>();
        text = (EditText)findViewById(R.id.project_descriptions_post_project);
        Log.d("load text","txt ok");

        Button profile = (Button) findViewById(R.id.profile_image_post_project);
        profile.setOnClickListener(onclick);
       
        
        Button addSteps = (Button) findViewById(R.id.add_step_post_project);
        addSteps.setOnClickListener(onclick);
       
        Button commitButton = (Button) findViewById(R.id.commit_post_project);
        commitButton.setOnClickListener(onclick);
        
        Button previewButton = (Button) findViewById(R.id.preview_post_project);
        previewButton.setOnClickListener(onclick);
        
        mediaButton = (Button)findViewById(R.id.image_button_post_project);
        mediaButton.setOnClickListener(onclick);
        
        stepListView = (ListView) findViewById(R.id.stepsList_post_project);
        stepAdapter = new CustomizedStepAdapterforPosting(ProjectActivity.this, steps);
        stepListView.setAdapter(stepAdapter);
        
       
    }
    private OnClickListener onclick = new OnClickListener(){
   
    @SuppressWarnings("deprecation")
    public void onClick(View v) {
        
        switch (v.getId()) {  
        case R.id.profile_image_post_project:
            Intent intentprofile = new Intent(v.getContext(), CustomizedGallery.class);
            startActivity(intentprofile);
            ArrayList<String> imagePathProfile = intentprofile.getStringArrayListExtra("gallery");
            View view = findViewById(android.R.id.content);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // Find dimension of the picture
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(imagePathProfile.get(0), options);
            ImageView image = new ImageView(view.getContext());
            image.setImageBitmap(bmp);
            image.setScaleType(ScaleType.FIT_CENTER);
            break;
            
        case R.id.image_button_post_project:
            Intent intent = new Intent(v.getContext(), CustomizedGallery.class);
            startActivity(intent);
            // startActivityForResult(intent,1);
            Log.d("debug","start customized");
            ArrayList<String> imagePathStep = intent.getStringArrayListExtra("gallery");
//            currentStep.setmedia(bmp);
            View viewStep = findViewById(android.R.id.content);
            BitmapFactory.Options optionsStep = new BitmapFactory.Options();
            // Find dimension of the picture
            optionsStep.inJustDecodeBounds = true;
            Bitmap bmpStep = BitmapFactory.decodeFile(imagePathStep.get(0), optionsStep);
            ImageView imageStep = new ImageView(viewStep.getContext());
            imageStep.setImageBitmap(bmpStep);
            imageStep.setScaleType(ScaleType.FIT_CENTER);
            break;
          

        case R.id.add_step_post_project:
            currentStep.setDescription(text.getText().toString());
            steps.add(currentStep);
            stepAdapter.notifyDataSetChanged();
            currentStep = new Step();
            
            break;
            
        case R.id.commit_post_project:
            EditText nameText = (EditText) findViewById(R.id.project_name_post_project);
            EditText desText = (EditText) findViewById(R.id.project_description_post_project);
            name = nameText.getText().toString();
            description = desText.getText().toString();
            /// better to use service here
            Intent store = new Intent(ProjectActivity.this, StoreNewProject.class);
            // when all objects are implementing Parcelable, it would be easy to send them to the next intent
           // intent.putExtra("project", transfer);
            startActivity(store);
            //there needs to be a way of checking whether the commit is successful or not
             boolean success = true;
            Intent messageIntent = new Intent(ProjectActivity.this, commitMessageActivity.class);
            
            if(success){
                messageIntent.putExtra("commit Message", "Your project commitment was successful!");
               
            } else{
                messageIntent.putExtra("commit Message", "Your project commitment failed!");
            }
            startActivity(messageIntent);
            break;
            
        case R.id.preview_post_project:
           setContentView(R.layout.activity_project_preview);
            TextView pname = (TextView)findViewById(R.id.pproject_name);
            pname.setText(name);
            TextView pdescription = (TextView)findViewById(R.id.pproject_description);
            pdescription.setText(description);
            LinearLayout stepsLayout = (LinearLayout)findViewById(R.id.pstepsList);
            Iterator<Step> iter = steps.iterator();
            int numStep = 1;
            
            while(iter.hasNext()){
                Step temps = iter.next(); 
                TextView sprompt = new TextView(ProjectActivity.this);
                sprompt.setTextColor(0xff000000);
                sprompt.setBackgroundColor(0xff888888);
                sprompt.setTextSize(14);
                sprompt.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                sprompt.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                sprompt.setGravity(0x03);
                sprompt.setText("Step" + numStep);
                
                Log.d("Debug", "in the loop");
                
                stepsLayout.addView(sprompt,new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
                
                TextView stepIntruction = new TextView(ProjectActivity.this);
                stepIntruction.setText(temps.getDescription());
                stepsLayout.addView(stepIntruction,new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                
                ImageView stepMedia = new ImageView(ProjectActivity.this);
                stepMedia.setImageBitmap(temps.getMedia());
                stepsLayout.addView(stepMedia,new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
              }
            break;     
            }
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      try {
      if (requestCode == 1) {
          if(resultCode == RESULT_OK){            
          }
          if (resultCode == RESULT_CANCELED) {  
              // what do we do if result is canceled?
          }
          
       }
      }catch (Exception e) {
          Log.e("Error Selecting Picture", e.getMessage());
      }
    }
    
    public void changeBack(){
        
    }
    
      
    
}



