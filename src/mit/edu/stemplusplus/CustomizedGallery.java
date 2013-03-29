package mit.edu.stemplusplus;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This class created a customized gallery for the user to view all of the
 * images
 * 
 * @author trannguyen
 * 
 */
public class CustomizedGallery extends Activity {
	/** size of the array */
	private int count;
	/** Array of Bitmap to store image */
	private Bitmap[] thumbnails;
	/** selected picture array */
	private boolean[] thumbnailSelection;
	/** picture path */
	private String[] arrPath;
	/** ImageAdapter for the array Bitmap */
	private ImageAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customized_gallery);
		// create the SQL database for query
		final String[] columns = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media._ID;
		
		Cursor imageCursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy);
		int image_column_index = imageCursor
				.getColumnIndex(MediaStore.Images.Media._ID);
		this.count = imageCursor.getCount();
		// initialize the two arrays
		this.thumbnails = new Bitmap[this.count];
		this.arrPath = new String[this.count];
		this.thumbnailSelection = new boolean[this.count];
		for (int i = 0; i < this.count; i++) {
			imageCursor.moveToPosition(i);
			int id = imageCursor.getInt(image_column_index);
			int dataColumnIndex = imageCursor
					.getColumnIndex(MediaStore.Images.Media.DATA);
			thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
					getApplicationContext().getContentResolver(), id,
					MediaStore.Images.Thumbnails.MICRO_KIND, null);
			arrPath[i] = imageCursor.getString(dataColumnIndex);
		}

		GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid_customized_gallery);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);

		final Button doneButton = (Button) findViewById(R.id.done_button_customized_gallery);
		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final int len = thumbnailSelection.length;
				int cnt = 0;
				String selectImages = "";
				for (int i = 0; i < len; i++) {
					if (thumbnailSelection[i]) {
						cnt++;
						selectImages = selectImages + arrPath[i] + "|";
					}
				}
				if (cnt == 0) {
					Toast.makeText(getApplicationContext(),
							"Please select at least one image",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"You've selected Total " + cnt + " image(s).",
							Toast.LENGTH_LONG).show();
					Log.d("SelectedImages", selectImages);
					
					/***************************/

					/* send these pictures to new activity that has all of the projects 
//					Intent backToCam = new Intent(v.getContext(),
//							AllProjectDisplayActivity.class);
 * 	
 */
/*					String[] select = new String[cnt];
					int counter = 0;
					int match = 0;
					while (match != cnt || counter >= thumbnailSelection.length) {
						if (thumbnailSelection[counter]) {
							select[match] = arrPath[counter];
							match++;
						}
						counter++;
					}

					backToCam.putExtra("gallery", select);
					startActivity(backToCam);*/
				}

			}
		});
	}

	
	/**
	 * The adapter to handle to underlying array of pictures
	 * @author trannguyen
	 *
	 */
	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ImageAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.galleryitem, null);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.thumbImage_gallery_item);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.itemCheckBox_gallery_item);
				convertView.setTag(holder);
			}
			// picture is already there
			else {
				holder = (ViewHolder) convertView.getTag();

			}
			
			
			holder.checkbox.setId(position);
			holder.imageView.setId(position);
			holder.checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					int id = cb.getId();
					if (thumbnailSelection[id]) {
						cb.setChecked(false);
						thumbnailSelection[id] = false;
					} else {
						cb.setChecked(true);
						thumbnailSelection[id] = true;
					}

				}
			});
			// View image intent
			holder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int id = v.getId();
					holder.checkbox.performClick();

				}
			});
			// take the bitmap from thumbnails and set it to the view

			// new LoadImage().execute(position,holder);
			holder.imageView.setImageBitmap(thumbnails[position]);

			// return the view to the GridView..
			// at this point, the ImageView is only displaying the
			// default icon..

			holder.setId(position);

			return convertView;

		}

	}

	// private class LoadImage extends AsyncTask<Integer, Void, Bitmap>{
	//
	// @Override
	// protected Bitmap doInBackground(Integer... params) {
	// // TODO Auto-generated method stub
	// return thumbnails[params[0]];
	// }
	//
	// @Override
	// protected void onPostExecute(Bitmap result) {
	// holder.imageView.setImageBitMap(result);
	// }
	//
	// }

	/**
	 * The view holder to store the check box and imageView 
	 * @author trannguyen
	 *
	 */
	private class ViewHolder {
		private CheckBox checkbox;
		private ImageView imageView;
		private int id;

		public void setId(int id) {
			this.id = id;
		}
	}

}
