package com.example.astrolabtsp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncTaskDownloadDataFroAPI extends AsyncTask<String, String, JSONObject> {
    private TextView textViewhoroscope;
    private TextView textViewdate;
    private AppCompatActivity myActivity;


    // Constructeur de AsyncTaskDownloadDataFroAPI
    public AsyncTaskDownloadDataFroAPI(AppCompatActivity myActivity_, TextView horoscope,TextView date) {
        myActivity = myActivity_;
        this.textViewhoroscope = horoscope;
        this.textViewdate = date;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        // in doInBackground
        URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        InputStream in = null;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        result = readStream(in); // Read stream
        urlConnection.disconnect();
        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json; // returns the resul
    }


    protected void onProgressUpdate(Integer... progress) {
        //    setProgressPercent(progress[0]);
    }

    protected void onPostExecute(JSONObject j) {
        Log.i("JFL", "JSON data: " + j);
        super.onPostExecute(j);
        try {
            String horoscope = j.getString("horoscope");
            textViewhoroscope.setText(horoscope);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String date = j.getString("date");
            textViewdate.setText(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    private String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),
                1024);//ww w  . ja v  a 2 s . c  om
        try {
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}