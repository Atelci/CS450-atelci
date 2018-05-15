package com.example.bugra.mapzz.ui.common.RecyclerView;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;

import com.example.bugra.mapzz.model.BaseModel;
import com.example.bugra.mapzz.ui.common.BaseViewModel;

import java.util.ArrayList;

public abstract class BaseRecyclerViewModel<Model extends BaseModel, ActionType> extends BaseViewModel<Model, ActionType> {

    protected MutableLiveData<ArrayList<Model>> mData = new MutableLiveData<>();
    public ObservableBoolean isLoading = new ObservableBoolean();

    public MutableLiveData<ArrayList<Model>> getLiveData() {
        return mData;
    }

    public abstract void fetchData();

    public void onRefresh() {
        fetchData();
    }

    public void setAsLoaded() {
        isLoading.set(false);
    }
}
