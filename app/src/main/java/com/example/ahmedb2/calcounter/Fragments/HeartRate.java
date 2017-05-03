package com.example.ahmedb2.calcounter.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ahmedb2.calcounter.Activities.ExerciseLogActivity;
import com.example.ahmedb2.calcounter.Activities.HeartRateLogActivity;
import com.example.ahmedb2.calcounter.Activities.MainActivity;
import com.example.ahmedb2.calcounter.R;
import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

public class HeartRate extends Fragment {
    EditText heart_rate;
    Button hr_submit, heart_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        final String username = settings.getString("loginName", "null");

        View view = inflater.inflate(R.layout.activity_heart_rate, container, false);
        heart_rate = (EditText) view.findViewById(R.id.heart_rate);
        hr_submit = (Button) view.findViewById(R.id.hr_submit);
        hr_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rate = heart_rate.getText().toString();
                //submit to database

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("insert_heart_rate", username, rate);
                heart_rate.setText("");

            }
        });
        heart_log = (Button) view.findViewById(R.id.heart_log);
        heart_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
                Intent i = new Intent(getActivity(), HeartRateLogActivity.class);
                startActivity(i);
            }
        });
        return  view;
    }
}
