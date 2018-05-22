package com.example.bugra.mapzz.model;


import com.google.firebase.auth.FirebaseUser;

public class User extends BaseModel {

    private String userId;
    private String displayName;
    private String photoUrl;
    private String bio;

    public User() {}

    public User( String userId, String displayName, String photoUrl, String bio ) {
        this.userId = userId;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.bio = bio;
    }

    public User( FirebaseUser authUser ) {
        this.userId = authUser.getUid();
        this.displayName = authUser.getDisplayName();
        this.photoUrl = authUser.getPhotoUrl().toString();
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
        return photoUrl;
    }

    public void setPhotoUrl( String pictureUrl ) {
        this.photoUrl = pictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio( String bio ) {
        this.bio = bio;
    }
}
