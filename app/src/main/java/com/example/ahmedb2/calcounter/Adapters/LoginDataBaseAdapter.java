package com.example.ahmedb2.calcounter.Adapters;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ahmedb2.calcounter.Utils.BackgroundWorker;

import java.util.concurrent.ExecutionException;

public class LoginDataBaseAdapter {

    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    //private LoginDataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
    }
    public LoginDataBaseAdapter open() throws SQLException
    {
        //db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String name, String userName,String password)
    {
        BackgroundWorker backgroundWorker = new BackgroundWorker(context);
        backgroundWorker.execute("user_signup", name, userName, password);

        /*ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();*/
    }

    public String getSinlgeEntry(String userName) throws ExecutionException, InterruptedException {
        String password = "";
        Log.d("password", password + "  " + userName);
        BackgroundWorker backgroundWorker = new BackgroundWorker(context);
        password = backgroundWorker.execute("user_login", userName).get();
        Log.d("password", password + "  " + userName);

        password = password.substring(18);
        return password;

        /*Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;*/
    }

}
