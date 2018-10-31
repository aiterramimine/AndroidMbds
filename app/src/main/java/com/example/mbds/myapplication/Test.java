package com.example.mbds.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mbds.myapplication.services.MessageReaderDBHelper;

public class Test extends AppCompatActivity {

    private EditText loginBox;

    private EditText passBox;

    private Button submitBtn;


    private MessageReaderDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBox = findViewById(R.id.login_box);
        passBox = findViewById(R.id.pass_box);
        submitBtn = findViewById(R.id.login_submit);

        //db = new MessageReaderDBHelper(this).getWritableDatabase();
        dbHelper = new MessageReaderDBHelper(this);
    }

    public void login(View v) {
        if(loginBox.getText().toString().equals("toto")
                && passBox.getText().toString().equals("tata")) {
            submitBtn.setBackgroundColor(Color.GREEN);
        } else {
            submitBtn.setBackgroundColor(Color.RED);
        }
    }

    /**
     * Starts the FragmentExample activity
     * @param v The button that will trigger the call.
     */
    public void toFragmentExample(View v) {
        Intent intent = new Intent(this, FragmentExample.class);
        startActivity(intent);

    }

    public void toCreateMessage(View v) {
        Intent intent = new Intent(this, CreateMessage.class);
        startActivity(intent);
    }

    public void toMessages(View v) {
        Intent intent = new Intent(this, ViewMessages.class);
        startActivity(intent);
    }

    public void deleteDb(View v) {
        dbHelper.deleteDatabase(this);
    }



}
