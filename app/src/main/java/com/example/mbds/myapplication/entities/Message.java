package com.example.mbds.myapplication.entities;

public class Message {

    private long id;
    private String sender;
    private String receiver;
    private String content;

    public Message() {

    }

    public Message(long id, String sender, String receiver, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
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

}
