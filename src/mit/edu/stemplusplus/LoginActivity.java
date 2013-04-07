package mit.edu.stemplusplus;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends StemPlusPlus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.register_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptRegister();
                    }
                });
        findViewById(R.id.sign_in_button).setOnClickListener(
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
