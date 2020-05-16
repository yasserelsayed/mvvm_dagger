package com.example.travellernotebook.ui.locationActivities.viewModels;

import com.example.travellernotebook.data.LocationActivityRepository;
import com.example.travellernotebook.domain.Media;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MediaViewModel extends ViewModel {
    private LocationActivityRepository mLocationActivityRepository;

    public MediaViewModel(LocationActivityRepository mLocationActivityRepository) {
        this.mLocationActivityRepository = mLocationActivityRepository;
    }

    public void addMediaContent(Media mMedia){
        mLocationActivityRepository.addMediaContent(mMedia);
    }

    public void removeMediaContent(Media mMedia){
        mLocationActivityRepository.removeMediaContent(mMedia);
    }

    public LiveData<List<Media>> getAllMediaContents(int locationId){
        return mLocationActivityRepository.getAllMediaContents(locationId);
    }

}
