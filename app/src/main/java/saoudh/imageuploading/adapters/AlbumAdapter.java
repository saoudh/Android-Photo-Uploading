package saoudh.imageuploading.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import saoudh.imageuploading.HelperClass;
import saoudh.imageuploading.R;
import saoudh.imageuploading.viewholders.AlbumViewHolder;

public class AlbumAdapter extends BaseAdapter {
    private Context albumContext;
    private ArrayList<HashMap<String, String>> data;

    public AlbumAdapter(Context albumContext, ArrayList<HashMap<String, String>> data) {
        this.albumContext = albumContext;
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
        AlbumViewHolder holder = null;
        if (convertView == null) {
            holder = new AlbumViewHolder();
            convertView = LayoutInflater.from(albumContext).inflate(
                    R.layout.item_mainactivity, parent, false);

            holder.imageview_albumImage =  convertView.findViewById(R.id.imageview__mainactivity_image);
            holder.tv_nrOfImages = convertView.findViewById(R.id.textview_mainactivity_nr_of_images_in_album);
            holder.albumName =  convertView.findViewById(R.id.textview_mainactivity_albumname);

            convertView.setTag(holder);
        } else {
            holder = (AlbumViewHolder) convertView.getTag();
        }
        holder.imageview_albumImage.setId(position);
        holder.tv_nrOfImages.setId(position);
        holder.albumName.setId(position);

        HashMap<String, String> album = data.get(position);
        try {
            holder.albumName.setText(album.get(HelperClass.ALBUM_KEY));
            holder.tv_nrOfImages.setText(album.get(HelperClass.NR_OF_IMG));

            Glide.with(albumContext)
                    .load(new File(album.get(HelperClass.IMAGE_PATH_KEY)))
                    .into(holder.imageview_albumImage);


        } catch (Exception e) {
        }
        return convertView;
    }
}
