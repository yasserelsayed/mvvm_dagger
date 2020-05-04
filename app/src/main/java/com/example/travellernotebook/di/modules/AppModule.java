package com.example.travellernotebook.di.modules;

import android.app.Application;
import android.content.Context;

import com.example.travellernotebook.data.TripRepository;
import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }
    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public FirebaseFirestore provideFirestoreDatabase() {
        return FirebaseFirestore.getInstance();
    }


    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(Context mContext) {
       return Room.databaseBuilder(mContext,AppDatabase.class, "database-notebook")
               .fallbackToDestructiveMigration()
               .build();
    }

    @Provides
    @Singleton
    public TripRepository provideTripRepository(FirebaseFirestore mFirebaseFirestore,AppDatabase mAppDatabase) {
        return new TripRepository(mFirebaseFirestore,mAppDatabase);
    }


    @Provides
    @Singleton
    public TripViewModelsFactory provideTripViewModelsFactory(TripRepository mTripRepository) {
        return new TripViewModelsFactory(mTripRepository);
    }
}
