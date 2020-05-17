package com.example.travellernotebook.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.data.database.entities.Tripdb;
import com.example.travellernotebook.data.preferences.UserPreference;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.util.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TripRepository {

    MutableLiveData<List<Trip>> tripsData;
    MutableLiveData<List<Trip>> sharedTripData;
    Date mToday;

    private AppDatabase mAppDatabase;
    private FirebaseFirestore mFirebaseFirestore;
    private UserPreference mUserPreference;
    private SimpleDateFormat mSimpleDateFormat;
    public TripRepository(FirebaseFirestore mFirebaseFirestore, AppDatabase mAppDatabase, UserPreference mUserPreference) {
        this.mAppDatabase = mAppDatabase;
        this.mFirebaseFirestore = mFirebaseFirestore;
        tripsData = new MutableLiveData<>();
        sharedTripData = new MutableLiveData<>();
        this.mUserPreference = mUserPreference;
        this.mSimpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
        mToday = new Date();
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

    public void removeTrip(Trip mTrip){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDatabase.tripDao().delete(mTrip.getRow());
                return null;
            }
        }.execute();
    }

    public Boolean shareTrip(Trip mTrip){
        boolean valid = true;
        String username = mUserPreference.getUserInfo(Constants.KeyUserName);
        String email = mUserPreference.getUserInfo(Constants.KeyEmail);
        String phone = mUserPreference.getUserInfo(Constants.KeyPhone);

        if(username ==null || username.isEmpty() ||
                email==null || email.isEmpty() || phone==null || phone.isEmpty())
            valid = false;

        if(valid) {
            Map<String, Object> trip = new HashMap<>();
            trip.put(mTrip.getTripNameAttributeName(), mTrip.getTripName());
            trip.put(mTrip.getStartDateAttributeName(), mTrip.getStartDate());
            trip.put(mTrip.getEndDateAttributeName(), mTrip.getEndDate());
            trip.put(Constants.KeyUserName,username);
            trip.put(Constants.KeyEmail, email);
            trip.put(Constants.KeyPhone,phone);

                    mFirebaseFirestore.collection("trips")
                            .add(trip)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    new AsyncTask<Void,Void,Void>(){
                                        @Override
                                        protected Void doInBackground(Void... voids) {
                                            mAppDatabase.tripDao().updateTipBackendid(mTrip.getId(),documentReference.getId());
                                            getAll();
                                            return null;
                                        }
                                    }.execute();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("", "Error adding document", e);
                                }
                            });

        }
        return valid;
    }

    public MutableLiveData<List<Trip>> getSharedTrips(){
            mFirebaseFirestore.collection("trips")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<Trip> lstTrips = new ArrayList<>();
                            Trip mTrip;
                            for (int i = 0; i <queryDocumentSnapshots.getDocuments().size() ; i++) {
                                mTrip = new Trip();
                                DocumentSnapshot mDocumentSnapshot = queryDocumentSnapshots.getDocuments().get(i);
                                mTrip.setTripName(mDocumentSnapshot.get(mTrip.getTripNameAttributeName()).toString());
                                try {
                                    Date sDate = mSimpleDateFormat.parse(mDocumentSnapshot.get(mTrip.getStartDateAttributeName()).toString());
                                    mTrip.setStartDate(sDate);
                                    Date eDate = mSimpleDateFormat.parse(mDocumentSnapshot.get(mTrip.getEndDateAttributeName()).toString());
                                    mTrip.setEndDate(eDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                mTrip.setUserName(mDocumentSnapshot.get(Constants.KeyUserName).toString());
                                mTrip.setEmail(mDocumentSnapshot.get(Constants.KeyEmail).toString());
                                mTrip.setPhone(mDocumentSnapshot.get(Constants.KeyPhone).toString());
                                if (mTrip.getStartDateDate()!=null && mToday.compareTo(mTrip.getStartDateDate()) > 0)
                                lstTrips.add(mTrip);
                            }
                            sharedTripData.setValue(lstTrips);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error adding document", e);
                        }
                    });


        return sharedTripData;
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
                tripsData.setValue(lstTrips);
            }
        }.execute();
        return tripsData;
    }
}
