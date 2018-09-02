package saoudh.imageuploading.threads;

import android.os.Handler;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import saoudh.imageuploading.HelperClass;
import saoudh.imageuploading.MyResponseHandler;


/**
 * <h1>ImageUploadThread</h1>
 * This is the starting Activity, which is executed first.
 * It displays all albums of the internal and external memory
 * of the device and enables its selection
 *
 * @author Hossam Saoud
 * @version 1.0
 * @since 2018-31-08
 */
public class ImageUploadThread extends Thread {
    String img_path;
    String tags;
    boolean status;

    public ImageUploadThread(String img_path, String tags) {
        this.img_path = img_path;
        this.tags = tags;
    }


    public void run() {
        String fileNameSegments[] = this.img_path.split("/");
        String filename = fileNameSegments[fileNameSegments.length - 1];
        RequestParams params = new RequestParams();

        String encodedImage = HelperClass.encodeBase64String(img_path);

        params.put(HelperClass.IMAGE_KEY, encodedImage);
        params.put(HelperClass.FILENAME_KEY, filename);
        params.put(HelperClass.TAGS_KEY, tags);

        boolean status = makeHTTPCall(params);
    }

    public boolean getStatus()
    {
        return status;
    }

    private boolean makeHTTPCall(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        StringBuilder sb = new StringBuilder();
        // URL of the php-script of the server
        sb.append(HelperClass.ServerIP);
        sb.append(HelperClass.URLImageUpload);
        // A Handler has to be used here, because it is an asynchronous task
        Handler myHandler = new Handler(Looper.getMainLooper());
        Runnable mRunnable = () -> client.post(sb.toString(),
                params, new MyResponseHandler());
        return myHandler.post(mRunnable);


    }
}