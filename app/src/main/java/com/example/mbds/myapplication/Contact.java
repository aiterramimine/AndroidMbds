package com.example.mbds.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Contact extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void addContact() {
        System.out.println("Add contact");
        // ToDo : Check if contact exists then add it to the local DB
    }

    public void back(View v) {
        Intent intent = new Intent(this, FragmentExample.class);
        startActivity(intent);
    }
}
