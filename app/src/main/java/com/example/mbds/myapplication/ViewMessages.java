package com.example.mbds.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.mbds.myapplication.adapters.MessageAdapter;
import com.example.mbds.myapplication.entities.Message;
import com.example.mbds.myapplication.services.MessageEntry;
import com.example.mbds.myapplication.services.MessageReaderDBHelper;

import java.util.ArrayList;
import java.util.List;


public class ViewMessages extends AppCompatActivity {

    public static final String PROJECTION[] = {
            BaseColumns._ID,
            MessageEntry.COLUMN_NAME_SENDER,
            MessageEntry.COLUMN_NAME_RECEIVER,
            MessageEntry.COLUMN_NAME_CONTENT
    };

    //public static final String SELECTION = MessageEntry.COLUMN_NAME_SENDER + " = ?";

    //public static final String[] SELECTION_ARGS = {"Me"};

    //public static final String SORT_ORDER = MessageEntry.COLUMN_NAME_SENDER + " DESC";

    private SQLiteDatabase db;

    private Cursor csr;

    private List<Message> messages;

    private ListView messagesLv;

    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        messagesLv = findViewById(R.id.messages_lv);

        messages = new ArrayList<>();

        adapter = new MessageAdapter(this, messages);

        messagesLv.setAdapter(adapter);

        db = new MessageReaderDBHelper(this).getReadableDatabase();

        csr = db.query(
                MessageEntry.TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null,
                null
        );

        findMessages();
    }

    private void findMessages() {

        while(csr.moveToNext()) {
            long id = csr.getLong(
                    csr.getColumnIndexOrThrow(MessageEntry._ID));

            String sender = csr.getString(
                    csr.getColumnIndexOrThrow(MessageEntry.COLUMN_NAME_SENDER)
            );

            String receiver = csr.getString(
                    csr.getColumnIndexOrThrow(MessageEntry.COLUMN_NAME_RECEIVER)
            );

            String content = csr.getString(
                    csr.getColumnIndexOrThrow(MessageEntry.COLUMN_NAME_CONTENT)
            );

            Message m = new Message(id, sender, receiver, content);

            messages.add(m);

            Log.d("tagg", "GETTING MESSAGE: " + m);

        }

        csr.close();
    }





}
