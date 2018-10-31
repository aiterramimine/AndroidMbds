package com.example.mbds.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mbds.myapplication.services.DBHelper;
import com.example.mbds.myapplication.services.entries.MessageEntry;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
        String sender = "Me";
        String receiver = ((EditText)findViewById(R.id.receiver_edt)).getText().toString();
        String content = ((EditText)findViewById(R.id.content_edt)).getText().toString();

        ContentValues vals = new ContentValues();

        vals.put(MessageEntry.MESSAGE_SENDER, sender);
        vals.put(MessageEntry.MESSAGE_RECEIVER, receiver);
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

    private byte[] cipher(String s) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] ret = cipher.doFinal(s.getBytes());

        Log.d("tagg","The cyphered message is :" + new String(ret));

        return ret;

    }

    private String decipher(byte[] b) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        Log.d("tagg", "Entering the deciphering function");
        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        Log.d("tagg", "Entering the deciphering function 1");
        byte[] decryptedBytes = cipher1.doFinal(b);
        Log.d("tagg", "Entering the deciphering function 2");
        String decrypted = new String(decryptedBytes);
        Log.d("tagg", "Entering the deciphering function 3");

        Log.d("tagg", "The deciphered message is : " + decrypted);

        return decrypted;
    }





}
