package com.example.bugra.mapzz.ui;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.bugra.mapzz.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MapStyleOptions;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class Mapzz extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LinearLayout legend;
    private TextView legend2;
    private LinearLayout legendyesil;
    private LinearLayout legendturuncu;
    private LinearLayout legendmor;
    String changedrenk;
    LoginButton loginButton ;
    CallbackManager callbackManager;
    ArrayList<Marker> markerlist;

    //GPSTracker gps = new GPSTracker(this);

    private static final String TAG = Mapzz.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView( R.layout.activity_mapzz);

        legend = (LinearLayout) findViewById(R.id.legendlayout);
        legend2 = (TextView) findViewById(R.id.legend2layout);

        legendyesil = (LinearLayout) findViewById(R.id.legendyesil);
        legendturuncu = (LinearLayout) findViewById(R.id.legendturuncu);
        legendmor = (LinearLayout) findViewById(R.id.legendmor);


        changedrenk = "";

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loginButton=findViewById(R.id.fblogin);
        callbackManager=CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // changeName(findViewById(R.id.profile_name_text),loginResult);

                ImageButton mButton3=findViewById(R.id.imageButton3);
                ImageButton mButton6=findViewById(R.id.imageButton6);
                mButton3.setVisibility(View.VISIBLE);
                mButton6.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);

                // addMarker("pink", "yeni",40.897854, 29.379614 );
                Toast.makeText(Mapzz.this,"success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(Mapzz.this,"cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Mapzz.this,"error", Toast.LENGTH_SHORT).show();

            }
        });


        View.OnClickListener listnr=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Mapzz.this, layout.class);
                startActivity(i);
            }
        };



        legend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mapzz.this, plantActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.up1,R.anim.up2);

            }
        });
        legendyesil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedrenk = filterMarker(changedrenk, "yesil", markerlist, "marker1" );
            }
        });
        legendturuncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedrenk = filterMarker(changedrenk, "turuncu", markerlist, "marker2" );
            }
        });
        legendmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedrenk = filterMarker(changedrenk, "mor", markerlist, "marker3" );
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);

        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));


        LatLng marker1 = new LatLng(41.015137, 28.979530);
        LatLng coord2 = new LatLng(41.020812, 28.988956);
        LatLng coord3 = new LatLng(41.0233323,28.9749202);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(marker1)
                .title("marker1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.yesilmark));




        InfoWindowData info = new InfoWindowData();
        info.setPlant("Plant: Persley");
        info.setHouseInfo("House Information: Balcony");
        info.setSuccess("Success: ***");
        info.setRating(1);
        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
        mMap.setInfoWindowAdapter(customInfoWindow);

        final Marker m = mMap.addMarker(markerOptions);
        m.setTag(info);

        markerlist = new ArrayList<Marker>();
        markerlist.add(m);
        markerlist.add(addmarker(coord2, "marker2", R.drawable.turuncumark, mMap));
        markerlist.add(addmarker(coord3, "marker3", R.drawable.mormark, mMap));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker1));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(m.equals(marker)){
                    legend.setVisibility(View.GONE);
                    legend2.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                legend.setVisibility(View.VISIBLE);
                legend2.setVisibility(View.GONE);
            }
        });

    }

    public static Marker addmarker(LatLng latLng, String title, int drawable, GoogleMap mMap){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(drawable));
        return mMap.addMarker(markerOptions);
    }

    public static String filterMarker(String changedrenk, String color, ArrayList<Marker> markerlist, String markerColor) {
        if(changedrenk.equals(color))
        {
            for (Marker m: markerlist){
                m.setVisible(true);
            }
            changedrenk = "";
        }
        else {
            for (Marker m : markerlist) {
                if (!m.getTitle().equals(markerColor)) {
                    m.setVisible(false);
                } else {
                    m.setVisible(true);
                }
            }
            changedrenk = color;
        }
        return changedrenk;
    }
}


