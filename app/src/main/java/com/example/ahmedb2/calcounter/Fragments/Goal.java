package com.example.ahmedb2.calcounter.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ahmedb2.calcounter.Activities.ExerciseLogActivity;
import com.example.ahmedb2.calcounter.Activities.GoalLogActivity;
import com.example.ahmedb2.calcounter.Activities.MainActivity;
import com.example.ahmedb2.calcounter.R;
import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Goal extends Fragment {
    EditText goal_description;
    Spinner goal_completed;
    Button goal_submit, goal_s, goal_log;
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");

        View view = inflater.inflate(R.layout.activity_goal, container, false);
        goal_description = (EditText) view.findViewById(R.id.goal_description);
        goal_submit = (Button) view.findViewById(R.id.goal_submit);
        goal_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String goal_name = goal_description.getText().toString();
                //submit to database

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("insert_daily_goals", goal_name, username);
                goal_description.setText("");
            }});
        goal_completed = (Spinner) view.findViewById(R.id.goal_completed);
        goal_s = (Button) view.findViewById(R.id.goal_s);
        goal_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = goal_completed.getSelectedItem().toString();
                //submit to database

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("insert_completes", username, name, "Completed");
            }
        });
        goal_log = (Button) view.findViewById(R.id.goal_log);
        goal_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
                Intent i = new Intent(getActivity(), GoalLogActivity.class);
                startActivity(i);
            }
        });
        try {
            addSpinValue(view);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  view;
    }

    public void addSpinValue (View view) throws ExecutionException, InterruptedException {
        List<String> list = new ArrayList<String>();
        BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        String result = backgroundWorker.execute("get_goal", username).get();
        Log.d("result", result);

        if(!result.equals("connection_success~end~")) {
            result = result.substring(18);
            while(!result.equals("~end~")) {
                String goal = result.substring(0,result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);
                list.add(goal);
            }

            Spinner spinner = (Spinner) view.findViewById(R.id.goal_completed);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}
