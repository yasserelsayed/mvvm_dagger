package com.example.travellernotebook.domain;

import com.example.travellernotebook.data.database.entities.Tripdb;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip implements Serializable {

    SimpleDateFormat mSimpleDateFormat;
    Date mDate;
    Date mToday;
    public Trip(Tripdb mTripdb){
        this.id = mTripdb.dbid;
        this.backendID = mTripdb.backendID;
        this.tripName = mTripdb.tripName;
        this.latitude = Double.parseDouble(mTripdb.latitude);
        this.longitude = Double.parseDouble(mTripdb.longitude);
        try {
            this.mSimpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
            this.startDate = mSimpleDateFormat.parse(mTripdb.startDate);
            this.endDate = mSimpleDateFormat.parse(mTripdb.endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.budget = Double.parseDouble(mTripdb.budget);
        this.mainPhoto = mTripdb.mainPhoto;

        mToday = new Date();
    }

    public Trip(){
        mSimpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
        mDate = new Date();
        startDate = mDate;
        endDate = mDate;
        budget = 0.0;
    }


    public Tripdb getRow()
    {
        Tripdb mTripdb =  new Tripdb();
        mTripdb.dbid = this.id;
        mTripdb.tripName = this.tripName;
        mTripdb.latitude = this.latitude.toString();
        mTripdb.longitude = this.longitude.toString();
        mTripdb.startDate = this.getStartDate();
        mTripdb.endDate = this.getEndDate();
        mTripdb.budget = this.budget.toString();
        mTripdb.mainPhoto = this.mainPhoto;

        return mTripdb;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    int id;
    String backendID;
    String tripName;
    Double latitude;
    Double longitude;
    Date startDate;
    Date endDate;
    Double budget;
    String locationName;
    String mainPhoto;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public int getId() {
        return id;
    }

    public String getBackendID() {
        return backendID;
    }

    public String getTripName() {
        return tripName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getStartDate() {
        return mSimpleDateFormat.format(startDate);
    }

    public String getEndDate() {
        return mSimpleDateFormat.format(endDate);
    }

    public Date getStartDateDate() {
        return startDate;
    }

    public Date getEndDateDate() {
        return endDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBackendID(String backendID) {
        this.backendID = backendID;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public boolean isUpcoming(){
        if (startDate!=null && mToday.compareTo(startDate) > 0)
            return false;
        return true;
    }

    public Boolean isDatesValid(){
        if(startDate==null || endDate==null)
            return false;
        return startDate.before(endDate);
    }
}
