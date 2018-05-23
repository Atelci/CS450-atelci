package com.example.bugra.mapzz.ui.plant;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.model.User;
import com.example.bugra.mapzz.repository.UserRepository;

public class PlantActivityViewModel extends ViewModel {

    public Plant plant;
    public final ObservableField<User> user = new ObservableField<>();
    private final UserRepository userRepository = new UserRepository();

    public void fetchPlantData( Plant plant ) {

        this.plant = plant;

        userRepository.getUser( user, plant.getOwnerId() );
    }
}
