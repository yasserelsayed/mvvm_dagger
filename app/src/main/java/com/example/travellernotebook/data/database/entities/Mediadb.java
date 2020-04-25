package com.example.travellernotebook.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "media")
public class Mediadb {
    @PrimaryKey(autoGenerate = true)
    public int dbid;
    @ColumnInfo(name = "path")
    public String path;
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;
    @ColumnInfo(name = "location_id")
    public int parent;
}

