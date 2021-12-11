package com.example.astrolabtsp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    Handler mainHandler = new Handler();
    String spinner_item = "Aries";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setup bottom navigation bar
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);



        //parametre button go
        Button button_gethoroscope = (Button) findViewById(R.id.activity_home_button_gethoroscope);


        //test spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<String> myAdaptater = new ArrayAdapter<String>(HomeActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.planets_array)
        );
        myAdaptater.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdaptater);

        //get item from spinner

        TextView textchoix = (TextView) findViewById(R.id.activity_home_textview_choix);


        button_gethoroscope.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view,
                            int i, long l) {
                        String state = mySpinner.getItemAtPosition(i).toString();
                        Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT).show();
                        spinner_item = state;
                        System.out.println(spinner_item);

                    }

                    public void onNothingSelected(

                            AdapterView<?> adapterView) {

                    }
                });
                spinner_item = mySpinner.getSelectedItem().toString();
                System.out.println(spinner_item);
                textchoix.setText(spinner_item);
/*
                Thread myThread = new FetchData(spinner_item);
                if(myThread.isAlive()){
                    myThread.interrupt();
                }
                myThread = new FetchData(spinner_item);
                myThread.start();

 */
                new AsyncTaskDownloadDataFroAPI(
                        HomeActivity.this,
                        findViewById(R.id.activity_home_textview_horoscopedata),
                        findViewById(R.id.activity_home_textview_datedata5)
                ).execute("http://horoscope-api.herokuapp.com/horoscope/today/"+spinner_item);
            }
        });


    }

    String data = "";

    class FetchData  extends Thread {
        private String spinner_item;

        public FetchData(String item){
            this.spinner_item = item;
        }

        @Override
        public void run() {
            TextView textViewHoroscope = (TextView) findViewById(R.id.activity_home_textview_horoscopedata);
            TextView textViewDate = (TextView) findViewById(R.id.activity_home_textview_datedata5);

            boolean yes = true;
            while (yes) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("On est dans le run");
                    }
                });


                try {
                    URL url = new URL(("http://horoscope-api.herokuapp.com/horoscope/today/"+spinner_item));
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

                        String horoscope = jsonObject.getString("horoscope");
                        //   System.out.println(users);
                        textViewHoroscope.post(new Runnable() {
                            public void run() {
                                textViewHoroscope.setText(horoscope);
                            }
                        });
                        String date = jsonObject.getString("date");
                        //   System.out.println(users);
                        textViewHoroscope.post(new Runnable() {
                            public void run() {
                                textViewDate.setText(date);
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
    private void switchActivitiesHomeintoSinin(){
        Intent switchActivityIntent = new Intent(this, SignInActivity.class);
        startActivity(switchActivityIntent);
    }






}
