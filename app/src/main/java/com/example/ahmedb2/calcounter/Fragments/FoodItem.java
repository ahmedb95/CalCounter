package com.example.ahmedb2.calcounter.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
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

import com.example.ahmedb2.calcounter.Activities.FoodLogActivity;
import com.example.ahmedb2.calcounter.Activities.MainActivity;
import com.example.ahmedb2.calcounter.R;
import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FoodItem extends Fragment {
    EditText food_name, food_calories, food_amount;
    Button food_submit, food_s, food_log;
    Spinner food_eaten;
    String username;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");

        view = inflater.inflate(R.layout.activity_food_item, container, false);
        food_name = (EditText) view.findViewById(R.id.food_name);
        food_calories = (EditText) view.findViewById(R.id.food_calories);
        food_submit = (Button) view.findViewById(R.id.food_submit);
        food_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = food_name.getText().toString();
                String calories = food_calories.getText().toString();
                //submit to database

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("insert_food_item", name, calories, username);
                food_name.setText("");
                food_calories.setText("");



            }
        });
        food_eaten = (Spinner) view.findViewById(R.id.food_eaten);
        food_amount = (EditText) view.findViewById(R.id.food_amount);
        food_s = (Button) view.findViewById(R.id.food_s);
        food_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = food_eaten.getSelectedItem().toString();
                name = name.substring(0,name.indexOf(":"));
                String amount = food_amount.getText().toString();
                //submit to database

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("insert_consumes", username, name, amount);
                food_amount.setText("");

            }
        });
        food_log = (Button) view.findViewById(R.id.food_log);
        food_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View Log or Heart Rate
                Intent i = new Intent(getActivity(), FoodLogActivity.class);
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
        String result = backgroundWorker.execute("get_food", username).get();

        if(!result.equals("connection_success~end~")){
            Log.d("result", result);
            result = result.substring(18);
            while(!result.equals("~end~")) {
                String exercise = result.substring(0,result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);
                String calories = result.substring(0, result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);

                list.add(exercise + ": " + calories + " calories per cup");
            }

            Spinner spinner = (Spinner) view.findViewById(R.id.food_eaten);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }


    }
}
