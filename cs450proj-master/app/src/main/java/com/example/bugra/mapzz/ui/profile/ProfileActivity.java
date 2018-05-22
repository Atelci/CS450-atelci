package com.example.bugra.mapzz.ui.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.ui.common.BaseActivity;

public class ProfileActivity extends BaseActivity {

    private ProfileActivityViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of( this ).get( ProfileActivityViewModel.class );

        mViewDataBinding.setVariable( BR.viewModel, viewModel );

        viewModel.fetchUser( getIntent().getStringExtra( "userId" ) );
    }
}