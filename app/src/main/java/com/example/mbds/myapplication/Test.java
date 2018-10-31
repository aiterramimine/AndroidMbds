package com.example.mbds.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mbds.myapplication.services.DBHelper;
import com.example.mbds.myapplication.services.UserOperations;

public class Test extends AppCompatActivity {

    private EditText loginBox;
    private EditText passBox;
    private Button submitBtn;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBox = findViewById(R.id.login_box);
        passBox = findViewById(R.id.pass_box);
        submitBtn = findViewById(R.id.login_submit);

        //db = new DBHelper(this).getWritableDatabase();
        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }


    }

    public void login(View v) {
        UserOperations userOperations = new UserOperations(this);
        boolean correctCredentials = userOperations.login(loginBox.getText().toString(), passBox.getText().toString());

        if(correctCredentials) {
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

    public void toRegister(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void deleteDb(View v) {
        dbHelper.deleteDatabase(this);
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            //System.out.println("Shared text : " + sharedText);
        }
    }



}
