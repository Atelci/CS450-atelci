package com.example.bugra.mapzz.ui.profile_settings;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.ui.common.BaseActivity;
import com.example.bugra.mapzz.ui.map.MapActivity;

public class ProfileSettingsActivity extends BaseActivity {

    ProfileSettingsActivityViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.profile_settings_activity;
    }

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        viewModel = ViewModelProviders.of( this ).get( ProfileSettingsActivityViewModel.class );

        mViewDataBinding.setVariable( BR.viewModel, viewModel );

        findViewById( R.id.profile_logout_button ).setOnClickListener( logoutButtonClickListener );
    }

    private View.OnClickListener logoutButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick( View view ) {
            viewModel.logout();

            Intent intent = new Intent( ProfileSettingsActivity.this, MapActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
        }
    };
}
