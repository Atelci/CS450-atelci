package com.example.bugra.mapzz.ui.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    protected ViewDataBinding mViewDataBinding;

    static private final String TAG = "BaseFragment";

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        mViewDataBinding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(),
                container,
                false
        );

        Log.d( TAG, "Created -> " + getResources().getResourceName( getLayoutId() ) );

        return mViewDataBinding.getRoot();
    }

    @Override
    public abstract void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState );

    protected abstract int getLayoutId();
}
