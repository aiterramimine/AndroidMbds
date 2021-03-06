package com.example.mbds.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mbds.myapplication.services.DBHelper;
import com.example.mbds.myapplication.services.PollService;
import com.example.mbds.myapplication.services.entries.MessageEntry;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CreateMessage extends AppCompatActivity {

    private SQLiteDatabase db;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        db = new DBHelper(this).getWritableDatabase();

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch(Exception e) {

        }

    }

    public void send(View v) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException{
        final String author = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
        String receiver = ((EditText)findViewById(R.id.receiver_edt)).getText().toString();
        String content = ((EditText)findViewById(R.id.content_edt)).getText().toString();


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("message", author + "[|]MSG[|]" + content);
        params.put("receiver", receiver);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://baobab.tokidev.fr/api/sendMsg";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(params),

                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);

                                try {

                                } catch (Exception e) {
                                    Log.d("error send", e.getMessage());
                                    return;
                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {

                SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + mPreferences.getString("token", ""));

                return headers;
            }
        };

        queue.add(jsonObjectRequest);


        ContentValues vals = new ContentValues();

        vals.put(MessageEntry.MESSAGE_SENDER, "Me");
        //vals.put(MessageEntry.MESSAGE_RECEIVER, receiver);
        vals.put(MessageEntry.MESSAGE_CONTENT, content);

        Date receivedAt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        String receivedAtStr = formatter.format(receivedAt);

        vals.put(MessageEntry.MESSAGE_RECEIVED_AT, receivedAtStr);
        vals.put(MessageEntry.MESSAGE_READ, false);

        long rowId = db.insert(MessageEntry.TABLE_NAME, null, vals);

        super.onBackPressed();

        //decipher(cipher(content));

        Toast.makeText(this, "Message sent successfully", Toast.LENGTH_LONG).show();

        //Log.d("tagg", "INSERTING MESSAGE | rowId: " + rowId);
    }




}
