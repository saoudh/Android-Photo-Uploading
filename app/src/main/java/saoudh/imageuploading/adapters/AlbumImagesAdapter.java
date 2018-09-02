package saoudh.imageuploading.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import saoudh.imageuploading.HelperClass;
import saoudh.imageuploading.R;
import saoudh.imageuploading.threads.ImageUploadThread;
import saoudh.imageuploading.viewholders.AlbumImageViewHolder;

public class AlbumImagesAdapter extends BaseAdapter {
    public Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public AlbumImagesAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
        this.activity = activity;
       this.data = data;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumImageViewHolder holder = null;
        if (convertView == null) {
            holder = new AlbumImageViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_albumactivity, parent, false);

            holder.imageview_image = (ImageView) convertView.findViewById(R.id.imageview_albumsactivity_image);
            holder.et_tags = (EditText) convertView.findViewById(R.id.edittext_albumsactivity_tags);

            holder.btn_upload = (Button) convertView.findViewById(R.id.button_albumsactivity_imageupload);


            convertView.setTag(holder);
        } else {
            holder = (AlbumImageViewHolder) convertView.getTag();
        }
        holder.imageview_image.setId(position);
        holder.et_tags.setId(position);
        holder.btn_upload.setId(position);

        HashMap<String, String> itemData = data.get(position);
        final String img_Path = itemData.get(HelperClass.IMAGE_PATH_KEY);
        final String tags = holder.et_tags.getText().toString();

        try {

            Glide.with(activity)
                    .load(new File(itemData.get(HelperClass.IMAGE_PATH_KEY)))
                    .into(holder.imageview_image);


        } catch (Exception e) {
        }
        holder.btn_upload.setOnClickListener(view -> new ImageUploadThread(img_Path, tags).start());


        return convertView;
    }
}
