package mit.edu.stemplusplus;

/**
 * This is the first screen that the user can choose from
 */

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class MenuScreen extends Activity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        TextView mainprompt=(TextView)findViewById(R.id.mainprompt);
        Typeface robotoThin=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        mainprompt.setTypeface(robotoThin);
        // End Add
        

        Button picSBtn = (Button) findViewById(R.id.camera_menu_screen);
        picSBtn.setTypeface(robotoThin); 
        Button vidBtn = (Button) findViewById(R.id.gallery_menu_screen);
        vidBtn.setTypeface(robotoThin); 
		
	}


}
