package com.example.ahmedb2.calcounter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ahmedb2 on 4/18/2017.
 */

public class BackgroundWorker {

    Context context;

    BackgroundWorker (Context ctx) {
        context = ctx;
    }

    protected String doInBackground(String... params) {

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String aVoid){
        super.onPostExecute(aVoid);
        /*Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("result", aVoid);
        message.setData(bundle);
        handler.sendMessage(message);*/
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
