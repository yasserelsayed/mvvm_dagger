package com.example.travellernotebook.ui.trip;

import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TripViewModelsFactory implements ViewModelProvider.Factory {

    TripRepository mTripRepository;
    public TripViewModelsFactory(TripRepository _TripRepository) {
        mTripRepository =  _TripRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(TripViewModel.class)) {
            return (T) new TripViewModel(mTripRepository);
        }else return null;
    }
}
