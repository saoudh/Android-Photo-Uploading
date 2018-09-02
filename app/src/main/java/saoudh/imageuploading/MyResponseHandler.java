package saoudh.imageuploading;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MyResponseHandler extends AsyncHttpResponseHandler {


    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        if (statusCode == 404) {
            System.out.println("Ressource not found: " + responseBody);
        } else if (statusCode == 500) {
            System.out.println("Something went wrong at server end: " + responseBody);
        } else {
            System.out.println("No internet");
        }


    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        System.out.println("response: " + responseBody.toString());

    }
}

