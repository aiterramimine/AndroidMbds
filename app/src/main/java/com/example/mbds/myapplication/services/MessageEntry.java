package com.example.mbds.myapplication.services;

import android.provider.BaseColumns;

import java.util.Date;

public class MessageEntry implements BaseColumns {
    public static final String TABLE_NAME = "message";
    public static final String COLUMN_NAME_SENDER = "sender";
    public static final String COLUMN_NAME_RECEIVER = "receiver";
    public static final String COLUMN_NAME_CONTENT = "content";
    public static final String COLUMN_NAME_RECEIVED_AT = "receivedAt";
    public static final String COLUMN_NAME_READ = "read";
}
