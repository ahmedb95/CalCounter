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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_daily, container, false);

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");

        total_food_calories = (TextView) view.findViewById(R.id.total_food_calories);
        total_exercise_calories = (TextView) view.findViewById(R.id.total_exercise_calories);
        total_daily_calories = (TextView) view.findViewById(R.id.total_daily_calories);

        return  view;

    }

}
