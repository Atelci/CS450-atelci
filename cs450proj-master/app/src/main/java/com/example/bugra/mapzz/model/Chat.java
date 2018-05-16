package com.example.bugra.mapzz.model;

public class Chat extends BaseModel {

    private String receiverId;

    public Chat( String receiverId ) {

        this.receiverId = receiverId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId( String receiverId ) {
        this.receiverId = receiverId;
    }
}
