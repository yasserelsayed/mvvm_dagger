package com.example.travellernotebook.data;
import android.os.AsyncTask;
import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.data.preferences.UserPreference;
import com.example.travellernotebook.domain.User;
import com.example.travellernotebook.util.Constants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {
    UserPreference mUserPreference;
    MutableLiveData<User> userDatasource;
    AppDatabase mAppDatabase;

    public UserRepository(UserPreference mUserPreference, AppDatabase mAppDatabase) {
        this.mUserPreference = mUserPreference;
        userDatasource = new MutableLiveData<>();
        this.mAppDatabase = mAppDatabase;
    }

    public void updateUserProfile(User mUser){
         if(validateUserInfo(mUser.getProfilePhoto()))
                 mUserPreference.setUserInfo(Constants.KeyUserPhoto,mUser.getProfilePhoto());
         else    mUserPreference.setUserInfo(Constants.KeyUserPhoto,"");
        if(validateUserInfo(mUser.getEmail()))
            mUserPreference.setUserInfo(Constants.KeyEmail,mUser.getEmail());
        else mUserPreference.setUserInfo(Constants.KeyEmail,"");
        if(validateUserInfo(mUser.getPhone()))
            mUserPreference.setUserInfo(Constants.KeyPhone,mUser.getPhone());
        else mUserPreference.setUserInfo(Constants.KeyPhone,"");
        if(validateUserInfo(mUser.getPassword()))
            mUserPreference.setUserInfo(Constants.KeyPassword,mUser.getPassword());
        else mUserPreference.setUserInfo(Constants.KeyPassword,"");
        if(validateUserInfo(mUser.getUserName()))
            mUserPreference.setUserInfo(Constants.KeyUserName,mUser.getUserName());
        else mUserPreference.setUserInfo(Constants.KeyUserName,"");
    }

    public void setUserSavings(String savings){
        mUserPreference.setUserInfo(Constants.KeySavings,savings);
    }

    public String getUserSavings(){
      return   mUserPreference.getUserInfo(Constants.KeySavings);
    }

    public void resetApp(){
        mUserPreference.setUserInfo(Constants.KeyUserPhoto,"");
        mUserPreference.setUserInfo(Constants.KeyEmail,"");
        mUserPreference.setUserInfo(Constants.KeyPhone,"");
        mUserPreference.setUserInfo(Constants.KeyPassword,"");
        mUserPreference.setUserInfo(Constants.KeyUserName,"");

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
               mAppDatabase.mediaDao().reset();
               mAppDatabase.quoteDao().reset();
               mAppDatabase.activityDao().reset();
               mAppDatabase.locationDao().reset();
               mAppDatabase.tripDao().reset();
                return null;
            }
        }.execute();
    }


    public LiveData<User> getUserProfile(){
        User mUser = new User();
        mUser.setPassword(mUserPreference.getUserInfo(Constants.KeyPassword));
        mUser.setUserName(mUserPreference.getUserInfo(Constants.KeyUserName));
        mUser.setPhone(mUserPreference.getUserInfo(Constants.KeyPhone));
        mUser.setEmail(mUserPreference.getUserInfo(Constants.KeyEmail));
        mUser.setProfilePhoto(mUserPreference.getUserInfo(Constants.KeyUserPhoto));
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
