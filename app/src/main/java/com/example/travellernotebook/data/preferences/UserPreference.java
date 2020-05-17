package com.example.travellernotebook.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
     Context mContext;

    public UserPreference(Context mContext) {
        this.mContext = mContext;
    }

    public String getUserInfo(String key){
       SharedPreferences mPreferences = mContext.getSharedPreferences(key,0);
       return mPreferences.getString(key,"");
    }

    public void  setUserInfo(String key,String value)
    {
        SharedPreferences mPreferences = mContext.getSharedPreferences(key,0);
        SharedPreferences.Editor mEdit = mPreferences.edit();
        mEdit.putString(key,value);
        mEdit.commit();
    }
}
