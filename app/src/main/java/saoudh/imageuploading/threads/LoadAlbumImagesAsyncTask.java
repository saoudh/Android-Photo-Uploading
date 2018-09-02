package saoudh.imageuploading.threads;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.GridView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import saoudh.imageuploading.HelperClass;
import saoudh.imageuploading.R;
import saoudh.imageuploading.adapters.AlbumImagesAdapter;



public class LoadAlbumImagesAsyncTask extends AsyncTask<String, ArrayList<HashMap<String, String>>, ArrayList<HashMap<String, String>>> {
    private WeakReference<Context> context;
    private ArrayList<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
    private String albumName = null;

    public LoadAlbumImagesAsyncTask(Context context, String albumName) {
        this.context = new WeakReference<Context> (context);

        this.albumName = albumName;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.imageList.clear();

    }

    protected ArrayList<HashMap<String, String>> doInBackground(String... args) {


        Uri externalUri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri internalUri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;


        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Cursor externalCursor = this.context.get().getContentResolver().query(externalUri, projection, "bucket_display_name = \"" + this.albumName + "\"", null, null);
        Cursor internalCursor = this.context.get().getContentResolver().query(internalUri, projection, "bucket_display_name = \"" + this.albumName
                + "\"", null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{externalCursor, internalCursor});

        while (cursor.moveToNext()) {
            HashMap<String, String> data = new HashMap<String, String>();
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            data.put(HelperClass.IMAGE_PATH_KEY, path);
            imageList.add(data);
        }

        assert internalCursor!=null;
        assert externalCursor!=null;
        internalCursor.close();
        externalCursor.close();
        cursor.close();
        return imageList;

    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> imageList) {
        GridView albumImagesGridView = ((Activity) this.context.get()).findViewById(R.id.gridview_albumactivity_list_of_albums);
        AlbumImagesAdapter adapter = new AlbumImagesAdapter((Activity) this.context.get(), imageList);
        albumImagesGridView.setAdapter(adapter);
    }
}
