package com.example.bugra.mapzz.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.ui.common.BaseActivity;

public class LoadingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.loading_activity;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent( LoadingActivity.this, MapActivity.class );
                LoadingActivity.this.startActivity( mainIntent );
                LoadingActivity.this.finish();
            }
        }, 4000 );
    }
}
