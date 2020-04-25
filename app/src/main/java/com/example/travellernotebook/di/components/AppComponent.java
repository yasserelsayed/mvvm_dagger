package com.example.travellernotebook.di.components;

import android.content.Context;

import com.example.travellernotebook.di.modules.AppModule;
import com.example.travellernotebook.ui.trip.views.TripFrgment;
import com.example.travellernotebook.ui.trip.views.TripsListFrgment;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    public void inject(TripFrgment mTripFrgment);
    public void inject(TripsListFrgment mTripsListFrgment);
}
