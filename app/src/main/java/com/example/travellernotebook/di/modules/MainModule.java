package com.example.travellernotebook.di.modules;

import android.content.Context;

import com.example.travellernotebook.data.AuthenticationRepository;
import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.data.preferences.UserPreference;
import com.example.travellernotebook.di.scope.MainScope;
import com.example.travellernotebook.ui.profile.ProfileViewModelsFactory;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
@Module
public class MainModule {


    @Provides
    @MainScope
    public TripRepository provideTripRepository(FirebaseFirestore mFirebaseFirestore,AppDatabase mAppDatabase) {
        return new TripRepository(mFirebaseFirestore,mAppDatabase);
    }


    @Provides
    @MainScope
    public TripViewModelsFactory provideTripViewModelsFactory(TripRepository mTripRepository) {
        return new TripViewModelsFactory(mTripRepository);
    }

    @Provides
    @MainScope
    public UserPreference provideUserPreference() {
        return new UserPreference();
    }

    @Provides
    @MainScope
    public AuthenticationRepository provideAuthenticationRepository(Context mContext, UserPreference mUserPreference) {
        return new AuthenticationRepository(mContext,mUserPreference);
    }


    @Provides
    @MainScope
    public ProfileViewModelsFactory provideProfileViewModelsFactory(AuthenticationRepository mAuthenticationRepository) {
        return new ProfileViewModelsFactory(mAuthenticationRepository);
    }
}
