package com.example.ahmedb2.calcounter;

import android.content.Context;
import android.os.AsyncTask;
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
 * Created by ahmedb2 on 1/23/2017.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    Handler handler;

    BackgroundWorker (Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String user_signup_url = "http://jasmine.cs.vcu.edu:20038/~ahmedb2/seniorDesign/user_update.php";
        if(type.equals("main_insert")) {
            try {
                URL url = new URL(user_signup_url);
                String app_name = params[1];
                String perm_name = params[2];
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("app_name", "UTF-8") + "=" + URLEncoder.encode(app_name, "UTF-8") +"&"
                        + URLEncoder.encode("perm_name", "UTF-8") + "=" + URLEncoder.encode(perm_name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            return null;

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
