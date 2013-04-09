package mit.edu.stemplusplus;


import com.parse.Parse;
import com.parse.ParseUser;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends StemPlusPlus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.initialize(this, "iqIztkJYN0f0Y8iPYLjhpVYYFpV9zmnpBAoKTP1s", "4Z2u2qEfF4NtBq8PyIGjfewuhTU1iC7iEdxapoV5");
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }
        
        findViewById(R.id.register_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptRegister();
                    }
                });
        findViewById(R.id.login_button).setOnClickListener(
                new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        signIn();
                    }
                });
    }
    public void signIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
    public void attemptRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
