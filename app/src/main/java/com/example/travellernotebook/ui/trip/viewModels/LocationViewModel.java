package com.example.travellernotebook.ui.trip.viewModels;

import android.location.Location;

import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.domain.TripLocation;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
   private TripRepository mTripRepository;

    public LocationViewModel(TripRepository mTripRepository) {
        this.mTripRepository = mTripRepository;
    }

    public void removeTripLocation(TripLocation mTripLocation){
        mTripRepository.removeTripLocation(mTripLocation);
    }

    public void addTripLocation(TripLocation mTripLocation){
        mTripRepository.addLocation(mTripLocation);
    }

    public LiveData<List<TripLocation>> getAllLocations(int tripId){
        return mTripRepository.getAllLocations(tripId);
    }


}
