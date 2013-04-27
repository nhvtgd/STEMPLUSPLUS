package mit.edu.stemplusplus;

import java.util.ArrayList;

import mit.edu.stemplusplus.helper.ParseDatabase;
import mit.edu.stemplusplus.helper.Project;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class UserProfileActivity extends StemPlusPlus {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "iqIztkJYN0f0Y8iPYLjhpVYYFpV9zmnpBAoKTP1s", "4Z2u2qEfF4NtBq8PyIGjfewuhTU1iC7iEdxapoV5");
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        if(username==null)
            username = ParseUser.getCurrentUser().getUsername();

        TextView user = (TextView) findViewById(R.id.user_profile_activity_username_user);
        TextView reputation_points = (TextView) findViewById(R.id.user_profile_activity_reputation_points);
        user.setText(username);
        
        final ListView lv = (ListView) findViewById(R.id.user_profile_past_projects_list_view);
        Project[] projects = getProject();
        lv.setAdapter(new ArrayAdapter<Project>(this,android.R.layout.simple_list_item_1,projects));
        lv.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Project project = (Project) lv.getItemAtPosition(position);
                Log.d("projects",project.getName());
                Intent intent = new Intent(UserProfileActivity.this, ProjectDescription.class);
                intent.putExtra(StemPlusPlus.PROJECT_INTENT,project);
                startActivity(intent);
            }
        });
        reputation_points.setText(projects.length+"");
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.activity_user_profile, menu);
        return true;
    }
    

        
    private ArrayList<Project> getProjectByUser(){
        try {
            return ParseDatabase.getListOfProjectsOfUser(username);
        } catch (ParseException e) {
            return new ArrayList<Project>();
        }
    }
    private Project[] getProject(){
        return (Project[]) getProjectByUser().toArray();
    }

}
