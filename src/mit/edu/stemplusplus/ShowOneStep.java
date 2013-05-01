package mit.edu.stemplusplus;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ShowOneStep extends StemPlusPlus {

	TextView stepName;
	ImageView stepImage;
	TextView stepDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_one_step);
		
		Intent i = getIntent();
		
		stepName = (TextView) findViewById(R.id.step_name_showOneStep);
		stepName.setText(i.getStringExtra(STEP_NAME));
		
		stepImage = (ImageView) findViewById(R.id.step_image_showOneStep);
		
		if (i.getStringExtra(STEP_IMAGE) != null && new File(i.getStringExtra(STEP_IMAGE)).exists()){
			stepImage.setImageBitmap(BitmapFactory.decodeFile(i.getStringExtra(STEP_IMAGE)));
		}
		
		stepDetail = (TextView) findViewById(R.id.step_detail_showOneStep);
		stepDetail.setText(i.getStringExtra(STEP_DETAIL));
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_done_step_display: {
			finish();
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
		inflater.inflate(R.menu.activity_show_one_step, menu);
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
