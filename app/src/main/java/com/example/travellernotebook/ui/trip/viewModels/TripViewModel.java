package com.example.travellernotebook.ui.trip.viewModels;

import android.os.AsyncTask;

import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.domain.Trip;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class TripViewModel extends ViewModel {
   private TripRepository mTripRepository;

    public TripViewModel(TripRepository mTripRepository) {
        this.mTripRepository = mTripRepository;
    }


    public void addTrip(Trip mTrip){
        mTripRepository.addTrip(mTrip);
    }

    public Boolean shareTrio(Trip mTrip){
       return mTripRepository.shareTrip(mTrip);
    }

    public void removeTrip(Trip mTrip){
        mTripRepository.removeTrip(mTrip);
    }

    public LiveData<List<Trip>> getAllTrips(){
        return mTripRepository.getAll();
    }

    public LiveData<List<Trip>> getSharedTrips(){
        return mTripRepository.getSharedTrips();
    }

}
