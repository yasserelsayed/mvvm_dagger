package com.example.travellernotebook.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "activity")
public class Activity {
    @PrimaryKey(autoGenerate = true)
    public int dbid;
    @ColumnInfo(name = "activity_name")
    public String activityName;
    @ColumnInfo(name = "location_id")
    public int parent;
}
