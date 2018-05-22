package com.example.bugra.mapzz.ui.profile_settings;

import com.example.bugra.mapzz.model.User;
import com.example.bugra.mapzz.ui.common.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileSettingsActivityViewModel extends BaseViewModel {

    public final User user = new User( FirebaseAuth.getInstance().getCurrentUser() );

    public void logout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }
}
