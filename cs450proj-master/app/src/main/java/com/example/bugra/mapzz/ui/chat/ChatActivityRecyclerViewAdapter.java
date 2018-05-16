package com.example.bugra.mapzz.ui.chat;

import android.arch.lifecycle.LifecycleOwner;
import android.databinding.ViewDataBinding;

import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.model.Chat;
import com.example.bugra.mapzz.ui.common.BasicActionType;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerViewAdapter;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerViewModel;

public class ChatActivityRecyclerViewAdapter extends BaseRecyclerViewAdapter<Chat, BasicActionType> {

    public ChatActivityRecyclerViewAdapter( BaseRecyclerViewModel<Chat, BasicActionType> baseRecyclerViewModel, LifecycleOwner lifecycleOwner ) {
        super( baseRecyclerViewModel, lifecycleOwner );
    }

    @Override
    protected void setViewHolderBindings( ViewDataBinding binding, Chat model ) {
        binding.setVariable( BR.viewModel, new ChatItemViewModel( model ) );
    }

    @Override
    protected int getItemViewId() {
        return R.layout.chat_item;
    }
}
