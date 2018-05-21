package com.example.bugra.mapzz.ui.map;


import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.ui.plant.PlantActivity;
import com.example.bugra.mapzz.ui.common.BaseActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;


public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private static final int FINE_LOCATION_PERMISSION_REQUEST = 0;

    private CallbackManager callbackManager;
    private MapActivityViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.map_activity;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        viewModel = ViewModelProviders.of( this ).get( MapActivityViewModel.class );
        mViewDataBinding.setVariable( BR.viewModel, viewModel );

        viewModel.plants.observe( this, new Observer<ArrayList<Plant>>() {
            @Override
            public void onChanged( @Nullable ArrayList<Plant> newPlants ) {
//                TODO: Enable this when the database is ready
//                viewModel.updateMarkers( newPlants );
            }
        } );

        //  Get Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        //  Configure facebook login button
        LoginButton loginButton = findViewById( R.id.fblogin );
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback( callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess( LoginResult loginResult ) {
                // changeName(findViewById(R.id.profile_name_text),loginResult);

                viewModel.registerUserWithFacebookToken( loginResult.getAccessToken() );

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


        findViewById( R.id.marker_details ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent( MapActivity.this, PlantActivity.class );

                Bundle bundle = new Bundle();
                bundle.putSerializable( "plant", viewModel.focusedPlant.get() );
                intent.putExtras( bundle );

                startActivity( intent );
                overridePendingTransition( R.anim.up1, R.anim.up2 );
            }
        } );
    }

    @Override
    public void onMapReady( GoogleMap googleMap ) {

        viewModel.setupMap( googleMap, this );

        enableMyLocationOnMap();
    }

    private void enableMyLocationOnMap() {

        if( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION },
                    FINE_LOCATION_PERMISSION_REQUEST );


            viewModel.configureLocation( false );
        }
        else {
            viewModel.configureLocation( true );
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        switch( requestCode ) {
            case FINE_LOCATION_PERMISSION_REQUEST:
                if( grantResults.length > 0 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED ) {

                    Log.d( TAG, "onRequestPermissionsResult: Got Location Permission" );

                    enableMyLocationOnMap();
                }
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        callbackManager.onActivityResult( requestCode, resultCode, data );
        super.onActivityResult( requestCode, resultCode, data );
    }
}


