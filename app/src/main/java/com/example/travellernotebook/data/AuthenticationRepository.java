package com.example.travellernotebook.data;

import android.content.Context;

import com.example.travellernotebook.data.preferences.UserPreference;
import com.example.travellernotebook.domain.User;
import com.example.travellernotebook.util.Constants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthenticationRepository {
    UserPreference mUserPreference;
    Context mContext;

    MutableLiveData<User> userDatasource;

    public AuthenticationRepository(Context mContext, UserPreference mUserPreference) {
        this.mUserPreference = mUserPreference;
        this.mContext = mContext;
        userDatasource = new MutableLiveData<>();
    }

    public void updateUserProfile(User mUser){
         if(validateUserInfo(mUser.getProfilePhoto()))
                 mUserPreference.setUserInfo(mContext, Constants.KeyUserPhoto,mUser.getProfilePhoto());
         else    mUserPreference.setUserInfo(mContext, Constants.KeyUserPhoto,"");
        if(validateUserInfo(mUser.getEmail()))
            mUserPreference.setUserInfo(mContext, Constants.KeyEmail,mUser.getEmail());
        else mUserPreference.setUserInfo(mContext, Constants.KeyEmail,"");
        if(validateUserInfo(mUser.getPhone()))
            mUserPreference.setUserInfo(mContext, Constants.KeyPhone,mUser.getPhone());
        else mUserPreference.setUserInfo(mContext, Constants.KeyPhone,"");
        if(validateUserInfo(mUser.getUserName()))
            mUserPreference.setUserInfo(mContext, Constants.KeyPassword,mUser.getPassword());
        else mUserPreference.setUserInfo(mContext, Constants.KeyPassword,"");
        if(validateUserInfo(mUser.getUserName()))
            mUserPreference.setUserInfo(mContext, Constants.KeyUserName,mUser.getUserName());
        else mUserPreference.setUserInfo(mContext, Constants.KeyUserName,"");
    }


    public LiveData<User> getUserProfile(){
        User mUser = new User();
        mUser.setUserName(mUserPreference.getUserInfo(mContext,Constants.KeyUserName));
        mUser.setPhone(mUserPreference.getUserInfo(mContext,Constants.KeyPhone));
        mUser.setEmail(mUserPreference.getUserInfo(mContext,Constants.KeyEmail));
        mUser.setProfilePhoto(mUserPreference.getUserInfo(mContext,Constants.KeyUserPhoto));
        userDatasource.setValue(mUser);
        return userDatasource;
    }


    private boolean validateUserInfo(String s)
    {
        if(s==null || s.isEmpty())
            return false;
        else return true;
    }
}
