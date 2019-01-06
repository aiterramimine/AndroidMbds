package com.example.mbds.myapplication.entities;

public class MessageUtils {

    public static String getAuthor(String msg) {
        String[] separated = msg.split("\\|");
        if(separated.length < 3) return "";
        return separated[0];
    }

    public static boolean isPing(String msg) {
        String[] separated = msg.split("\\|");
        if(separated.length < 3) return false;
        if(separated[1].equals("PING")) return true;
        return false;
    }

    public static boolean isPong(String msg) {
        String[] separated = msg.split("\\|");
        if(separated.length < 3) return false;
        if(separated[1].equals("PONG")) return true;
        return false;
    }

    public static String getContent(String msg) {
        String[] separated = msg.split("\\|");
        if(separated.length < 3 && !separated[1].equals("MSG")) return "";
        return separated[2];
    }
}
