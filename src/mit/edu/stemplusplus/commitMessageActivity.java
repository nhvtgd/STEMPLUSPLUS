package mit.edu.stemplusplus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class commitMessageActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commit_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra("commit Message");
          // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(50);
        textView.setText(message);
        // Set the text view as the activity layout
        setContentView(textView);
        Button backButton = new Button(this);
        backButton.setText("ok");
        backButton.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            finish();
          }
        });
    }
    
    

}
