package mit.edu.stemplusplus;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class StepDisplay extends StemPlusPlus {

	EditText stepName;
	EditText stepDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step_display);

		stepName = (EditText) findViewById(R.id.step_name_step_display);
		stepDetail = (EditText) findViewById(R.id.step_detail_step_display);

	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_done_step_display: {
			if (stepName.getText().toString().length() > 0) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra(STEP_NAME, stepName.getText().toString());
				setResult(RESULT_OK, returnIntent);
				finish();
			} else {
				Toast.makeText(this, "Please Enter Step Name",
						Toast.LENGTH_LONG).show();
			}
			return true;
		}
		default:
			finish();
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_step_display, menu);
		menu.findItem(R.id.menu_done_step_display).setIcon(
				resizeImage(R.drawable.check_image, 108, 108));
		return true;
	}

	private Drawable resizeImage(int resId, int w, int h) {
		// load the origial Bitmap
		Bitmap BitmapOrg = BitmapFactory.decodeResource(getResources(), resId);
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;
		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		return new BitmapDrawable(resizedBitmap);
	}

}
