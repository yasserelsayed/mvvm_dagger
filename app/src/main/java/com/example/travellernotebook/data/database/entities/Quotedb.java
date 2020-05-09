package com.example.travellernotebook.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quote")
public class Quotedb {
    @PrimaryKey(autoGenerate = true)
    public int dbid;
    @ColumnInfo(name = "quote_text")
    public String quote;
    @ColumnInfo(name = "activity_id")
    public int parent;
}
