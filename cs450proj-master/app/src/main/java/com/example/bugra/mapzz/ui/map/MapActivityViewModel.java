package com.example.bugra.mapzz.ui.map;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
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
    MutableLiveData<ArrayList<Plant>> plants = new MutableLiveData<>();
    private PlantRepository repository = new PlantRepository();
    private Plant.TYPE currentFilter;
    private GoogleMap map;
    private final FusedLocationProviderClient locationProvider = new FusedLocationProviderClient( getApplication() );

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public final ObservableBoolean isUserLoggedIn = new ObservableBoolean( auth.getCurrentUser() != null );
    public final ObservableBoolean isMarkerFocused = new ObservableBoolean( false );
    private Marker focusedMarker;
    public final ObservableField<Plant> focusedPlant = new ObservableField<>();

    public MapActivityViewModel( @NonNull Application application ) {
        super( application );

        repository.getPlants( plants );
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

    public void setupMap( GoogleMap freshMap, Context context ) {

        map = freshMap;

        //  Configure Google Map
        map.getUiSettings().setZoomControlsEnabled( true );
        map.setMinZoomPreference( 11 );
        map.setMapStyle( MapStyleOptions.loadRawResourceStyle( getApplication(), R.raw.style_json ) );

        //  Set click listeners
        map.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker ) {
                isMarkerFocused.set( true );
                focusedPlant.set( (Plant) marker.getTag() );

                if( focusedMarker != null ) {
                    dehighlightMarker( focusedMarker );
                }

                focusedMarker = marker;

                highlightMarker( focusedMarker );

                return false;
            }
        } );

        map.setOnMapClickListener( new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick( LatLng latLng ) {
                isMarkerFocused.set( false );
                focusedPlant.set( null );

                dehighlightMarker( focusedMarker );
            }
        } );

        //  Fill with random markers
        for( int i = 0; i < 24; i++ ) {
            addMarker( repository.getRandomPlant() );
        }
    }

    private void highlightMarker( Marker marker ) {
        Bitmap icon = getMarkerIcon( ((Plant) marker.getTag()).getType(), 2 );
        marker.setIcon( BitmapDescriptorFactory.fromBitmap( icon ) );
    }

    private void dehighlightMarker( Marker marker ) {
        Bitmap icon = getMarkerIcon( ((Plant) marker.getTag()).getType(), 3 );
        marker.setIcon( BitmapDescriptorFactory.fromBitmap( icon ) );
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
        } catch( SecurityException e ) {
            Log.e( "Exception: %s", e.getMessage() );
        }
    }

    void updateMarkers( ArrayList<Plant> newPlants ) {

        for( Marker marker : markers ) {
            marker.remove();
        }

        for( Plant plant : newPlants ) {
            addMarker( plant );
        }
    }

    private Bitmap getMarkerIcon( Plant.TYPE type, int factor ) {

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
                bitmap.getWidth() / factor,
                bitmap.getHeight() / factor,
                false
        );
    }

    public Marker addMarker( Plant plant ) {

        MarkerOptions markerOptions = new MarkerOptions()
                .position( new LatLng( plant.getLat(), plant.getLng() ) )
                .icon( BitmapDescriptorFactory.fromBitmap( getMarkerIcon( plant.getType(), 3 ) ) );

        Marker marker = map.addMarker( markerOptions );

        marker.setTag( plant );

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
                if( ((Plant) m.getTag()).getType().equals( plantType ) ) {
                    m.setVisible( true );
                }
                else {
                    m.setVisible( false );
                }
            }
            currentFilter = plantType;
        }
    }
}