package com.example.mbds.myapplication.entities;

import java.util.Date;

public class Message {

    private long id;
    private String sender;
    private String receiver;
    private String content;
    private Date date;
    private boolean read;

    public Message() {

    }

    // Old constructor to be deleted when not used anymore.
    public Message(long id, String sender, String receiver, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public Message(long id, String sender, String receiver, String content, Date date, boolean read) {
        this(id, sender, receiver, content);
        this.date = date;
        this.read = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "id:" + id + "|sender:" + sender + "|receiver:" + receiver + "|content:" + content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
