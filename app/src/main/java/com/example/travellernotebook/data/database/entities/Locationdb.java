package com.example.travellernotebook.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location")
public class Locationdb {
    @PrimaryKey(autoGenerate = true)
    public int dbid;
    @ColumnInfo(name = "location_name")
    public String locationName;
    @ColumnInfo(name = "latitude")
    public String latitude;
    @ColumnInfo(name = "longitude")
    public String longitude;
    @ColumnInfo(name = "start_date")
    public String startDate;
    @ColumnInfo(name = "budget")
    public String budget;
    @ColumnInfo(name = "main_photo")
    public String mainPhoto;

    @ColumnInfo(name = "trip_id")
    public int parent;
}

