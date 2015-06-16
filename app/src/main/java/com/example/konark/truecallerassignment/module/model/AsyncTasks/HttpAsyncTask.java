package com.example.konark.truecallerassignment.module.model.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.konark.truecallerassignment.module.controller.TrueCallerAssignmentActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by konark on 12/6/15.
 */

public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    private String url;

    public HttpAsyncTask(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    /*
     * Async task doInBackground method to call url on background thread
     */
    @Override
    protected String doInBackground(String... urls) {
        return GET(url);
    }

    /*
     * onPostExecute displays the results of the AsyncTask.
     */
    @Override
    protected void onPostExecute(String result) {
        ((TrueCallerAssignmentActivity) (context)).onSuccess(result);
    }

    /*
     * Network get request call from AsyncTask.
     */
    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    /*
     * String response from Input String.
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}