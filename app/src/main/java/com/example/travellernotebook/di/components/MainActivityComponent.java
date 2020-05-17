package com.example.travellernotebook.di.components;

import com.example.travellernotebook.di.modules.MainModule;
import com.example.travellernotebook.di.scope.MainScope;
import com.example.travellernotebook.ui.locationActivities.ActivitiesHomeFrgment;
import com.example.travellernotebook.ui.trip.SharedTripsListFrgment;
import com.example.travellernotebook.ui.user.LoginFrgment;
import com.example.travellernotebook.ui.user.SplashFrgment;
import com.example.travellernotebook.ui.user.UserFrgment;
import com.example.travellernotebook.ui.locationActivities.ActivitiesListFrgment;
import com.example.travellernotebook.ui.locationActivities.ActivityFrgment;
import com.example.travellernotebook.ui.trip.HomeFrgment;
import com.example.travellernotebook.ui.locationActivities.LocationFrgment;
import com.example.travellernotebook.ui.locationActivities.LocationsHomeFrgment;
import com.example.travellernotebook.ui.locationActivities.LocationsListFrgment;
import com.example.travellernotebook.ui.locationActivities.PhotosGalleryFrgment;
import com.example.travellernotebook.ui.trip.TripFrgment;
import com.example.travellernotebook.ui.trip.TripsListFrgment;

import dagger.Component;

@MainScope
@Component(modules = {MainModule.class},dependencies ={AppComponent.class})
public interface MainActivityComponent {
    public void inject(LocationsListFrgment mLocationsListFrgment);
    public void inject(SharedTripsListFrgment mSharedTripsListFrgment);
    public void inject(LocationsHomeFrgment mLocationsHomeFrgment);
    public void inject(TripFrgment mTripFrgment);
    public void inject(LocationFrgment mLocationFrgment);
    public void inject(HomeFrgment mHomeFrgment);
    public void inject(TripsListFrgment mTripsListFrgment);
    public void inject(ActivitiesListFrgment mActivitiesListFrgment);
    public void inject(ActivitiesHomeFrgment mActivitiesHomeFrgment);
    public void inject(ActivityFrgment mActivityFrgment);
    public void inject(PhotosGalleryFrgment mPhotosGalleryFrgment);
    public void inject(UserFrgment mUserFrgment);
    public void inject(SplashFrgment mSplashFrgment);
    public void inject(LoginFrgment mLoginFrgment);

}
