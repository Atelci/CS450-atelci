package com.example.bugra.mapzz.model;


import com.google.firebase.auth.FirebaseUser;

public class User extends BaseModel {

    private String userId;
    private String displayName;
    private String pictureUrl;

    public User() {}

    public User( String userId, String displayName, String pictureUrl ) {
        this.userId = userId;
        this.displayName = displayName;
        this.pictureUrl = pictureUrl;
    }

    public User( FirebaseUser authUser ) {
        this.userId = authUser.getUid();
        this.displayName = authUser.getDisplayName();
        this.pictureUrl = authUser.getPhotoUrl().toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId( String userId ) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return pictureUrl;
    }

    public void setPhotoUrl( String pictureUrl ) {
        this.pictureUrl = pictureUrl;
    }
}
