package com.example.bugra.mapzz.model;

public class Message extends BaseModel {

    private String senderId;
    private String content;
    private long timestamp;

    public Message( String senderId, String content, long timestamp ) {
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId( String senderId ) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( long timestamp ) {
        this.timestamp = timestamp;
    }
}
