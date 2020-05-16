package com.example.travellernotebook.factory;


import com.example.travellernotebook.data.LocationActivityRepository;
import com.example.travellernotebook.ui.locationActivities.viewModels.ActivityViewModel;
import com.example.travellernotebook.ui.locationActivities.viewModels.LocationViewModel;
import com.example.travellernotebook.ui.locationActivities.viewModels.MediaViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LocationActivityFactory implements ViewModelProvider.Factory {

    LocationActivityRepository mLocationActivityRepository;
    public LocationActivityFactory(LocationActivityRepository _LocationActivityRepository) {
        mLocationActivityRepository =  _LocationActivityRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if(modelClass.equals(LocationViewModel.class)) {
            return (T) new LocationViewModel(mLocationActivityRepository);
        }else if(modelClass.equals(ActivityViewModel.class)) {
            return (T) new ActivityViewModel(mLocationActivityRepository);
        }else if(modelClass.equals(MediaViewModel.class)) {
            return (T) new MediaViewModel(mLocationActivityRepository);
        }else return null;
    }
}
