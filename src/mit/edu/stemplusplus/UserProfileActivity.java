package mit.edu.stemplusplus;

import mit.edu.stemplusplus.helper.NavigateActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.ArrayList;
import mit.edu.stemplusplus.helper.Project;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UserProfileActivity extends NavigateActionBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView user = (TextView) findViewById(R.id.user_profile_activity_username_user);
        TextView reputation_points = (TextView) findViewById(R.id.user_profile_activity_reputation_points);
        user.setText(StemPlusPlus.USER_PARSE);
        reputation_points.setText(StemPlusPlus.REPUTATION_POINTS_PARSE);
        ListView lv = (ListView) findViewById(R.id.user_profile_past_projects_list_view);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getProject());
        lv.setAdapter(aa);
        OnItemClickListener clickHandler = new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                // TODO
                // call the appropriate class
            }
        };
        lv.setOnItemClickListener(clickHandler);
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
    
    private ArrayList<Project> getProjectByUser(){
        ArrayList<Project> projects = new ArrayList<Project>();
        //TODO
        //get projects from parse server
        return projects;
    }
    private String[] getProject(){
        ArrayList<Project> p = getProjectByUser();
        String[] strings = new String[p.size()];
        for(int i=0; i<p.size(); i++){
            strings[i] = p.toString();
        }
        return strings;
    }

}
