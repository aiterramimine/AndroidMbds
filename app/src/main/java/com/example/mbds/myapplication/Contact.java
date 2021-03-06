package com.example.mbds.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mbds.myapplication.services.CipherUtils;
import com.example.mbds.myapplication.services.entries.MessageEntry;

import org.json.JSONObject;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Contact extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addContact(View v) {
        // ToDo : Check if contact exists then add it to the local DB
        // For now we assume the contact doesn't exist


        try {
            sendPingMessage();
            //Log.d("success", "contact added");
        } catch (Exception e) {
            //Log.d("error", "contact not added");
            e.printStackTrace();
        }
    }

    public void back(View v) {
        Intent intent = new Intent(this, FragmentExample.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendPingMessage() throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        final String author = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
        String receiver = ((EditText)findViewById(R.id.login_box)).getText().toString();

        try {
            CipherUtils.generateKeyPair("key_" + author + "_" + receiver);
            PublicKey publicKey = CipherUtils.getPublicKey("key_" + author + "_" + receiver);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("message", author + "[|]PING[|]" + "cle");
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
                                    //Log.d("response message", response.getString("msg"));

                                } catch (Exception e) {
                                    Log.d("error add contact", e.getMessage());
                                    return;
                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error", "error adding contact");
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
    }
}
