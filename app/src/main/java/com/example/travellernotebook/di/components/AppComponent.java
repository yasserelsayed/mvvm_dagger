package com.example.travellernotebook.di.components;
import com.example.travellernotebook.di.modules.AppModule;
import com.example.travellernotebook.ui.trip.views.HomeFrgment;
import com.example.travellernotebook.ui.trip.views.LocationsListFrgment;
import com.example.travellernotebook.ui.trip.views.TripFrgment;
import com.example.travellernotebook.ui.trip.views.TripsListFrgment;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    public void inject(LocationsListFrgment mLocationsListFrgment);
    public void inject(TripFrgment mTripFrgment);
    public void inject(HomeFrgment mHomeFrgment);
    public void inject(TripsListFrgment mTripsListFrgment);
}
