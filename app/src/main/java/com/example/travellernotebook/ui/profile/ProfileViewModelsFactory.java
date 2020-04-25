package com.example.travellernotebook.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileViewModelsFactory implements ViewModelProvider.Factory {

//    public ProfileViewModelsFactory(MoviesRepository _MoviesRepository) {
//        mMoviesRepository =  _MoviesRepository;
//    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.equals(MoviesListViewModel.class)) {
//            return (T) new MoviesListViewModel(mMoviesRepository);
//        }else
            return null;
    }
}
