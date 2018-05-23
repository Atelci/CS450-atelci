package com.example.bugra.mapzz.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

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

    public void getPlantsOfUser( MutableLiveData<ArrayList<Plant>> liveData, String userId ) {

        Log.d( "TAG", "getPlantsOfUser: userId => " + userId );

        if( userId.equals( "random" ) ) {

            ArrayList<Plant> plants = new ArrayList<>();
            int amount = (int) (Math.random() * 4) + 2;

            for( int i=0; i<amount; i++ ) {
                plants.add( getRandomPlant() );
            }

            liveData.setValue( plants );

            return;
        }


        DatabaseReference ref = database.getReference( "users" )
                .child( userId )
                .child( "plants" );

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

    static private final String[] plantNames = new String[]{
            "Sarmaşık", "Fesleğen", "Biberiye",
            "Çilek",    "Biber",    "Havuç",
            "Menekşe",  "Zambak",   "Manolya"
    };

    public Plant getRandomPlant() {

        int type = (int) (Math.random() * 3);
        int plantName = type * 3 + (int) (Math.random() * 3);
        int rating = (int) (Math.random() * 5) + 1;

        return new Plant(
                40.965f + (float) (Math.random()) * 0.05f,
                29.026f + (float) (Math.random()) * 0.06f,
                Plant.TYPE.values()[ type ],
                plantNames[ plantName ],
                rating,
                "random"
        );
    }
}
