package com.example.ahmedb2.calcounter.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ahmedb2.calcounter.R;

public class FoodItem extends Fragment {
    EditText food_name, food_calories, food_eaten, food_amount;
    Button food_submit, food_s, food_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_food_item, container, false);
        food_name = (EditText) view.findViewById(R.id.food_name);
        food_calories = (EditText) view.findViewById(R.id.food_calories);
        food_submit = (Button) view.findViewById(R.id.food_submit);
        food_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = food_name.getText().toString();
                String calories = food_calories.getText().toString();
                //submit to database

            }
        });
        food_eaten = (EditText) view.findViewById(R.id.food_eaten);
        food_amount = (EditText) view.findViewById(R.id.food_amount);
        food_s = (Button) view.findViewById(R.id.food_s);
        food_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = food_eaten.getText().toString();
                String calories = food_amount.getText().toString();
                //submit to database

            }
        });
        food_log = (Button) view.findViewById(R.id.food_log);
        food_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
            }
        });
        return  view;
    }
}
