package com.example.ahmedb2.calcounter.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;
import com.example.ahmedb2.calcounter.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    TextView link_login;
    EditText name, email, username, password, confirm_pass, weight, height;
    Spinner age;
    Button btn_signup, btnDisplay;
    RadioGroup gender;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addSpinValue();
        context = getApplicationContext();
        link_login = (TextView) findViewById(R.id.link_login);
        name = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        confirm_pass = (EditText) findViewById(R.id.confirm_password);
        weight = (EditText) findViewById(R.id.input_weight);
        height = (EditText) findViewById(R.id.input_height);
        age = (Spinner) findViewById(R.id.spin_age);
        gender = (RadioGroup) findViewById(R.id.radioSex);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        goToLogin();
        signup();


    }

    public void addSpinValue () {
        List age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        Spinner spinner = (Spinner)findViewById(R.id.spin_age);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public void goToLogin () {
        link_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                startActivity(new Intent(v.getContext(), LoginActivity.class));
                //finish();

            }
        });
    }

    public void signup () {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name_text = name.getText().toString();
                String email_text = email.getText().toString();
                String username_text = username.getText().toString();
                String password_text = password.getText().toString();
                String weight_text = weight.getText().toString();
                String height_text = height.getText().toString();
                String age_text = age.getSelectedItem().toString();
                String gender_text = "";

                password_text = md5(password_text);

                int genderId = gender.getCheckedRadioButtonId();

                if(genderId != -1){
                    RadioButton selectedButton = (RadioButton) findViewById(genderId);
                    gender_text = selectedButton.getText().toString();
                } else {
                    gender_text = "none";
                }
                Log.d("info", name_text + "  " + email_text + "  " + username_text + "  " + weight_text + "  " + height_text + "  " + age_text + "  " + gender_text);
                BackgroundWorker backgroundWorker = new BackgroundWorker(context);
                backgroundWorker.execute("user_signup", username_text, name_text, email_text, password_text, weight_text, height_text, age_text, gender_text);
                /// Create Intent for SignUpActivity  abd Start The Activity
                startActivity(new Intent(v.getContext(), LoginActivity.class));
                //finish();

            }
        });
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
