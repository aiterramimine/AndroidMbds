package com.example.mbds.myapplication.services;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public final class CipherUtils {

    public byte[] cipher(String s) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        byte[] ret = null;

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            ret = cipher.doFinal(s.getBytes());
        } catch(Exception e) {

        }

        Log.d("tagg","The cyphered message is :" + new String(ret));

        return ret;

    }

    public String decipher(byte[] b) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        String decrypted = null;

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();


            Log.d("tagg", "Entering the deciphering function");
            Cipher cipher1 = Cipher.getInstance("RSA");
            cipher1.init(Cipher.DECRYPT_MODE, privateKey);
            Log.d("tagg", "Entering the deciphering function 1");
            byte[] decryptedBytes = cipher1.doFinal(b);
            Log.d("tagg", "Entering the deciphering function 2");
            decrypted = new String(decryptedBytes);
            Log.d("tagg", "Entering the deciphering function 3");

            Log.d("tagg", "The deciphered message is : " + decrypted);

        } catch (Exception e) {

        }

        return decrypted;
    }

    public static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    public static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void generateKeyPair(String keyName) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        KeyPairGenerator kpg = null;

        kpg = KeyPairGenerator.getInstance("RSA");

        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyName, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);

        kpg.initialize(builder.build());

        KeyPair kp = kpg.genKeyPair();
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static PublicKey getPublicKey(String keyName) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException, NoSuchProviderException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");

        keyStore.load(null);

        return keyStore.getCertificate(keyName).getPublicKey();
    }

    public static PrivateKey  getPrivateKey(String keyName) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        KeyStore.Entry entry = keyStore.getEntry(keyName, null);
        return ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
    }

    public static byte[] generateSharedKey(Context context, byte[] publicKey, String author, String receiver) throws Exception {
        byte[] clearSharedKey = new byte[128];
        new Random().nextBytes(clearSharedKey);

        SharedPreferences.Editor editor = context.getSharedPreferences("key", Context.MODE_PRIVATE).edit();
        editor.putString("key_" + author + "_" + receiver, new String(clearSharedKey));
        editor.commit();

        return encrypt(publicKey, clearSharedKey);
    }

}
