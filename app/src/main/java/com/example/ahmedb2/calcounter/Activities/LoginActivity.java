package com.example.ahmedb2.calcounter.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;
import com.example.ahmedb2.calcounter.Adapters.LoginDataBaseAdapter;
import com.example.ahmedb2.calcounter.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText input_email, input_password;
    Button btn_login;
    TextView link_signup;
    LoginDataBaseAdapter login;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //String md5Pass = md5("billy");
        //Log.d("md5Pass", md5Pass);
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

//        if(hasLoggedIn){
//            Intent intent = new Intent(getBaseContext(), InputActivity.class);
//            startActivity(intent);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this.getApplicationContext();
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        link_signup = (TextView) findViewById(R.id.link_signup);

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Set OnClick Listener on SignUp button
        link_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                startActivity(new Intent(v.getContext(), SignupActivity.class));
                //finish();

            }
        });


        // Set On ClickListener*/
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String username=input_email.getText().toString();
                String password=input_password.getText().toString();
                password = md5(password);
                String storedPassword;

                // fetch the Password form database for respective user name
                storedPassword= "";
                try {
                    BackgroundWorker backgroundWorker = new BackgroundWorker(context);
                    storedPassword = backgroundWorker.execute("user_login", username).get();
                    Log.d("passwordsn", storedPassword);
                    storedPassword = storedPassword.substring(18);
                    // check if the Stored password matches with  Password entered by user
                    if(password.equals(storedPassword))
                    {
//                    SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
//                    SharedPreferences.Editor editor = settings.edit();
//                    editor.putBoolean("hasLoggedIn", true);
//                    editor.putString("loginName",email.substring(0, email.indexOf("@")));
//                    editor.commit();

                        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();

                        editor.putString("loginName", username);
                        editor.putBoolean("hasLoggedIn", true);
                        editor.commit();
                        Intent intent = new Intent(getBaseContext(), InputActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Email or Password does not match", Toast.LENGTH_LONG).show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


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
