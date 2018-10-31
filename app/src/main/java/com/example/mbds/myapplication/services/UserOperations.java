package com.example.mbds.myapplication.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mbds.myapplication.services.entries.UserEntry;

public class UserOperations {
    private Context context;
    private SQLiteDatabase dbHelper;

    public UserOperations(Context context) {
        dbHelper = new DBHelper(context).getWritableDatabase();
    }

    public void addUser(String login, String password, String firstName, String lastName) {
        //ToDo : insérer dans la BD distante, pas celle du téléphone

        ContentValues values = new ContentValues();
        values.put(UserEntry.USER_LOGIN, login);
        values.put(UserEntry.USER_PASSWORD, password);
        values.put(UserEntry.USER_FIRSTNAME, firstName);
        values.put(UserEntry.USER_LASTNAME, lastName);

        long newRowId = dbHelper.insert(UserEntry.TABLE_USER, null, values);
        System.out.println("New contact added : \n - First name : " + firstName + "\n - Last name : " + lastName + "\n - Login : " + login);
    }


}
