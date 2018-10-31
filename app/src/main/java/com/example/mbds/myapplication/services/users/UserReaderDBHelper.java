package com.example.mbds.myapplication.services.users;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserReaderDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MessageReader.db";

    private static final String TABLE_USERS_CREATE =
            "CREATE TABLE " + UserEntry.TABLE_USER + "(" +
                    UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UserEntry.USER_LOGIN + " VARCHAR(20) NOT NULL, " +
                    UserEntry.USER_PASSWORD + "  NOT NULL);";

    private static final String TABLE_USERS_DELETE =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_USER;

    public UserReaderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USERS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(UserReaderDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_USER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDatabase(Context ctx) {
        ctx.deleteDatabase(DATABASE_NAME);
    }
}
