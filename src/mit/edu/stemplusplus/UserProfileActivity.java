package mit.edu.stemplusplus;

import mit.edu.stemplusplus.helper.NavigateActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserProfileActivity extends NavigateActionBar{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView user = (TextView) findViewById(R.id.user_profile_activity_username_user);
        TextView reputation_points = (TextView) findViewById(R.id.user_profile_activity_reputation_points);
        user.setText(StemPlusPlus.USER_PARSE);
        reputation_points.setText(StemPlusPlus.REPUTATION_POINTS_PARSE);
        LinearLayout ll = (LinearLayout) findViewById(R.id.user_profile_past_projects);
        /*for(String project : StemPlusPlus.PROJECT_PARSE){
            TextView tv = new TextView(this);
            tv.setText(project);
            ll.addView(tv);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_user_profile, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return navigate(item, this, true);
    }

}
