package com.example.bugra.mapzz.ui.map;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.ui.common.BaseActivity;
import com.example.bugra.mapzz.ui.plantActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;


public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = MapActivity.class.getSimpleName();

    private LinearLayout legend;
    private TextView legend2;
    LoginButton loginButton;
    CallbackManager callbackManager;
    MapActivityViewModel viewModel;

    //GPSTracker gps = new GPSTracker(this);

    @Override
    protected int getLayoutId() {
        return R.layout.map_activity;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        viewModel = ViewModelProviders.of( this ).get( MapActivityViewModel.class );

        FacebookSdk.sdkInitialize( getApplicationContext() );

        legend = (LinearLayout) findViewById( R.id.legendlayout );
        legend2 = (TextView) findViewById( R.id.legend2layout );


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        loginButton = findViewById( R.id.fblogin );
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback( callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess( LoginResult loginResult ) {
                // changeName(findViewById(R.id.profile_name_text),loginResult);

                findViewById( R.id.map_messages_button ).setVisibility( View.VISIBLE );
                findViewById( R.id.map_profile_button ).setVisibility( View.VISIBLE );

                loginButton.setVisibility( View.INVISIBLE );

                Toast.makeText( MapActivity.this, "success", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText( MapActivity.this, "cancel", Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onError( FacebookException error ) {
                Toast.makeText( MapActivity.this, "error", Toast.LENGTH_SHORT ).show();

            }
        } );

        legend2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent i = new Intent( MapActivity.this, plantActivity.class );
                startActivity( i );
                overridePendingTransition( R.anim.up1, R.anim.up2 );
            }
        } );
    }

    @Override
    public void onMapReady( GoogleMap googleMap ) {

        googleMap.getUiSettings().setZoomControlsEnabled( true );
        googleMap.setMinZoomPreference( 11 );

        googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle( this, R.raw.style_json ) );

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap( this );
        googleMap.setInfoWindowAdapter( customInfoWindow );


        googleMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker ) {
                legend.setVisibility( View.GONE );
                legend2.setVisibility( View.VISIBLE );
                return false;
            }
        } );

        googleMap.setOnMapClickListener( new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick( LatLng latLng ) {
                legend.setVisibility( View.VISIBLE );
                legend2.setVisibility( View.GONE );
            }
        } );


        viewModel.setMap( googleMap );
        viewModel.setupMap();
    }

}


