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
import android.widget.TextView;

import com.example.ahmedb2.calcounter.Activities.ExerciseLogActivity;
import com.example.ahmedb2.calcounter.Activities.FoodLogActivity;
import com.example.ahmedb2.calcounter.Activities.MainActivity;
import com.example.ahmedb2.calcounter.R;
import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Weekly extends Fragment {
    TextView total_w_food_calories, total_w_exercise_calories, total_weekly_calories;
    String username;
    String weekly_performs, weekly_consumes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weekly, container, false);

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");

        total_w_food_calories = (TextView) view.findViewById(R.id.total_w_food_calories);
        total_w_exercise_calories = (TextView) view.findViewById(R.id.total_w_exercise_calories);
        total_weekly_calories = (TextView) view.findViewById(R.id.total_weekly_calories);

        BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        try {
            weekly_performs = backgroundWorker.execute("get_weekly_performs", username).get();
            weekly_performs = weekly_performs.substring(18);
            total_w_exercise_calories.setText("Total Exercise Calories: " + weekly_performs);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        backgroundWorker= new BackgroundWorker(getContext());
        try {
            weekly_consumes = backgroundWorker.execute("get_weekly_consumes", username).get();
            weekly_consumes = weekly_consumes.substring(18);
            total_w_food_calories.setText("Total Food Calories: " + weekly_consumes);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(!weekly_performs.equals("") && !weekly_consumes.equals("")) {
            int total = Integer.parseInt(weekly_consumes)-Integer.parseInt(weekly_performs);
            total_weekly_calories.setText("Total weekly Calories: " + total);
        }


        return  view;

    }

}
