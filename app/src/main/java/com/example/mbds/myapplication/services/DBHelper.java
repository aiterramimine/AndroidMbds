package com.example.mbds.myapplication.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mbds.myapplication.services.entries.MessageEntry;
import com.example.mbds.myapplication.services.entries.UserEntry;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "EntityReader.db";

    //ToDo : Foreign key pour les senders/receivers
    private final static String CREATE_MESSAGE =
        "CREATE TABLE " + MessageEntry.TABLE_NAME + "(" +
        MessageEntry._ID + " INTEGER PRIMARY KEY," +
        MessageEntry.MESSAGE_SENDER + " TEXT, " +
        MessageEntry.MESSAGE_RECEIVER + " TEXT, " +
        MessageEntry.MESSAGE_CONTENT + " TEXT," +
        MessageEntry.MESSAGE_RECEIVED_AT + " TEXT," +
        MessageEntry.MESSAGE_READ + " INTEGER" +
        ");";

    private final static String DROP_MESSAGE =
            "DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME;

    private static final String CREATE_USER =
            "CREATE TABLE " + UserEntry.TABLE_NAME + "(" +
            UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserEntry.USER_LOGIN + " VARCHAR(20) NOT NULL, " +
            UserEntry.USER_PASSWORD + " VARCHAR(20) NOT NULL, " +
            UserEntry.USER_FIRSTNAME + " TEXT, " +
            UserEntry.USER_LASTNAME + " TEXT);";

    private static final String DROP_USER =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;


    public DBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_MESSAGE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_MESSAGE);
        db.execSQL(DROP_USER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDatabase(Context ctx) {
        ctx.deleteDatabase(DATABASE_NAME);
    }




}
