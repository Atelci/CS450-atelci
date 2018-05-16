package com.example.bugra.mapzz.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.bugra.mapzz.model.Chat;
import com.example.bugra.mapzz.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatRepository {

    static private final String TAG = "ChatRepository";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void getChats( MutableLiveData<ArrayList<Chat>> data, String userId ) {
        database.getReference( "users" )
                .child( userId )
                .child( "chats" )
                .addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                Chat chat = dataSnapshot.getValue( Chat.class );

                data.getValue().add( chat );
                data.setValue( data.getValue() );
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        } );
    }

    public String createChat( String userId1, String userId2 ) {

        DatabaseReference newChat = database.getReference( "chats" ).push();
        String newChatId = newChat.getKey();

        Log.d( TAG, "createChat: with id => " + newChatId );

        newChat.child( "user1" ).setValue( userId1 );
        newChat.child( "user2" ).setValue( userId2 );

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference( "users" );

        usersRef.child( userId1 ).child( "chats" ).setValue( newChatId );
        usersRef.child( userId2 ).child( "chats" ).setValue( newChatId );

        return newChatId;
    }
}
