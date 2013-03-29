package mit.edu.stemplusplus.helper;

import java.util.ArrayList;

import mit.edu.stemplusplus.ImagePreview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * This class will create a horizontal layout that let the user dynamically
 * add view (primarily picture) to the layout horizontally
 * @author trannguyen
 *
 */
public class MyHorizontalLayout extends LinearLayout {
	/** The width and height of the thumnail image to add to the layout*/
	private final static int WIDTH = 96;
	private final static int HEIGHT = 96;
	
	/** The context that this layout is added in */
	Context myContext;
	
	/** The array contains the internal model */
	ArrayList<String> itemList;

	public MyHorizontalLayout(Context context) {
		super(context);
		myContext = context;
	}
	
	/** set the array to the item List */
	public void setArrayList(ArrayList<String> array){		
		itemList = array;
	}
	
	public MyHorizontalLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		myContext = context;
	}
	
	/**
	 * retrieve the image from the path and store that to the item
	 * @param path the path of the image to add in
	 */
	public void add(String path) {
		int newIdx = itemList.size();
		itemList.add(path);
		addView(getImageView(newIdx));
	}

	/**
	 * Retrieve the image from the path index
	 * @param i the index of the the image to be retrieve
	 * @return
	 */
	ImageView getImageView(final int i) {
		Bitmap bm = null;
		if (i < itemList.size()) {
			bm = resizeBitMapImage(itemList.get(i), WIDTH, HEIGHT);
			Log.d("add Image", "okay");
		}
		// dynanimically create the ImageView to add to the layout
		ImageView imageView = new ImageView(myContext);
		imageView.setLayoutParams(new LayoutParams(WIDTH, HEIGHT));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);
		Log.d("set Image", "okay");
		imageView.setOnClickListener(new OnClickListener() {
			// send the image to image preview 
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(myContext,ImagePreview.class);
				intent.putExtra("image_path", itemList.get(i));
				myContext.startActivity(intent);
				
			}
		});
		
		

		return imageView;
	}

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
			int reqHeight) {
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, options);

		return bm;
	}
	
	public static Bitmap resizeBitMapImage(String filePath, int targetWidth,
			int targetHeight) {
		Bitmap bitMapImage = null;
		// First, get the dimensions of the image
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		double sampleSize = 0;
		// Only scale if we need to
		// (16384 buffer for img processing)
		Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math
				.abs(options.outWidth - targetWidth);

		if (options.outHeight * options.outWidth * 2 >= 1638) {
			// Load, scaling to smallest power of 2 that'll get it <= desired
			// dimensions
			sampleSize = scaleByHeight ? options.outHeight / targetHeight
					: options.outWidth / targetWidth;
			sampleSize = (int) Math.pow(2d,
					Math.floor(Math.log(sampleSize) / Math.log(2d)));
		}

		// Do the actual decoding
		options.inJustDecodeBounds = false;
		options.inTempStorage = new byte[128];
		while (true) {
			try {
				options.inSampleSize = (int) sampleSize;
				bitMapImage = BitmapFactory.decodeFile(filePath, options);

				break;
			} catch (Exception ex) {
				try {
					sampleSize = sampleSize * 2;
				} catch (Exception ex1) {

				}
			}
		}

		return bitMapImage;
	}

	public int calculateInSampleSize(

	BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 2;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

}
