package com.example.bugra.mapzz.repository;


import android.databinding.ObservableField;

import com.example.bugra.mapzz.model.User;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void getUser( ObservableField<User> user, String userId ) {


    }
}