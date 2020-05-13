package com.example.travellernotebook.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {

    public String getUserInfo(Context mContext,String key){
       SharedPreferences mPreferences = mContext.getSharedPreferences(key,0);
       return mPreferences.getString(key,"");
    }

    public void  setUserInfo(Context mContext,String key,String value)
    {
        SharedPreferences mPreferences = mContext.getSharedPreferences(key,0);
        SharedPreferences.Editor mEdit = mPreferences.edit();
        mEdit.putString(key,value);
        mEdit.commit();
    }
}
