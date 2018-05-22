package com.example.bugra.mapzz.ui.profile;


import android.databinding.ObservableField;
import android.util.Log;

import com.example.bugra.mapzz.model.User;
import com.example.bugra.mapzz.repository.UserRepository;
import com.example.bugra.mapzz.ui.common.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivityViewModel extends BaseViewModel {

    private static final String TAG = "ProfileActivityVM";

    public ObservableField<User> user = new ObservableField<>();
    private final UserRepository userRepository = new UserRepository();

    public void fetchUser( String userId ) {

        if( userId.length() == 0 ) {
            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

            if( authUser == null ) {
                //  TODO: Show error and finish activity
                Log.d( TAG, "fetchUser: no auth user found" );
                return;
            }

            user.set( new User( authUser ) );
        }
        else {
            userRepository.getUser( user, userId );
        }
    }
}
