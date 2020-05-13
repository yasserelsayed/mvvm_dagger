package com.example.travellernotebook.ui.profile;

import com.example.travellernotebook.data.AuthenticationRepository;
import com.example.travellernotebook.ui.profile.viewModels.ProfileViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileViewModelsFactory implements ViewModelProvider.Factory {

    AuthenticationRepository mAuthenticationRepository;
    public ProfileViewModelsFactory(AuthenticationRepository _AuthenticationRepository) {
        mAuthenticationRepository =  _AuthenticationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(mAuthenticationRepository);
        }else return null;
    }
}
