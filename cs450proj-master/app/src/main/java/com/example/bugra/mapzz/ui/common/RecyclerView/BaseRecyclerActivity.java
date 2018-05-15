package com.example.bugra.mapzz.ui.common.RecyclerView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bugra.mapzz.ui.common.BaseActivity;


public abstract class BaseRecyclerActivity extends BaseActivity {

    protected <T extends BaseRecyclerViewAdapter> void configureRecyclerView( RecyclerView recyclerView, T recyclerAdapter ) {

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        recyclerView.setAdapter( recyclerAdapter );
    }
}
