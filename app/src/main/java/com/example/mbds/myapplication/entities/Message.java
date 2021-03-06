package com.example.mbds.myapplication.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private long id;
    private String sender;
    private String content;
    private Date receivedAt;
    private boolean read;

    public Message() {

    }

    // Old constructor to be deleted when not used anymore.
    public Message(long id, String sender, String content) {
        this.id = id;
        this.sender = sender;
        this.content = content;
    }

    public Message(long id, String sender, String content, Date receivedAt, boolean read) {
        this(id, sender, content);
        this.receivedAt = receivedAt;
        this.read = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "id:" + id + "|sender:" + sender + "|content:" + content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getFormattedReceivedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        return formatter.format(receivedAt);
    }
}
