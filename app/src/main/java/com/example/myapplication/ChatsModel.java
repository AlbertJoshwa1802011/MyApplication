package com.example.myapplication;

public class ChatsModel {

    private String message;

    @Override
    public String toString() {
        return "ChatsModel{" +
                "message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }

    private String sender;

    public ChatsModel(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
