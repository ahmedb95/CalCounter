package com.example.ahmedb2.calcounter.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ahmedb2.calcounter.R;
import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FoodLogActivity extends AppCompatActivity {

    String username;
    private ListView listview;
    ArrayAdapter<String> adapter;
    Button delete, edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_log);

        listview = (ListView) findViewById(R.id.foodLogListView);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delete = (Button) view.findViewById(R.id.delete);
                edit = (Button) view.findViewById(R.id.edit);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete from database
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete from database
                    }
                });
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
        String result = backgroundWorker.execute("get_food_log", username).get();

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

                list.add("Time: " + time + "\nFood Name: " + food_name + "\nTotal Calories: " + calories);
            }
        }

        adapter = new ArrayAdapter<String>(this, R.layout.text_list, R.id.content, list);
        listview.setAdapter(adapter);
    }
}
