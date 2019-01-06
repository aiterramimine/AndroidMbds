package com.example.mbds.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mbds.myapplication.services.DBHelper;
import com.example.mbds.myapplication.services.UserOperations;

import org.json.JSONObject;

import java.util.HashMap;

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
       /* UserOperations userOperations = new UserOperations(this);
        boolean correctCredentials = userOperations.login(loginBox.getText().toString(), passBox.getText().toString());

        if(correctCredentials) {
            submitBtn.setBackgroundColor(Color.GREEN);
        } else {
            submitBtn.setBackgroundColor(Color.RED);
        }*/

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", loginBox.getText().toString());
        params.put("password", passBox.getText().toString());

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://baobab.tokidev.fr/api/login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(params),

                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //((TextView)findViewById(R.id.status)).setText("User registered!");
                                SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);

                                try {
                                    SharedPreferences.Editor editor = mPreferences.edit();

                                    editor.putString("token", response.getString("access_token"));  // Saving string
                                    Log.d("yess", mPreferences.getString("token", null));

                                } catch (Exception e) {
                                    Log.d("yess", mPreferences.getString("token", null));

                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("yess", "no2");

                            }
                        });

        queue.add(jsonObjectRequest);

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

    public void register(View v) {
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
