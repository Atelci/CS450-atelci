package com.example.bugra.mapzz.ui.profile;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.model.User;
import com.example.bugra.mapzz.ui.common.Action;
import com.example.bugra.mapzz.ui.common.BasicActionType;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerActivity;
import com.example.bugra.mapzz.ui.plant.PlantActivity;
import com.example.bugra.mapzz.ui.profile_settings.ProfileSettingsActivity;

public class ProfileActivity extends BaseRecyclerActivity {

    private ProfileActivityViewModel viewModel;
    private PlantRecyclerViewAdapter recyclerViewAdapter;
    private PlantRecyclerViewModel recyclerViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        viewModel = ViewModelProviders.of( this ).get( ProfileActivityViewModel.class );
        mViewDataBinding.setVariable( BR.viewModel, viewModel );

        User user = (User) getIntent().getExtras().getSerializable( "user" );
        viewModel.fetchUserData( user );

        recyclerViewModel = ViewModelProviders.of( this ).get( PlantRecyclerViewModel.class );
        recyclerViewAdapter = new PlantRecyclerViewAdapter( recyclerViewModel, this );
        configureRecyclerView( findViewById( R.id.plants_recyclerview ), recyclerViewAdapter );

        recyclerViewModel.fetchData( user.getUserId() );

        recyclerViewModel.getAction().observe( this, new Observer<Action<Plant, BasicActionType>>() {
            @Override
            public void onChanged( @Nullable Action<Plant, BasicActionType> action ) {

                if( action == null ) return;

                switch( action.getActionType() ) {
                    case RECYCLER_ITEM_CLICK:
                        Intent intent = new Intent( ProfileActivity.this, PlantActivity.class );

                        Bundle bundle = new Bundle();
                        bundle.putSerializable( "plant", action.getModel() );
                        intent.putExtras( bundle );

                        startActivity( intent );
                        return;
                }
            }
        } );

        //  Set listeners
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