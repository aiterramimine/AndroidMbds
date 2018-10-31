package com.example.mbds.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mbds.myapplication.services.DBHelper;
import com.example.mbds.myapplication.services.entries.MessageEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateMessage extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        db = new DBHelper(this).getWritableDatabase();

    }

    public void send(View v) {
        String sender = "Me";
        String receiver = ((EditText)findViewById(R.id.receiver_edt)).getText().toString();
        String content = ((EditText)findViewById(R.id.content_edt)).getText().toString();

        ContentValues vals = new ContentValues();

        vals.put(MessageEntry.MESSAGE_SENDER, sender);
        vals.put(MessageEntry.MESSAGE_RECEIVER, receiver);
        vals.put(MessageEntry.MESSAGE_CONTENT, content);

        Date receivedAt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        String receivedAtStr = formatter.format(receivedAt);

        vals.put(MessageEntry.MESSAGE_RECEIVED_AT, receivedAtStr);
        vals.put(MessageEntry.MESSAGE_READ, false);

        long rowId = db.insert(MessageEntry.TABLE_NAME, null, vals);

        Log.d("tagg", "INSERTING MESSAGE | rowId: " + rowId);
    }



}
