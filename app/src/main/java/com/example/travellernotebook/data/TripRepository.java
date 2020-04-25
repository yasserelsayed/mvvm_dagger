package com.example.travellernotebook.data;

import android.os.AsyncTask;
import android.widget.LinearLayout;

import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.data.database.entities.Tripdb;
import com.example.travellernotebook.domain.Trip;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TripRepository {

    MutableLiveData<List<Trip>> Datasource;

    private AppDatabase mAppDatabase;
    private FirebaseFirestore mFirebaseFirestore;
    public TripRepository(FirebaseFirestore mFirebaseFirestore, AppDatabase mAppDatabase) {
        this.mAppDatabase = mAppDatabase;
        this.mFirebaseFirestore = mFirebaseFirestore;
        Datasource = new MutableLiveData<>();
    }

    public void addTrip(Trip mTrip){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.tripDao().insert(mTrip.getRow());
                return null;
            }
        }.execute();
    }

    public LiveData<List<Trip>> getAll(){
        List<Trip> lstTrips = new ArrayList<>();
        new AsyncTask<Void,Void,List<Tripdb>>(){
            @Override
            protected List<Tripdb> doInBackground(Void... voids) {
                return  mAppDatabase.tripDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Tripdb> trips) {
                super.onPostExecute(trips);
                for(Tripdb mTripdb :trips)
                    lstTrips.add(new Trip(mTripdb));
                Datasource.setValue(lstTrips);
            }
        }.execute();

        return Datasource;
    }
}
