package com.example.backend.pinboard;

public class Message {
    private String author;
    private String timestamp;
    private String content;

    public Message(String author, String date, String time, String content) {
        this.author = author;
        this.timestamp = date + " " + time;
        this.content = content;
    }
}
