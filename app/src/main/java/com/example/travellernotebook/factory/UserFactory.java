package com.example.travellernotebook.factory;

import com.example.travellernotebook.data.UserRepository;
import com.example.travellernotebook.ui.user.viewModels.UserViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserFactory implements ViewModelProvider.Factory {

    UserRepository mUserRepository;
    public UserFactory(UserRepository _UserRepository) {
        mUserRepository = _UserRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(UserViewModel.class)) {
            return (T) new UserViewModel(mUserRepository);
        }else return null;
    }
}
