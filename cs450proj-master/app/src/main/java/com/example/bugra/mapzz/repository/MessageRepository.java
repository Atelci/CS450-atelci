package com.example.bugra.mapzz.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.bugra.mapzz.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageRepository {

    static private final String TAG = "MessageRepository";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void getMessages( MutableLiveData<ArrayList<Message>> data, String id ) {
        database.getReference( "chats" )
                .child( id )
                .child( "messages" )
                .addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                Message message = dataSnapshot.getValue( Message.class );

                Log.d( TAG, "onDataChange: " + message.getContent() );

                if( data.getValue() == null ) data.setValue( new ArrayList<>() );

                data.getValue().add( message );
                data.postValue( data.getValue() );
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        } );
    }

    public void sendMessage( String id, Message message ) {
        database.getReference( "chats" )
                .child( id )
                .child( "messages" )
                .push()
                .setValue( message );
    }
}
