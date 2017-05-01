package com.example.ahmedb2.calcounter.Fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ahmedb2.calcounter.R;

public class HeartRate extends Fragment {
    EditText heart_rate;
    Button hr_submit, heart_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_heart_rate, container, false);
        heart_rate = (EditText) view.findViewById(R.id.heart_rate);
        hr_submit = (Button) view.findViewById(R.id.hr_submit);
        hr_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = heart_rate.getText().toString();
                String calories = heart_rate.getText().toString();
                //submit to database

            }
        });
        heart_log = (Button) view.findViewById(R.id.heart_log);
        heart_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
            }
        });
        return  view;
    }
}
