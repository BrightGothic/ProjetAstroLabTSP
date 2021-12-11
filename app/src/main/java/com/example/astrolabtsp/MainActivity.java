package com.example.astrolabtsp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> userlist;
    Handler mainHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //start the data import
        new FetchData().start();


    }

    String data = "";

    class FetchData  extends Thread {

        @Override
        public void run() {
            TextView textView = (TextView) findViewById(R.id.activity_main_textview_prediction);
            boolean yes = true;
            while (yes) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("On est dans le run");
                    }
                });


                try {
                    URL url = new URL("http://horoscope-api.herokuapp.com/horoscope/today/Libra");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        data = data + line;

                    }

                    if (!data.isEmpty()) {

                        JSONObject jsonObject = new JSONObject(data);
                     //   System.out.println("Pas de pb dans le try");

                        String users = jsonObject.getString("horoscope");
                     //   System.out.println(users);
                        textView.post(new Runnable() {
                            public void run() {
                                textView.setText(users);
                            }
                        });
                        //    new DownloadImageTask((ImageView) findViewById(R.id.activity_main_image1)).execute(users);
                        // handle IOException
                        httpURLConnection.disconnect();

                    }


                } catch (MalformedURLException e) {
                    System.out.println("Pb dans le try");

                    e.printStackTrace();
                } catch (IOException ioException) {
                    System.out.println("Pb dans le try");
                    ioException.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("Pb dans le try");

                    e.printStackTrace();
                }
            }
        }
    }
}