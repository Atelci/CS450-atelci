package com.example.bugra.mapzz.ui.map;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.ui.common.BaseActivity;
import com.example.bugra.mapzz.ui.PlantActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;


public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private CallbackManager callbackManager;
    private MapActivityViewModel viewModel;

    //GPSTracker gps = new GPSTracker(this);

    @Override
    protected int getLayoutId() {
        return R.layout.map_activity;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        viewModel = ViewModelProviders.of( this ).get( MapActivityViewModel.class );
        mViewDataBinding.setVariable( BR.viewModel, viewModel );

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
                Intent i = new Intent( MapActivity.this, PlantActivity.class );
                startActivity( i );
                overridePendingTransition( R.anim.up1, R.anim.up2 );
            }
        } );
    }

    @Override
    public void onMapReady( GoogleMap googleMap ) {

        viewModel.setupMap( googleMap, this );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}


