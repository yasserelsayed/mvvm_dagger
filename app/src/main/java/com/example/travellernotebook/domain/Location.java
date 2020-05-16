package com.example.travellernotebook.domain;

import com.example.travellernotebook.data.database.entities.Locationdb;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Location implements Serializable {
    int id;
    String locationName;
    Double latitude;
    Double longitude;
    Date startDate;
    Double budget;
    String locationAddress;
    String mainPhoto;


    SimpleDateFormat mSimpleDateFormat;
    Date mDate;
    Date mToday;
    public Location(Locationdb mLocationdb){
        this.id = mLocationdb.dbid;
        this.locationName = mLocationdb.locationName;
        this.latitude = Double.parseDouble(mLocationdb.latitude);
        this.longitude = Double.parseDouble(mLocationdb.longitude);
        try {
            this.mSimpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
            this.startDate = mSimpleDateFormat.parse(mLocationdb.startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.budget = Double.parseDouble(mLocationdb.budget);
        this.mainPhoto = mLocationdb.mainPhoto;

        mToday = new Date();
    }

    public Location(){
        mSimpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
        mDate = new Date();
        startDate = mDate;
        budget = 0.0;
    }

    public Locationdb getRow()
    {
        Locationdb mLocationdb=  new Locationdb();
        mLocationdb.dbid = this.id;
        mLocationdb.locationName = this.locationName;
        mLocationdb.latitude = this.latitude.toString();
        mLocationdb.longitude = this.longitude.toString();
        mLocationdb.startDate = this.getStartDate();
        mLocationdb.budget = this.budget.toString();
        mLocationdb.mainPhoto = this.mainPhoto;
        mLocationdb.parent = this.parentId;
        return mLocationdb;
    }

    int parentId;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getLocationName() {
        return locationName;
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

    public Double getBudget() {
        return budget;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public int getParentId() {
        return parentId;
    }
}