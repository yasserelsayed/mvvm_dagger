package com.example.travellernotebook.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip")
public class Tripdb {
    @PrimaryKey(autoGenerate = true)
    public int dbid;
    @ColumnInfo(name = "backend_id")
    public String backendID;
    @ColumnInfo(name = "trip_name")
    public String tripName;
    @ColumnInfo(name = "latitude")
    public String latitude;
    @ColumnInfo(name = "longitude")
    public String longitude;
    @ColumnInfo(name = "start_date")
    public String startDate;
    @ColumnInfo(name = "end_date")
    public String endDate;
    @ColumnInfo(name = "budget")
    public String budget;
}
