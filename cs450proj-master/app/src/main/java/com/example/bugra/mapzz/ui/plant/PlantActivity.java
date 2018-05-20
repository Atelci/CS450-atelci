package com.example.bugra.mapzz.ui.plant;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.ui.profile.ProfileActivity;
import com.example.bugra.mapzz.ui.common.BaseActivity;

public class PlantActivity extends BaseActivity {

    private PlantActivityViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.plant_activity;
    }

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        viewModel = ViewModelProviders.of( this ).get( PlantActivityViewModel.class );

        mViewDataBinding.setVariable( BR.viewModel, viewModel );


        viewModel.plant = (Plant) getIntent().getExtras().getSerializable( "plant" );


        findViewById( R.id.plant_profile_card ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent( PlantActivity.this, ProfileActivity.class );
                startActivity( intent );
            }
        } );
    }
}
