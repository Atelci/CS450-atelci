package com.example.bugra.mapzz.ui.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.ui.common.BaseActivity;
import com.example.bugra.mapzz.ui.profile_settings.ProfileSettingsActivity;

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

        findViewById( R.id.profile_edit_button ).setOnClickListener( profileEditButtonClickListener );
    }

    private View.OnClickListener profileEditButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick( View view ) {
            Intent intent = new Intent( ProfileActivity.this, ProfileSettingsActivity.class );
            startActivity( intent );
        }
    };
}