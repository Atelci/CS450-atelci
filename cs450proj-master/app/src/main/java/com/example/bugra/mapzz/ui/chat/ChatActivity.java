package com.example.bugra.mapzz.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerActivity;

public class ChatActivity extends BaseRecyclerActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.chat_activity;
    }

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        ChatActivityRecyclerViewModel recyclerVM = new ChatActivityRecyclerViewModel();

        ChatActivityRecyclerViewAdapter recyclerAdapter = new ChatActivityRecyclerViewAdapter( recyclerVM, this );

        configureRecyclerView( findViewById( R.id.chat_recycler_view ), recyclerAdapter );

        recyclerVM.fetchData( "1" );    //  TODO: this should be firebase auth user id
    }
}
