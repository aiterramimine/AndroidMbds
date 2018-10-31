package com.example.mbds.myapplication.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.mbds.myapplication.services.entries.UserEntry;

public class UserOperations {
    private SQLiteDatabase db;

    public UserOperations(Context context) {
        db = new DBHelper(context).getWritableDatabase();
    }

    public void addUser(String login, String password, String firstName, String lastName) {
        //ToDo : insérer dans la BD distante, pas celle du téléphone

        ContentValues values = new ContentValues();
        values.put(UserEntry.USER_LOGIN, login);
        values.put(UserEntry.USER_PASSWORD, password);
        values.put(UserEntry.USER_FIRSTNAME, firstName);
        values.put(UserEntry.USER_LASTNAME, lastName);

        long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);
        System.out.println("New contact added : \n - First name : " + firstName + "\n - Last name : " + lastName + "\n - Login : " + login);
    }

    public boolean login(String login, String password) {
        String[] projection = {
                BaseColumns._ID,
                UserEntry.USER_LOGIN,
                UserEntry.USER_PASSWORD
        };

        String whereClause = "login = ? AND password = ?";
        String[] whereArgs = new String[] {
                login,
                password
        };

        Cursor c = db.query(
                UserEntry.TABLE_NAME,
                projection,
                whereClause,
                whereArgs,
                null,
                null,
                UserEntry._ID);

        if (c.getCount() > 0 ) {
            return true;
        } else {
            return false;
        }
    }


}
