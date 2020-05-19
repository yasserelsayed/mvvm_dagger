package com.example.travellernotebook.ui.user.viewModels;

import com.example.travellernotebook.data.UserRepository;
import com.example.travellernotebook.domain.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private UserRepository mUserRepository;
    public UserViewModel(UserRepository mmUserRepository) {
        this.mUserRepository = mmUserRepository;
    }
    public void updateUserProfile(User mUser){
        mUserRepository.updateUserProfile(mUser);
    }

    public LiveData<User> getUserProfile(){
        return mUserRepository.getUserProfile();
    }

    public void resetApp(){
        mUserRepository.resetApp();
    }

    public String getUserSavings(){
      return   mUserRepository.getUserSavings();
    }

    public void setUserSavings(String savings){
           mUserRepository.setUserSavings(savings);
    }
}
