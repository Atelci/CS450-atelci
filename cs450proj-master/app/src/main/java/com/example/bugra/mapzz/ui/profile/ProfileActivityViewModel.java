package com.example.bugra.mapzz.ui.profile;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.bugra.mapzz.model.User;
import com.example.bugra.mapzz.repository.UserRepository;
import com.example.bugra.mapzz.ui.common.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivityViewModel extends BaseViewModel {

    private static final String TAG = "ProfileActivityVM";

    public User user;
    public final ObservableBoolean isMyProfile = new ObservableBoolean( false );

    public void fetchUserData( User user ) {

        this.user = user;

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        if( authUser != null && user.getUserId().equals( authUser.getUid() ) ) {
            isMyProfile.set( true );
        }
        else {
            isMyProfile.set( false );
        }
    }
}
