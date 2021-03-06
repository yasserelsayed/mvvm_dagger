package com.example.travellernotebook.data;

import android.os.AsyncTask;

import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.data.database.entities.Activitydb;
import com.example.travellernotebook.data.database.entities.Locationdb;
import com.example.travellernotebook.data.database.entities.Mediadb;
import com.example.travellernotebook.data.database.entities.Quotedb;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.domain.Media;
import com.example.travellernotebook.domain.Quote;
import com.example.travellernotebook.domain.Location;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LocationActivityRepository {
    MutableLiveData<List<Location>> LocationsDatasource;
    MutableLiveData<List<Activity>> ActivitiesDatasource;
    MutableLiveData<List<Media>> MediaContentsDatasource;

    private AppDatabase mAppDatabase;
    private FirebaseFirestore mFirebaseFirestore;
    public LocationActivityRepository(FirebaseFirestore mFirebaseFirestore, AppDatabase mAppDatabase) {
        this.mAppDatabase = mAppDatabase;
        this.mFirebaseFirestore = mFirebaseFirestore;
        LocationsDatasource = new MutableLiveData<>();
        ActivitiesDatasource = new MutableLiveData<>();
        MediaContentsDatasource = new MutableLiveData<>();
    }



    public void removeLocation(Location mLocation){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.locationDao().delete(mLocation.getRow());
                return null;
            }
        }.execute();
    }


    public void removeActivity(Activity mActivity){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.activityDao().delete(mActivity.getRow());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getAllActivities(mActivity.getParent());
            }
        }.execute();
    }

    public void addLocation(Location mLocation){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.locationDao().insert(mLocation.getRow());
                return null;
            }
        }.execute();
    }

    public LiveData<List<Media>> getAllMediaContents(int locationId){
        List<Media> lstMediaContents = new ArrayList<>();
        new AsyncTask<Void,Void,List<Mediadb>>(){
            @Override
            protected List<Mediadb> doInBackground(Void... voids) {
                return  mAppDatabase.mediaDao().getAll(locationId);
            }

            @Override
            protected void onPostExecute(List<Mediadb> mediaContents) {
                super.onPostExecute(mediaContents);
                for(Mediadb mMediadb :mediaContents)
                    lstMediaContents.add(new Media(mMediadb));
                MediaContentsDatasource.setValue(lstMediaContents);
            }
        }.execute();

        return MediaContentsDatasource;
    }

    public LiveData<List<Location>> getAllLocations(int tripId){
        List<Location> lstLocations = new ArrayList<>();
        new AsyncTask<Void,Void,List<Locationdb>>(){
            @Override
            protected List<Locationdb> doInBackground(Void... voids) {
                return  mAppDatabase.locationDao().getAll(tripId);
            }

            @Override
            protected void onPostExecute(List<Locationdb> locations) {
                super.onPostExecute(locations);
                for(Locationdb mLocationdb :locations)
                    lstLocations.add(new Location(mLocationdb));
                LocationsDatasource.setValue(lstLocations);
            }
        }.execute();

        return LocationsDatasource;
    }


    public void addActivity(Activity mActivity){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.activityDao().insert(mActivity.getRow());
                return null;
            }
        }.execute();
    }

    public LiveData<List<Activity>> getAllActivities(int locationId){
        new AsyncTask<Void,Void,List<Activity>>(){
            @Override
            protected List<Activity> doInBackground(Void... voids) {
                List<Activity> lstActivity = new ArrayList<>();
                List<Activitydb> lstActivitydbs =  mAppDatabase.activityDao().getAll(locationId);
                for (Activitydb mActivitydb : lstActivitydbs) {
                    Activity mActivity =   new Activity(mActivitydb);
                    mActivity.setParent(locationId);
                    lstActivity.add(mActivity);
                }
                return   getAllQuotes(lstActivity);
            }

            @Override
            protected void onPostExecute(List<Activity> activities) {
                super.onPostExecute(activities);
                ActivitiesDatasource.setValue(activities);
            }
        }.execute();

        return ActivitiesDatasource;
    }

    public void addQuote(Quote mQuote){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.quoteDao().insert(mQuote.getRow());
                return null;
            }
        }.execute();
        getAllActivities(mQuote.getLocation());
    }

    public void removeQuote(Quote mQuote){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.quoteDao().delete(mQuote.getRow());
                return null;
            }
        }.execute();
    }

    public void addMediaContent(Media mMedia){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.mediaDao().insert(mMedia.getRow());
                return null;
            }
        }.execute();
        getAllMediaContents(mMedia.getParent());
    }

    public void removeMediaContent(Media mMedia){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.mediaDao().delete(mMedia.getRow());
                return null;
            }
        }.execute();
        getAllMediaContents(mMedia.getParent());
    }


    public List<Activity> getAllQuotes(List<Activity> lstActivites){
        List<Quote> lstQuotes;
        for (Activity mActivity :lstActivites) {
            lstQuotes = new ArrayList<>();
            List<Quotedb> lstQuotesdb =  mAppDatabase.quoteDao().getAll(mActivity.getId());
            for(Quotedb mQuotedb :lstQuotesdb) {
                Quote mQuote =  new Quote(mQuotedb);
                mQuote.setLocation(mActivity.getParent());
                lstQuotes.add(new Quote(mQuotedb));
            }
            mActivity.setLstQuotes(lstQuotes);
        }
        return lstActivites;
    }
}
