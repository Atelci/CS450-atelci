package com.example.bugra.mapzz.ui.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public abstract class BaseActivity extends AppCompatActivity {
    protected ViewDataBinding mViewDataBinding;

    static private final String TAG = "BaseActivity";

    protected abstract int getLayoutId();

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mViewDataBinding = DataBindingUtil.setContentView( this, getLayoutId() );

        Log.d( TAG, "Created -> " + getResources().getResourceName( getLayoutId() ) );
    }
}