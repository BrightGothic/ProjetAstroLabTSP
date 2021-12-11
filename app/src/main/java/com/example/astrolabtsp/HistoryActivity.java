package com.example.astrolabtsp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity {

  //  com.example.testlistview.FeedReaderDBHelper db = new com.example.testlistview.FeedReaderDBHelper(this);

    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };

    String[] infoArray = {
            "8 tentacled monster",
            "Delicious in rolls",
            "Great for jumpers",
            "Nice in a stew",
            "Great for shoes",
            "Scary."
    };

    Integer[] imageArray = {R.drawable.octopus,
            R.drawable.pig,
            R.drawable.sheep,
            R.drawable.rabbit,
            R.drawable.snake,
            };

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

     CustomListAdaptater whatever = new CustomListAdaptater(this, imageArray, nameArray, infoArray);

        listView = (ListView) findViewById(R.id.activity_history_listview);
        listView.setAdapter(whatever);
    }
}