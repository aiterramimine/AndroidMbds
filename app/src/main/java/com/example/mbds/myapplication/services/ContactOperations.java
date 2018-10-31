package com.example.mbds.myapplication.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mbds.myapplication.Contact;
import com.example.mbds.myapplication.services.entries.ContactEntry;
import com.example.mbds.myapplication.services.entries.UserEntry;

public class ContactOperations {
    private SQLiteDatabase db;

    public ContactOperations(Context context) {
        db = new DBHelper(context).getWritableDatabase();
    }


    public void addContact(String firstName, String lastName, String nickName) {
        ContentValues values = new ContentValues();
        values.put(ContactEntry.CONTACT_FIRSTNAME, firstName);
        values.put(ContactEntry.CONTACT_LASTNAME, lastName);
        if (lastName.length() > 0) {
            values.put(UserEntry.USER_LASTNAME, lastName);
        }

        long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);

        System.out.println("New contact : \n - First name : " + firstName + "\n - Last name : " + lastName + "\n - Nickname : " + nickName);
    }

    public void addContact(String firstName, String lastName) {
        ContentValues values = new ContentValues();
        values.put(ContactEntry.CONTACT_FIRSTNAME, firstName);
        values.put(ContactEntry.CONTACT_LASTNAME, lastName);

        long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);

        System.out.println("New contact : \n - First name : " + firstName + "\n - Last name : " + lastName);
    }
}
