package com.example.ahmedb2.calcounter.Activities;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.ahmedb2.calcounter.R;
import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExerciseLogActivity extends AppCompatActivity {

    String username;
    private ListView listview;
    ArrayAdapter<String> adapter;
    Button delete, edit;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");

        listview = (ListView) findViewById(R.id.exerciseLogListView);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final Dialog dialog = new Dialog(ExerciseLogActivity.this);
                pos = position;
                dialog.setContentView(R.layout.excercise_dialog);
                dialog.show();
                Button submit = (Button) dialog.findViewById(R.id.exercise_dialog_s);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText dialog_dur = (EditText) dialog.findViewById(R.id.exercise_dialog_duration);
                        String dur = dialog_dur.getText().toString();
                        String text = adapter.getItem(pos).toString();

                        String[] entry = adapter.getItem(pos).toString().split("\n");
                        String time = entry[0].split(": ")[1];
                        String name = entry[1].split(": ")[1].replaceAll(" ", "");
                        String cals = entry[2].split(": ")[1].replaceAll(" ", "");

                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                        backgroundWorker.execute("update_exercise", username, time, name, dur);
                        //edit query
                        dialog.dismiss();
                    }
                });
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Delete query here
                String text = adapter.getItem(i).toString();

                String[] entry = adapter.getItem(i).toString().split("\n");
                String time = entry[0].split(": ")[1];
                String name = entry[1].split(": ")[1].replaceAll(" ", "");
                String cals = entry[2].split(": ")[1].replaceAll(" ", "");
                Log.d("yolo", time + ":" + name + ":");

                BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                backgroundWorker.execute("delete_exercise", username, time, name);
                return true;
            }
        });


        try {
            loadActivity();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadActivity() throws ExecutionException, InterruptedException {

        SharedPreferences settings = getApplicationContext().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        username = settings.getString("loginName", "null");

        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
        String result = backgroundWorker.execute("get_exercise_log", username).get();

        List<String> list = new ArrayList<String>();

        if(!result.equals("connection_success~end~")){
            Log.d("result", result);
            result = result.substring(18);
            while(!result.equals("~end~")) {
                String time = result.substring(0,result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);
                String food_name = result.substring(0, result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);
                String calories = result.substring(0, result.indexOf("="));
                result = result.substring(result.indexOf("=")+1);

                list.add("Time: " + time + "\nExercise Name: " + food_name + "\nTotal Calories: " + calories);
                Log.d("exercise log", "Time: " + time + "\nExercise Name: " + food_name + "\nTotal Calories: " + calories);
            }
        }

        adapter = new ArrayAdapter<String>(this, R.layout.text_list, R.id.content, list);
        listview.setAdapter(adapter);
    }
}
