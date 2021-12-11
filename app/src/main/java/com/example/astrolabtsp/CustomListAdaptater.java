package com.example.astrolabtsp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdaptater extends ArrayAdapter {
    //to reference the activity
    private Activity context;

    //To store the user pic
    private Integer[] imageArray;

    //to store the user name
    private String[] nomArray;

    //to store the user bio
    private String[] bioArray;

    CustomListAdaptater(Activity context, Integer[] imageParam, String[] nomParam, String[] bioParam){
        super(context, R.layout.listview_row, imageParam);

        this.context = context;
        this.imageArray =imageParam;
        this.nomArray = nomParam;
        this.bioArray = bioParam;
    };

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row,null, true);

        //this code gets reference to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.textViewinListElement1);
        TextView bioTextField = (TextView) rowView.findViewById(R.id.textViewinListElement2);
        ImageView imageField = (ImageView) rowView.findViewById(R.id.imageViewinListElement);

        //this code sets the value of the objects to values from the arrays
        nameTextField.setText(nomArray[position]);
        bioTextField.setText(bioArray[position]);
        imageField.setImageResource(imageArray[position]);

        return rowView;
    };




}

