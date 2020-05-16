package com.example.travellernotebook.ui.locationActivities.viewModels;

import com.example.travellernotebook.data.LocationActivityRepository;
import com.example.travellernotebook.domain.Location;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
   private LocationActivityRepository mLocationActivityRepository;

    public LocationViewModel(LocationActivityRepository mLocationActivityRepository) {
        this.mLocationActivityRepository = mLocationActivityRepository;
    }

    public void removeTripLocation(Location mLocation){
        mLocationActivityRepository.removeLocation(mLocation);
    }

    public void addTripLocation(Location mLocation){
        mLocationActivityRepository.addLocation(mLocation);
    }

    public LiveData<List<Location>> getAllLocations(int tripId){
        return mLocationActivityRepository.getAllLocations(tripId);
    }


}
