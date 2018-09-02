package saoudh.imageuploading.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import saoudh.imageuploading.HelperClass;
import saoudh.imageuploading.R;
import saoudh.imageuploading.threads.LoadAlbumsAsyncTask;

import static saoudh.imageuploading.HelperClass.PERMISSIONS;

/**
 * <h1>MainActivity</h1>
 * This is the starting Activity, which is executed first.
 * It displays all albums of the internal and external memory
 * of the device and enables its selection
 *
 * @author H. Saoud
 * @version 1.0
 * @since 2018-31-08
 */
public class MainActivity extends AppCompatActivity {

    private LoadAlbumsAsyncTask loadAlbumTask;
    private GridView albumsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        albumsGridView = findViewById(R.id.gridview_mainactivity_list_of_albums);
        //check if all needed permissions are given
        if (!HelperClass.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, HelperClass.PERMISSION_REQUEST_KEY);
        }


    }

    /**
     * callback which returns after checking needed permissions
     *
     * @param requestCode  request code of the executed method
     * @param permissions  requested permissions
     * @param grantResults granted permissions
     *
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case HelperClass.PERMISSION_REQUEST_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadAlbumTask = new LoadAlbumsAsyncTask(getApplicationContext(), albumsGridView);
                    loadAlbumTask.execute();
                } else {
                    Toast.makeText(MainActivity.this, HelperClass.PERMISSION_ERROR_MSG, Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    /**
     *  Android method for resuming the activity
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        //check if permissions are given
        if (!HelperClass.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, HelperClass.PERMISSION_REQUEST_KEY);
        } else {
            //If permissions are given, then start loading albums to a gridview with the AsyncTask-Class LoadAlbumsAsyncTask
            loadAlbumTask = new LoadAlbumsAsyncTask(getApplicationContext(), albumsGridView);
            loadAlbumTask.execute();
        }

    }
}
