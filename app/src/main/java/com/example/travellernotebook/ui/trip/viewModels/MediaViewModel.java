package com.example.travellernotebook.ui.trip.viewModels;

import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.domain.Media;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MediaViewModel extends ViewModel {
    private TripRepository mTripRepository;

    public MediaViewModel(TripRepository mTripRepository) {
        this.mTripRepository = mTripRepository;
    }

    public void addMediaContent(Media mMedia){
        mTripRepository.addMediaContent(mMedia);
    }

    public void removeMediaContent(Media mMedia){
        mTripRepository.removeMediaContent(mMedia);
    }

    public LiveData<List<Media>> getAllMediaContents(int locationId){
        return mTripRepository.getAllMediaContents(locationId);
    }

}
