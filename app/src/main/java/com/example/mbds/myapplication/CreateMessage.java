package com.example.mbds.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mbds.myapplication.services.MessageEntry;
import com.example.mbds.myapplication.services.MessageReaderDBHelper;

public class CreateMessage extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        db = new MessageReaderDBHelper(this).getWritableDatabase();

    }

    public void send(View v) {
        String sender = "Me";
        String receiver = ((EditText)findViewById(R.id.receiver_edt)).getText().toString();
        String content = ((EditText)findViewById(R.id.content_edt)).getText().toString();

        ContentValues vals = new ContentValues();
        vals.put(MessageEntry.COLUMN_NAME_SENDER, sender);
        vals.put(MessageEntry.COLUMN_NAME_RECEIVER, receiver);
        vals.put(MessageEntry.COLUMN_NAME_CONTENT, content);


        long rowId = db.insert(MessageEntry.TABLE_NAME, null, vals);

        Log.d("tagg", "INSERTING MESSAGE | rowId: " + rowId);
    }



}
