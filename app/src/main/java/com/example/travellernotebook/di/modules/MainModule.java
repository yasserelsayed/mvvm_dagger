package com.example.travellernotebook.di.modules;

import android.content.Context;

import com.example.travellernotebook.data.LocationActivityRepository;
import com.example.travellernotebook.data.UserRepository;
import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.data.preferences.UserPreference;
import com.example.travellernotebook.di.scope.MainScope;
import com.example.travellernotebook.factory.LocationActivityFactory;
import com.example.travellernotebook.factory.TripFactory;
import com.example.travellernotebook.factory.UserFactory;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
@Module
public class MainModule {


    @Provides
    @MainScope
    public TripRepository provideTripRepository(FirebaseFirestore mFirebaseFirestore,AppDatabase mAppDatabase,UserPreference mUserPreference) {
        return new TripRepository(mFirebaseFirestore,mAppDatabase,mUserPreference);
    }

    @Provides
    @MainScope
    public LocationActivityRepository provideLocationActivityRepository(FirebaseFirestore mFirebaseFirestore, AppDatabase mAppDatabase) {
        return new LocationActivityRepository(mFirebaseFirestore,mAppDatabase);
    }

    @Provides
    @MainScope
    public TripFactory provideTripViewModelsFactory(TripRepository mTripRepository) {
        return new TripFactory(mTripRepository);
    }


    @Provides
    @MainScope
    public LocationActivityFactory provideLocationActivityFactory(LocationActivityRepository mLocationActivityRepository) {
        return new LocationActivityFactory(mLocationActivityRepository);
    }

    @Provides
    @MainScope
    public UserPreference provideUserPreference(Context m) {
        return new UserPreference(m);
    }

    @Provides
    @MainScope
    public UserRepository provideAuthenticationRepository(UserPreference mUserPreference,AppDatabase mAppDatabase) {
        return new UserRepository(mUserPreference,mAppDatabase);
    }


    @Provides
    @MainScope
    public UserFactory provideProfileViewModelsFactory(UserRepository mUserRepository) {
        return new UserFactory(mUserRepository);
    }
}
