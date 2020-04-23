package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Trip;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trip mTrip);

    @Query("SELECT * FROM trip WHERE dbid = :tripId")
    Trip getRecord(String tripId);

    @Delete
    void delete(Trip mTrip);
}
