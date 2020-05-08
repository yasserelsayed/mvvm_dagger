package com.example.travellernotebook.ui.trip.viewModels;

import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.domain.Activity;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ActivityViewModel extends ViewModel {
   private TripRepository mTripRepository;

    public ActivityViewModel(TripRepository mTripRepository) {
        this.mTripRepository = mTripRepository;
    }

    public void addActivity(Activity mActivity){
        mTripRepository.addActivity(mActivity);
    }

    public void removeActivity(Activity mActivity){
        mTripRepository.removeActivity(mActivity);
    }

    public LiveData<List<Activity>> getAllActivities(int locationId){
        return mTripRepository.getAllActivities(locationId);
    }


}
