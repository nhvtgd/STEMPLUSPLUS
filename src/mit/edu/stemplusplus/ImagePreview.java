package mit.edu.stemplusplus;

import mit.edu.stemplusplus.helper.MyHorizontalLayout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
/**
 * 
 * @author trannguyen
 *
 */
public class ImagePreview extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_preview);
		
		Intent i = getIntent();
		String path = i.getStringExtra("image_path");
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels-100;
		int width = displaymetrics.widthPixels-100;
		
		ImageView image = (ImageView) findViewById(R.id.image_imagePreview);
		Bitmap bitmap = MyHorizontalLayout.resizeBitMapImage(path, width, height);
		image.setImageBitmap(bitmap);
		
	}


}
