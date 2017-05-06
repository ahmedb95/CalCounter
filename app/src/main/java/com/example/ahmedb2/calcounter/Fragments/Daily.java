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

public class Daily extends Fragment {
    TextView total_food_calories, total_exercise_calories, total_daily_calories;
    String username;
    String daily_performs, daily_consumes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_daily, container, false);

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");



        total_food_calories = (TextView) view.findViewById(R.id.total_food_calories);
        total_exercise_calories = (TextView) view.findViewById(R.id.total_exercise_calories);
        total_daily_calories = (TextView) view.findViewById(R.id.total_daily_calories);

        BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        try {
            daily_performs = backgroundWorker.execute("get_daily_performs", username).get();
            daily_performs = daily_performs.substring(18);
            total_exercise_calories.setText("Total Exercise Calories: " + daily_performs);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        backgroundWorker= new BackgroundWorker(getContext());
        try {
            daily_consumes = backgroundWorker.execute("get_daily_consumes", username).get();
            daily_consumes = daily_consumes.substring(18);
            total_food_calories.setText("Total Food Calories: " + daily_consumes);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(!daily_performs.equals("") && !daily_consumes.equals("")) {
            int total = Integer.parseInt(daily_consumes)-Integer.parseInt(daily_performs);
            total_daily_calories.setText("Total Daily Calories: " + total);
        }

        return  view;

    }

}
