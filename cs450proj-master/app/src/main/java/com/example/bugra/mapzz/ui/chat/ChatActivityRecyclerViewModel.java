package com.example.bugra.mapzz.ui.chat;

import com.example.bugra.mapzz.model.Chat;
import com.example.bugra.mapzz.repository.ChatRepository;
import com.example.bugra.mapzz.ui.common.BasicActionType;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerViewModel;

public class ChatActivityRecyclerViewModel extends BaseRecyclerViewModel<Chat, BasicActionType> {

    private ChatRepository repository = new ChatRepository();

    public void fetchData( String userId ) {
        repository.getChats( mData, userId );
    }

    @Override
    public void fetchData() {}
}
