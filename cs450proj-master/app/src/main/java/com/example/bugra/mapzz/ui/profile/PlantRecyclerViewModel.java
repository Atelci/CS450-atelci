package com.example.bugra.mapzz.ui.profile;

import android.util.Log;

import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.repository.PlantRepository;
import com.example.bugra.mapzz.ui.common.BasicActionType;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerViewModel;

public class PlantRecyclerViewModel extends BaseRecyclerViewModel<Plant, BasicActionType> {

    private PlantRepository repository = new PlantRepository();

    @Override
    public void fetchData() {

    }

    public void fetchData( String userId ) {
        repository.getPlantsOfUser( mData, userId );
    }
}
