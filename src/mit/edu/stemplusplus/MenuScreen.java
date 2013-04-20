package mit.edu.stemplusplus;

/**
 * This is the first screen that the user can choose from
 */

import mit.edu.stemplusplus.helper.NavigateActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuScreen extends NavigateActionBar{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_screen);

        TextView mainprompt =(TextView) findViewById(R.id.mainprompt);
        Typeface robotoThin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        mainprompt.setTypeface(robotoThin);
        // End Add
        

        Button picSBtn = (Button) findViewById(R.id.camera_menu_screen);
        picSBtn.setTypeface(robotoThin); 
        picSBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), CustomCameraActivity.class);
				startActivity(i);
				
			}
		});
        Button vidBtn = (Button) findViewById(R.id.gallery_menu_screen);
        vidBtn.setTypeface(robotoThin); 
        vidBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), AllProjectDisplayActivity.class);
				startActivity(i);
				
			}
		});
        
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return navigate(item, this, true);
	}
}
