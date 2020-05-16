package com.example.travellernotebook.ui.locationActivities.viewModels;

import com.example.travellernotebook.data.LocationActivityRepository;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.domain.Quote;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ActivityViewModel extends ViewModel {
   private LocationActivityRepository mLocationActivityRepository;

    public ActivityViewModel(LocationActivityRepository mLocationActivityRepository) {
        this.mLocationActivityRepository = mLocationActivityRepository;
    }

    public void addActivity(Activity mActivity){
        mLocationActivityRepository.addActivity(mActivity);
    }

    public void removeActivity(Activity mActivity){
        mLocationActivityRepository.removeActivity(mActivity);
    }

    public LiveData<List<Activity>> getAllActivities(int locationId){
        return mLocationActivityRepository.getAllActivities(locationId);
    }

    public void addActivityQuote(Quote mQuote){
        mLocationActivityRepository.addQuote(mQuote);
    }

    public void removeActivityQuote(Quote mQuote){
        mLocationActivityRepository.removeQuote(mQuote);
    }


}
