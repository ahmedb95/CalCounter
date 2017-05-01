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

public class Goal extends Fragment {
    EditText goal_id, goal_description, goal_id_completed;
    Button goal_submit, goal_s, goal_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_goal, container, false);
        goal_id = (EditText) view.findViewById(R.id.goal_id);
        goal_description = (EditText) view.findViewById(R.id.goal_description);
        goal_submit = (Button) view.findViewById(R.id.goal_submit);
        goal_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = goal_id.getText().toString();
                String calories = goal_description.getText().toString();
                //submit to database
            }});
        goal_id_completed = (EditText) view.findViewById(R.id.goal_id_completed);
        goal_s = (Button) view.findViewById(R.id.goal_s);
        goal_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = goal_id_completed.getText().toString();
                //submit to database
            }
        });
        goal_log = (Button) view.findViewById(R.id.goal_log);
        goal_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
            }
        });
        return  view;
    }
}
