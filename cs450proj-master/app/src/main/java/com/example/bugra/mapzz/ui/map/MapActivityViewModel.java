package com.example.bugra.mapzz.ui.map;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bugra.mapzz.R;
import com.facebook.AccessToken;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MapActivityViewModel extends ViewModel {

    static private final String TAG = "MapActivityViewModel";

    public enum PLANT_TYPE {
        GREENERY,
        FARMING,
        FLOWERS;

        static public int getMarkerIcon( PLANT_TYPE type ) {

            switch( type ) {
                case GREENERY:
                    return R.drawable.yesilmark;
                case FARMING:
                    return R.drawable.turuncumark;
                case FLOWERS:
                    return R.drawable.mormark;
                default:
                    return 0;
            }
        }
    }

    private PLANT_TYPE currentFilter;
    private ArrayList<Marker> markers = new ArrayList<>();
    private GoogleMap map;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public final ObservableBoolean isUserLoggedIn = new ObservableBoolean( auth.getCurrentUser() != null );
    public final ObservableBoolean isMarkerFocused = new ObservableBoolean( false );

    public void registerUserWithFacebookToken( AccessToken accessToken ) {

        Log.d( TAG, "registerUserWithFacebookToken: " + accessToken.getToken() );

        AuthCredential credential = FacebookAuthProvider.getCredential( accessToken.getToken() );

        auth.signInWithCredential( credential )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( @NonNull Task<AuthResult> task ) {

                        if( task.isSuccessful() ) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( TAG, "signInWithCredential:success" );

                            isUserLoggedIn.set( true );
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w( TAG, "signInWithCredential:failure", task.getException() );
                        }
                    }
                } );
    }

    public void setMap( GoogleMap map ) {
        this.map = map;
    }

    public Marker addMarker( LatLng latLng, PLANT_TYPE plantType ) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position( latLng )
                .title( plantType.toString() )
                .icon( BitmapDescriptorFactory.fromResource( PLANT_TYPE.getMarkerIcon( plantType ) ) );

        Marker marker = map.addMarker( markerOptions );


        InfoWindowData info = new InfoWindowData();
        info.setPlant( "Plant: Persley" );
        info.setHouseInfo( "House Information: Balcony" );
        info.setSuccess( "Success: ***" );
        info.setRating( 1 );

        marker.setTag( info );

        markers.add( marker );

        return marker;
    }

    public void filterMarkers( PLANT_TYPE plantType ) {

        if( currentFilter != null  &&  currentFilter.equals( plantType ) ) {
            for( Marker m : markers ) {
                m.setVisible( true );
            }
            currentFilter = null;
        }
        else {
            for( Marker m : markers ) {
                if( !m.getTitle().equals( plantType.toString() ) ) {
                    m.setVisible( false );
                }
                else {
                    m.setVisible( true );
                }
            }
            currentFilter = plantType;
        }
    }

    public void setupMap() {

        LatLng coord1 = new LatLng( 41.015137, 28.979530 );
        LatLng coord2 = new LatLng( 41.020812, 28.988956 );
        LatLng coord3 = new LatLng( 41.0233323, 28.9749202 );

        addMarker( coord1, PLANT_TYPE.GREENERY );
        addMarker( coord2, PLANT_TYPE.FLOWERS );
        addMarker( coord3, PLANT_TYPE.FARMING );

        map.moveCamera( CameraUpdateFactory.newLatLng( coord1 ) );
    }
}