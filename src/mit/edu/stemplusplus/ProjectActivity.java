package mit.edu.stemplusplus;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import mit.edu.stemplusplus.helper.*;

public class ProjectActivity extends NavigateActionBar {
    private User user;
    private String name;
    private String description;
    private ArrayList<Step> steps;
    private ArrayList<Comment> comments;
    private ArrayList<History> historyList;
    private LinearLayout stepsLayout;
    private int stepsCount;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_project);
        Button addSteps = (Button) findViewById(R.id.add_step);
        addSteps.setOnClickListener((OnClickListener) this);
        
        Button commitButton = (Button) findViewById(R.id.commit);
        commitButton.setOnClickListener((OnClickListener) this);
        
        
        Button previewButton = (Button) findViewById(R.id.preview);
        previewButton.setOnClickListener((OnClickListener) this);
        
    }
    
   
    @SuppressWarnings("deprecation")
    public void onClick(View v) {
        switch (v.getId()) {  
        case R.id.add_step:
            //Check if the Layout already exists
            LinearLayout hiddenLayout = (LinearLayout)findViewById(R.id.hiddenLayout);
            if(hiddenLayout == null){
                //Inflate the Hidden Layout Information View
                RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.wrapper);
                View hiddenInfo = getLayoutInflater().inflate(R.layout.hidden, myLayout, false);
                myLayout.addView(hiddenInfo);
                stepsCount = 0;
            }
            Step temp = new Step();
            stepsCount ++;
            TextView stepsPrompt = new TextView(this);
            stepsPrompt.setText("Step" + stepsCount);
            stepsPrompt.setTextColor(0xff000000);
            stepsPrompt.setBackgroundColor(0xff888888);
            stepsPrompt.setTextSize(14);
            stepsPrompt.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            stepsPrompt.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            stepsPrompt.setGravity(0x03);
            hiddenLayout.addView(stepsPrompt);
            
            TextView contentPrompt = new TextView(this);
            contentPrompt.setText("Instructions:");
            contentPrompt.setTextColor(0xff000000);
            contentPrompt.setBackgroundColor(0xff888888);
            contentPrompt.setTextSize(12);
            contentPrompt.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            contentPrompt.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            contentPrompt.setGravity(0x03);
            hiddenLayout.addView(contentPrompt);
            
            EditText content = new EditText(this);
            content.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
            content.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            content.setInputType(0x00000001);
            content.setHint("Enter your instructions for this step here.");
            temp.setDescription(content.getText().toString());
            hiddenLayout.addView(content);
            
            Button mediaButton = new Button(this);
            mediaButton.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
            mediaButton.setHeight(30);
            mediaButton.setGravity(0x50);
            Drawable bgrnd = Drawable.createFromPath("drawable/custom_button_blue");
            mediaButton.setBackgroundDrawable(bgrnd);
            mediaButton.setText("Add Media");
            mediaButton.setTextColor(0xffffffff);
            mediaButton.setTextSize(10);
            hiddenLayout.addView(mediaButton);
            
            mediaButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context mContext = getApplicationContext();
//                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//                    View layout = inflater.inflate(R.layout.activity_customized_gallery,
//                            (ViewGroup) findViewById(R.id.gallery_wrapper));
//                    final PopupWindow pw = new PopupWindow(layout, 300, 470, true);
//                    pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
//                    Intent intent = new Intent(this, CustomizedGallery.class);
//                    pw.startActivity(intent);
//                    Button done = (Button) layout.findViewById(R.id.done_button_customized_gallery);
//                    done.setOnClickListener(new OnClickListener() {
//                        public void onClick(View v) {
//                            pw.dismiss();
//                        }});
                    
                    Intent intent = new Intent(ProjectActivity.this, CustomizedGallery.class);
                    startActivityForResult(intent,1);
                   
                }
            });
            
            Bundle extras = getIntent().getExtras();
            byte[] b = extras.getByteArray("picture");
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            View view = findViewById(android.R.id.content);
            ImageView image = new ImageView(view.getContext());
            image.setScaleType(ScaleType.FIT_CENTER);
            hiddenLayout.addView(image);
            temp.setmedia(bmp);
            steps.add(temp);
            break;
        
        case R.id.commit:
            EditText nameText = (EditText) findViewById(R.id.project_name);
            EditText desText = (EditText) findViewById(R.id.project_description);
            name = nameText.getText().toString();
            description = desText.getText().toString();
            /// better to use service here
            Intent intent = new Intent(this, StoreNewProject.class);
            // when all objects are implementing Parcelable, it would be easy to send them to the next intent
           // intent.putExtra("project", transfer);
            startActivity(intent);
            //there needs to be a way of checking whether the commit is successful or not
            
           
            boolean success = true;
            Intent messageIntent = new Intent(this, commitMessageActivity.class);
            
            if(success){
                messageIntent.putExtra("commit Message", "Your project commitment was successful!");
               
            } else{
                messageIntent.putExtra("commit Message", "Your project commitment failed!");
            }
            startActivity(messageIntent);
            break;
            
        case R.id.preview:
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
                TextView sprompt = new TextView(this);
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
                
                TextView stepIntruction = new TextView(this);
                stepIntruction.setText(temps.getDescription());
                stepsLayout.addView(stepIntruction,new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                
                ImageView stepMedia = new ImageView(this);
                stepMedia.setImageBitmap(temps.getMedia());
                stepsLayout.addView(stepMedia,new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
              }
            break;     
            }
        }
 
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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return navigate(item, this, true);
    }
  
}


