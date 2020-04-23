package com.example.travellernotebook.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "qoute")
public class Quote {
    @PrimaryKey(autoGenerate = true)
    public int dbid;
    @ColumnInfo(name = "qoute_text")
    public String qoute;
    @ColumnInfo(name = "activity_id")
    public int parent;
}
