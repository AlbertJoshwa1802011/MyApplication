package com.example.myapplication;

public class MessageModel {
    private String cnt;

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public MessageModel(String cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "cnt='" + cnt + '\'' +
                '}';
    }
}
