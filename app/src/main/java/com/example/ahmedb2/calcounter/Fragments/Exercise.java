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
import com.example.ahmedb2.calcounter.Activities.FoodLogActivity;
import com.example.ahmedb2.calcounter.Activities.MainActivity;
import com.example.ahmedb2.calcounter.Adapters.MyFragmentPagerAdapter;
import com.example.ahmedb2.calcounter.R;
import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Exercise extends Fragment {
    EditText exercise_name, exercise_calories, exercise_duration;
    Button exercise_submit, exercise_s, exercise_log;
    Spinner exercise_completed;
    String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_exercise, container, false);

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");


        exercise_name = (EditText) view.findViewById(R.id.exercise_name);
        exercise_calories = (EditText) view.findViewById(R.id.exercise_calories);
        exercise_submit = (Button) view.findViewById(R.id.exercise_submit);
        exercise_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exercise_name.getText().toString();
                String calories = exercise_calories.getText().toString();
                //submit to database

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("insert_exercise", name, calories, username);
                exercise_name.setText("");
                exercise_calories.setText("");


            }
        });
        exercise_completed = (Spinner) view.findViewById(R.id.exercise_completed);
        exercise_duration = (EditText) view.findViewById(R.id.exercise_duration);
        exercise_s = (Button) view.findViewById(R.id.exercise_s);
        exercise_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exercise_completed.getSelectedItem().toString();
                name = name.substring(0,name.indexOf(":"));
                String duration = exercise_duration.getText().toString();

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("insert_performs", username, name, duration);
                exercise_duration.setText("");
            }
        });
        exercise_log = (Button) view.findViewById(R.id.exercise_log);
        exercise_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
                Intent i = new Intent(getActivity(), ExerciseLogActivity.class);
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
        String result = backgroundWorker.execute("get_exercise", username).get();
        Log.d("result", result);

        if(!result.equals("connection_success~end~")) {
            result = result.substring(18);
            while(!result.equals("~end~")) {
                String exercise = result.substring(0,result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);
                String calories = result.substring(0, result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);

                list.add(exercise + ": " + calories + " calories per minute");
            }

            Spinner spinner = (Spinner) view.findViewById(R.id.exercise_completed);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}
