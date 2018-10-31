package com.example.mbds.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mbds.myapplication.services.DBHelper;
import com.example.mbds.myapplication.services.UserOperations;

public class Register extends AppCompatActivity {
    private UserOperations userOperations;

    private EditText login;
    private EditText password;
    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login_box);
        password = findViewById(R.id.pass_box);
        firstName = findViewById(R.id.first_name_box);
        lastName = findViewById(R.id.last_name_box);

        userOperations = new UserOperations(this);
    }

    public void register(View v) {

        userOperations.addUser(
                login.getText().toString(),
                password.getText().toString(),
                firstName.getText().toString(),
                lastName.getText().toString());
    }

    public void toLogin(View v) {
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }
}
