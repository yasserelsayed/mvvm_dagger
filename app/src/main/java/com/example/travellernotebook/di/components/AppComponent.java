package com.example.travellernotebook.di.components;
import android.content.Context;

import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.di.modules.AppModule;
import com.example.travellernotebook.ui.trip.views.ActivitiesListFrgment;
import com.example.travellernotebook.ui.trip.views.ActivityFrgment;
import com.example.travellernotebook.ui.trip.views.HomeFrgment;
import com.example.travellernotebook.ui.trip.views.LocationActivitiesFrgment;
import com.example.travellernotebook.ui.trip.views.LocationFrgment;
import com.example.travellernotebook.ui.trip.views.LocationsHomeFrgment;
import com.example.travellernotebook.ui.trip.views.LocationsListFrgment;
import com.example.travellernotebook.ui.trip.views.PhotosGalleryFrgment;
import com.example.travellernotebook.ui.trip.views.TripFrgment;
import com.example.travellernotebook.ui.trip.views.TripsListFrgment;
import com.google.firebase.firestore.FirebaseFirestore;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    public Context provideContext();
    public FirebaseFirestore provideFirestoreDatabase();
    public AppDatabase provideAppDatabase();
}
