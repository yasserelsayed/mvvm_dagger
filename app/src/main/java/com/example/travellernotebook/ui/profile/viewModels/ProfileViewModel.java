package com.example.travellernotebook.ui.profile.viewModels;

import com.example.travellernotebook.data.AuthenticationRepository;
import com.example.travellernotebook.domain.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private AuthenticationRepository mAuthenticationRepository;
    public ProfileViewModel(AuthenticationRepository mmAuthenticationRepository) {
        this.mAuthenticationRepository = mmAuthenticationRepository;
    }
    public void updateUserProfile(User mUser){
        mAuthenticationRepository.updateUserProfile(mUser);
    }

    public LiveData<User> getUserProfile(){
        return mAuthenticationRepository.getUserProfile();
    }
}
