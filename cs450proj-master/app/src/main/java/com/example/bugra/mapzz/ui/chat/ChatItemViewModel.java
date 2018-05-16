package com.example.bugra.mapzz.ui.chat;

import android.arch.lifecycle.ViewModel;

import com.example.bugra.mapzz.model.Chat;

public class ChatItemViewModel extends ViewModel {

    public Chat chat;

    public ChatItemViewModel( Chat chat ) {
        this.chat = chat;
    }
}
