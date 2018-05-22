package com.example.bugra.mapzz.ui.plant;

import android.arch.lifecycle.ViewModel;

import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.model.User;

public class PlantActivityViewModel extends ViewModel {

    public Plant plant;
    public User user = new User( "", "Nayra Keskin", "photo_url", "bio" );
}
