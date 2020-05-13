package com.example.travellernotebook.di.components;

import com.example.travellernotebook.di.modules.MainModule;
import com.example.travellernotebook.di.scope.MainScope;
import com.example.travellernotebook.ui.profile.views.ProfileFrgment;
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

import dagger.Component;

@MainScope
@Component(modules = {MainModule.class},dependencies ={AppComponent.class})
public interface MainActivityComponent {
    public void inject(LocationsListFrgment mLocationsListFrgment);
    public void inject(LocationsHomeFrgment mLocationsHomeFrgment);
    public void inject(TripFrgment mTripFrgment);
    public void inject(LocationFrgment mLocationFrgment);
    public void inject(HomeFrgment mHomeFrgment);
    public void inject(TripsListFrgment mTripsListFrgment);
    public void inject(ActivitiesListFrgment mActivitiesListFrgment);
    public void inject(LocationActivitiesFrgment mLocationActivitiesFrgment);
    public void inject(ActivityFrgment mActivityFrgment);
    public void inject(PhotosGalleryFrgment mPhotosGalleryFrgment);
    public void inject(ProfileFrgment mProfileFrgment);
}
