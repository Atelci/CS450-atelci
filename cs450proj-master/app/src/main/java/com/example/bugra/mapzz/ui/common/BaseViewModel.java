package com.example.bugra.mapzz.ui.common;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bugra.mapzz.model.BaseModel;


public abstract class BaseViewModel<Model extends BaseModel, ActionType> extends ViewModel {

    protected MutableLiveData<Action<Model, ActionType>> mAction = new MutableLiveData<>();

    public MutableLiveData<Action<Model, ActionType>> getAction() {
        return this.mAction;
    }

    public void setAction( Model model, ActionType actionType ) {
        this.mAction.postValue( new Action<>( model, actionType ) );
    }
}
