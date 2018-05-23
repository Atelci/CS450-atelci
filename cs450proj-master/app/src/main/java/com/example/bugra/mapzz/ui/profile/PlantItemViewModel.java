package com.example.bugra.mapzz.ui.profile;

import android.arch.lifecycle.ViewModel;

import com.example.bugra.mapzz.model.Plant;

public class PlantItemViewModel extends ViewModel {

    public final Plant plant;

    public PlantItemViewModel( Plant plant ) {
        this.plant = plant;
    }
}
