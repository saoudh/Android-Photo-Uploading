package saoudh.imageuploading;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class HelperClass {


    public static final String ALBUM_KEY = "albumName";
    public static final String IMAGE_PATH_KEY = "imagePath";
    public static final String NR_OF_IMG = "nrOfImage";
    public static final String ServerIP = "http://10.0.2.2/";
    public static final String URLImageUpload = "image_server.php";
    public static final int PERMISSION_REQUEST_KEY = 1;
    public static final String PERMISSION_ERROR_MSG = "Permissions not accepted!";
    public static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
    public static final String IMAGE_KEY ="image" ;
    public static final String FILENAME_KEY = "filename";
    public static final String TAGS_KEY = "tags";


    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static String encodeBase64String(String img_path) {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap = BitmapFactory.decodeFile(img_path,
                options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byte_arr = stream.toByteArray();
        return Base64.encodeToString(byte_arr, 0);
    }

    public static int getNrOfPhotosOfAlbum(Context applicationContext, String albumName) {
        Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};
        Cursor externalCursor = applicationContext.getContentResolver().query(uriExternal, projection, "bucket_display_name = \"" + albumName + "\"", null, null);
        Cursor internalCursor = applicationContext.getContentResolver().query(uriInternal, projection, "bucket_display_name = \"" + albumName + "\"", null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{externalCursor, internalCursor});
        int nrOfPhotos=cursor.getCount();
        assert externalCursor != null;
        assert internalCursor != null;
        cursor.close();
        externalCursor.close();
        internalCursor.close();


        return nrOfPhotos;
    }
}
