package com.example.mbds.myapplication.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessageReaderDBHelper extends SQLiteOpenHelper {

    private final static String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + MessageEntry.TABLE_NAME + " (" +
        MessageEntry._ID + " INTEGER PRIMARY KEY," +
        MessageEntry.COLUMN_NAME_SENDER + " TEXT, " +
        MessageEntry.COLUMN_NAME_RECEIVER + " TEXT, " +
        MessageEntry.COLUMN_NAME_CONTENT + " TEXT)";

    private final static String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MessageReader.db";

    public MessageReaderDBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDatabase(Context ctx) {
        ctx.deleteDatabase(DATABASE_NAME);
    }


}
