package saoudh.imageuploading.threads;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

import saoudh.imageuploading.HelperClass;
import saoudh.imageuploading.activities.AlbumsActivity;
import saoudh.imageuploading.adapters.AlbumAdapter;

import static saoudh.imageuploading.HelperClass.ALBUM_KEY;


public class LoadAlbumsAsyncTask extends AsyncTask<Void, Void, Void> {
    private final Context context;
    private final GridView albumsGridView;
    ArrayList<HashMap<String, String>> albumList;

    /**
     * constructor
     *
     * @param context
     *            context of the AlbumActivity
     * @param albumsGridView
     *             GridView of AlbumActivity
     */
    public LoadAlbumsAsyncTask(Context context, GridView albumsGridView)

    {
        this.context = context;
        this.albumsGridView = albumsGridView;
        this.albumList = new ArrayList<HashMap<String, String>>();
    }

/**
 * empty the ArrayList with albums
 */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.albumList.clear();
    }


/**
 * Method for loading all albums
 *
 */
    protected Void doInBackground(Void... args) {

        // get Uri of internal and external storage
        Uri externalUri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri internalUri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        // Select relevant information in the storage like album-name
        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        // query for images and group them into their respective albums
        Cursor externalCursor = context.getContentResolver().query(externalUri, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);
        Cursor internalCursor = context.getContentResolver().query(internalUri, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);
        // merge cursors
        Cursor cursor = new MergeCursor(new Cursor[]{externalCursor, internalCursor});
        //iterating over all albums and adding to list
        while (cursor.moveToNext()) {
            HashMap<String, String> data = new HashMap<String, String>();
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            String albumName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            int nrOfPhotosOfAlbum = HelperClass.getNrOfPhotosOfAlbum(context.getApplicationContext(), albumName);
            String nrOfPhotosString = nrOfPhotosOfAlbum+" Photos";
            data.put(HelperClass.ALBUM_KEY, albumName);
            data.put(HelperClass.IMAGE_PATH_KEY, path);
            data.put(HelperClass.NR_OF_IMG, nrOfPhotosString);
            albumList.add(data);
        }

        // check if the cursor are not null and closing them
        assert internalCursor!=null;
        assert externalCursor!=null;
        internalCursor.close();
        externalCursor.close();
        cursor.close();
        return null;
    }

/**
 * callback which returns after checking needed permissions
 *  set adapter with the data of the albums
 */
    @Override
    protected void onPostExecute(Void args) {
        AlbumAdapter adapter = new AlbumAdapter(context, albumList);
        albumsGridView.setAdapter(adapter);
        // after clicking on one album start the albumactivity with the album-name as parameter
        albumsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                Intent intent = new Intent(context, AlbumsActivity.class);
                intent.putExtra(ALBUM_KEY, albumList.get(+position).get(ALBUM_KEY));
                context.startActivity(intent);
            }
        });
    }
}
