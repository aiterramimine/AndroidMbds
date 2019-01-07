package com.example.mbds.myapplication.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.mbds.myapplication.R;
import com.example.mbds.myapplication.entities.Message;
import com.example.mbds.myapplication.entities.MessageUtils;
import com.example.mbds.myapplication.services.entries.MessageEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PollService extends Service {

    private boolean firstTime;
    private Handler mHandler;
    private Context context = this;
    private SQLiteDatabase db;

    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 30 * 1000;

    SharedPreferences mPreferences;

    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            syncData();
            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        firstTime = true;

        mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);

        new DBHelper(this).deleteDatabase(this);

        db = new DBHelper(this).getWritableDatabase();
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private synchronized void syncData() {
        String tok;
        if(mPreferences.getString("token", null) != null)
            tok = mPreferences.getString("token", null);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://baobab.tokidev.fr/api/fetchMessages";

        JsonRequest req = new JsonRequest<JSONArray>(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("poll", response.toString());
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                try {

                    String jsonString =
                            new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                   // return Response.success(new JSONObject(jsonString),
                     //       HttpHeaderParser.parseCacheHeaders(response));

                    JSONArray arr = new JSONArray(jsonString);

                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject o = arr.getJSONObject(i);
                        if(o.getBoolean("alreadyReturned") && !firstTime)
                            continue;
                        if(MessageUtils.isPing(o.getString("msg"))) {
                            String author = MessageUtils.getAuthor(o.getString("msg"));
                            String receiver = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
                            String key = MessageUtils.getKey(o.getString("msg"));
                            String keyName = "key_" + author + "_" + receiver;

                            boolean keyExists = true;
                            try {
                                getSharedPreferences("key", MODE_PRIVATE).getString(keyName, "");
                            } catch (NullPointerException e) {
                                keyExists = false;
                            }

                            if (!keyExists) {
                                byte[] generatedKey = CipherUtils.generateSharedKey(getApplicationContext(), key.getBytes(), author, receiver);
                                sendPongMessage(receiver, author, generatedKey.toString());
                            }

                            continue;
                        }

                        if (MessageUtils.isPong(o.getString("msg"))) {
                            String author = MessageUtils.getAuthor(o.getString("msg"));
                            String receiver = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
                            String keyName = "key_" + author + "_" + receiver;

                            try {
                                getSharedPreferences("key", MODE_PRIVATE).getString(keyName, "");
                            } catch (NullPointerException e) {
                                String key = MessageUtils.getKey(o.getString("msg"));

                                byte[] privateKey = CipherUtils.getPrivateKey(keyName).getEncoded();
                                byte[] clearSharedKey = CipherUtils.decrypt(privateKey, key.getBytes());

                                getSharedPreferences("key", MODE_PRIVATE).edit().putString(keyName, new String(clearSharedKey));
                            }

                            continue;
                        }

                        ContentValues vals = new ContentValues();
                        vals.put(MessageEntry.MESSAGE_SENDER, MessageUtils.getAuthor(o.getString("msg")));
                        vals.put(MessageEntry.MESSAGE_CONTENT, MessageUtils.getContent(o.getString("msg")));
                        vals.put(MessageEntry.MESSAGE_RECEIVED_AT, o.getString("dateCreated"));

                        long rowId = db.insert(MessageEntry.TABLE_NAME, null, vals);

                    }

                    firstTime = false;
                    Log.d("poll", arr.toString());
                    return Response.error(new VolleyError());

                } catch (Exception e) {
                    Log.d("error poll", e.toString());

                    return Response.error(new VolleyError());
                }
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                if(mPreferences.getString("token", null) != null)
                    headers.put("Authorization", "Bearer " + mPreferences.getString("token", null) );
                return headers;
            }
        };

        queue.add(req);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendPongMessage(String author, String receiver, String key) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("message", author + "[|]PONG[|]" + key);
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