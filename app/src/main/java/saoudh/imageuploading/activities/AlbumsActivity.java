package saoudh.imageuploading.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import saoudh.imageuploading.R;
import saoudh.imageuploading.threads.LoadAlbumImagesAsyncTask;

import static saoudh.imageuploading.HelperClass.ALBUM_KEY;

/**
 * <h1>AlbumsActivity</h1>
 * This Activity lists all images of a selected album
 *
 * This is the starting Activity, which is executed first.
 * It displays all albums of the internal and external memory
 * of the device for selection
 *
 * @author Hossam Saoud
 * @version 1.0
 * @since 2018-31-08
 */
public class AlbumsActivity extends AppCompatActivity {

/**
 * This activity starts immediately with loading all images
 * of the selected album
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Intent intent = getIntent();

        String albumName = intent.getStringExtra(ALBUM_KEY);
        setTitle(albumName);

        // Start Async-Task Thread for loading all images of the selected album and displaying them
        LoadAlbumImagesAsyncTask loadAlbumTask = new LoadAlbumImagesAsyncTask(this, albumName);
        loadAlbumTask.execute();


    }
}