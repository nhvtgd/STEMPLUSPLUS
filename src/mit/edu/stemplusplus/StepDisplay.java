package mit.edu.stemplusplus;


import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



public class StepDisplay extends StemPlusPlus {

	protected static final int ADD_STEP_CODE = 10;
	EditText stepName;
	EditText stepDetail;
	ImageView stepImage;
	String stepImagePath;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step_display);

		stepName = (EditText) findViewById(R.id.step_name_step_display);
		stepDetail = (EditText) findViewById(R.id.step_detail_step_display);
		stepImage = (ImageView) findViewById(R.id.step_image_step_display);
	
		stepImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), CustomizedGallery.class);
				startActivityForResult(i, ADD_STEP_CODE);
				
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ADD_STEP_CODE) {
			if (resultCode == RESULT_OK) {
				ArrayList<String> image = data
						.getStringArrayListExtra(GALLERY_INTENT);
				if (image.size() > 0 && new File(image.get(0)).exists()) {
					stepImage.setImageBitmap(BitmapFactory
							.decodeFile(image.get(0)));
					stepImagePath = image.get(0);
				}
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_done_step_display: {
			if (stepName.getText().toString().length() > 0) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra(STEP_NAME, stepName.getText().toString());
				returnIntent.putExtra(STEP_DETAIL, stepDetail.getText().toString());
				returnIntent.putExtra(STEP_IMAGE, stepImagePath);
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
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
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
