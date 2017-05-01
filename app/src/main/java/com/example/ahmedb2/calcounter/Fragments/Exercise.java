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

public class Exercise extends Fragment {
    EditText exercise_name, exercise_calories, exercise_completed, exercise_duration;
    Button exercise_submit, exercise_s, exercise_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exercise, container, false);
        exercise_name = (EditText) view.findViewById(R.id.exercise_name);
        exercise_calories = (EditText) view.findViewById(R.id.exercise_calories);
        exercise_submit = (Button) view.findViewById(R.id.exercise_submit);
        exercise_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exercise_name.getText().toString();
                String calories = exercise_calories.getText().toString();
                //submit to database

            }
        });
        exercise_completed = (EditText) view.findViewById(R.id.exercise_completed);
        exercise_duration = (EditText) view.findViewById(R.id.exercise_duration);
        exercise_s = (Button) view.findViewById(R.id.exercise_s);
        exercise_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exercise_completed.getText().toString();
                String calories = exercise_duration.getText().toString();
                //submit to database

            }
        });
        exercise_log = (Button) view.findViewById(R.id.exercise_log);
        exercise_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
            }
        });
        return  view;
    }
}
