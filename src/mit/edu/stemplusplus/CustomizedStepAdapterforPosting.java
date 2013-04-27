package mit.edu.stemplusplus;

import java.util.List;

import mit.edu.stemplusplus.helper.Step;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

public class CustomizedStepAdapterforPosting extends BaseAdapter {
    private ProjectActivity activity;
    /** The collections of sellable objects */
    private List<Step> data;
    /** To inflate the view from an xml file */
    private LayoutInflater inflater = null;

    public CustomizedStepAdapterforPosting(ProjectActivity a, List<Step> steps) {
        activity = a;
        data = steps;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.hidden, null);
            Log.d("get TextView", "ok");
            holder.stepImage = (ImageView) convertView
                    .findViewById(R.id.image_button_hidden);
            holder.stepDescription = (EditText) convertView
                    .findViewById(R.id.project_description_hidden);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        Step step = data.get(position);
        Log.d("get project", "ok");
        holder.stepDescription.setHint("step " + (position + 2)
                + " description");
        Log.d("set Text", "ok");
        Log.d("set Image", "ok");
        holder.stepImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStep = new Intent(v.getContext(),
                        CustomizedGallery.class);
                activity.startActivityForResult(intentStep, 1000);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        private EditText stepDescription;
        private ImageView stepImage;
        private int id;

        public void setId(int id) {
            this.id = id;
        }
    }

}