package com.example.bugra.mapzz.ui.map;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.repository.PlantRepository;
import com.facebook.AccessToken;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MapActivityViewModel extends AndroidViewModel {

    static private final String TAG = "MapActivityViewModel";

    private ArrayList<Marker> markers = new ArrayList<>();
    private PlantRepository repository = new PlantRepository();
    private Plant.TYPE currentFilter;
    private GoogleMap map;
    private FusedLocationProviderClient locationProvider;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public final ObservableBoolean isUserLoggedIn = new ObservableBoolean( auth.getCurrentUser() != null );
    public final ObservableBoolean isMarkerFocused = new ObservableBoolean( false );

    public MapActivityViewModel( @NonNull Application application ) {
        super( application );

        locationProvider = new FusedLocationProviderClient( application );
    }

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

    private Bitmap getMarkerIcon( Plant.TYPE type ) {

        int resourceId = 0;

        switch( type ) {
            case GREENERY:
                resourceId = R.drawable.greenery;
                break;
            case FARMING:
                resourceId = R.drawable.farming;
                break;
            case FLOWER:
                resourceId = R.drawable.flower;
                break;
        }

        BitmapDrawable drawable = (BitmapDrawable) getApplication().getResources().getDrawable( resourceId );
        Bitmap bitmap = drawable.getBitmap();

        return Bitmap.createScaledBitmap(
                bitmap,
                bitmap.getWidth() / 3,
                bitmap.getHeight() / 3,
                false
        );
    }

    public Marker addMarker( LatLng latLng, Plant.TYPE plantType ) {

        MarkerOptions markerOptions = new MarkerOptions()
                .position( latLng )
                .icon( BitmapDescriptorFactory.fromBitmap( getMarkerIcon( plantType ) ) );

        Marker marker = map.addMarker( markerOptions );

        marker.setTag( plantType.toString() );

        markers.add( marker );

        return marker;
    }

    public void filterMarkers( Plant.TYPE plantType ) {

        if( currentFilter != null && currentFilter.equals( plantType ) ) {
            for( Marker m : markers ) {
                m.setVisible( true );
            }
            currentFilter = null;
        }
        else {
            for( Marker m : markers ) {
                if( !m.getTag().equals( plantType.toString() ) ) {
                    m.setVisible( false );
                }
                else {
                    m.setVisible( true );
                }
            }
            currentFilter = plantType;
        }
    }

    public void setupMap( GoogleMap freshMap, Context context ) {

        map = freshMap;

        //  Configure Google Map
        map.getUiSettings().setZoomControlsEnabled( true );
        map.setMinZoomPreference( 11 );
        map.setMapStyle( MapStyleOptions.loadRawResourceStyle( context, R.raw.style_json ) );

        //  Set click listeners
        map.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker ) {
                isMarkerFocused.set( true );
                return false;
            }
        } );

        map.setOnMapClickListener( new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick( LatLng latLng ) {
                isMarkerFocused.set( false );
            }
        } );


        //  Fill with random markers
        for( int i = 0; i < 24; i++ ) {

            Plant plant = repository.getRandomPlant();
            addMarker( new LatLng( plant.getLat(), plant.getLng() ), plant.getType() );
        }
    }

    @SuppressLint( "MissingPermission" )
    public void configureLocation( boolean isLocationPermissionGranted ) {

        if( !isLocationPermissionGranted ) {

            map.setMyLocationEnabled( false );
            map.getUiSettings().setMyLocationButtonEnabled( false );

            map.moveCamera( CameraUpdateFactory.newLatLng( new LatLng( 41.01f, 29.056f ) ) );
        }
        else {

            map.setMyLocationEnabled( true );
            map.getUiSettings().setMyLocationButtonEnabled( true );

            getDeviceLocation();
        }
    }

    private void getDeviceLocation() {

        try {
            Task<Location> locationResult = locationProvider.getLastLocation();
            locationResult.addOnCompleteListener( new OnCompleteListener<Location>() {
                @Override
                public void onComplete( @NonNull Task<Location> task ) {
                    if( task.isSuccessful() ) {

                        Location location = task.getResult();
                        map.moveCamera( CameraUpdateFactory.newLatLng(
                                new LatLng( location.getLatitude(), location.getLongitude() )
                        ) );
                    }
                }
            } );
        }
        catch( SecurityException e ) {
            Log.e( "Exception: %s", e.getMessage() );
        }
    }
}