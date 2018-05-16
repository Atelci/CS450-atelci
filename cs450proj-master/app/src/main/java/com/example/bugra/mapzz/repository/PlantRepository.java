package com.example.bugra.mapzz.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.bugra.mapzz.model.Plant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlantRepository {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void getPlants( MutableLiveData<ArrayList<Plant>> liveData ) {

        DatabaseReference ref = database.getReference( "plants" );

        ref.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {

                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();

                ArrayList<Plant> plants = new ArrayList<>();

                for( DataSnapshot snapshot : snapshots ) {
                    plants.add( snapshot.getValue( Plant.class ) );
                }

                liveData.setValue( plants );
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {

            }
        } );
    }

    public Plant getRandomPlant() {
        return new Plant(
                40.975f + (float)(Math.random()) * 0.07f,
                29.026f + (float)(Math.random()) * 0.06f,
                Plant.TYPE.values()[ (int)(Math.random() * 3) ],
                (int)(Math.random() * 5),
                ""
        );
    }
}
