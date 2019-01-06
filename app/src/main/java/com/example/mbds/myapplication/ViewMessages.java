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
import com.example.mbds.myapplication.services.entries.MessageEntry;
import com.example.mbds.myapplication.services.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewMessages extends AppCompatActivity {

    public static final String PROJECTION[] = {
            BaseColumns._ID,
            MessageEntry.MESSAGE_SENDER,
            MessageEntry.MESSAGE_CONTENT,
            MessageEntry.MESSAGE_RECEIVED_AT,
            MessageEntry.MESSAGE_READ
    };

    //public static final String SELECTION = MessageEntry.MESSAGE_SENDER + " = ?";

    //public static final String[] SELECTION_ARGS = {"Me"};

    //public static final String SORT_ORDER = MessageEntry.MESSAGE_SENDER + " DESC";

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

        db = new DBHelper(this).getReadableDatabase();

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
                    csr.getColumnIndexOrThrow(MessageEntry.MESSAGE_SENDER)
            );

           /* String receiver = csr.getString(
                    csr.getColumnIndexOrThrow(MessageEntry.MESSAGE_RECEIVER)
            );*/

            String content = csr.getString(
                    csr.getColumnIndexOrThrow(MessageEntry.MESSAGE_CONTENT)
            );

            String receivedAt = csr.getString(
                    csr.getColumnIndexOrThrow(MessageEntry.MESSAGE_RECEIVED_AT)
            );

            boolean read = csr.getInt(
                    csr.getColumnIndexOrThrow(MessageEntry.MESSAGE_READ)
            ) != 0;



            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                Date receivedAtDate = sdf.parse(receivedAt);
                Message m = new Message(id, sender, content, receivedAtDate, read);

                messages.add(m);

                Log.d("tagg", "GETTING MESSAGE: " + m);

            } catch( Exception pe) {
                Log.e("tagg", "A date parsing exception has occured");
            }

        }

        csr.close();
    }





}
