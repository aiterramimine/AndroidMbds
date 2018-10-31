package com.example.mbds.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mbds.myapplication.services.DBHelper;

public class Register extends AppCompatActivity {
    private DBHelper dbHelper;

    private EditText loginText;
    private EditText passwordText;
    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginText = findViewById(R.id.login_box);
        passwordText = findViewById(R.id.pass_box);

        dbHelper = new DBHelper(getApplicationContext());
    }

    public void register(View v) {
        //ToDo : insert new user here
        System.out.println("New user : " + loginText.getText() + " || " + passwordText.getText());
    }
}
