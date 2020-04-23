package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Location;
import com.example.travellernotebook.data.database.entities.Trip;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    List<Location> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location mLocation);

    @Query("SELECT * FROM location WHERE dbid = :locationId")
    Location getRecord(String locationId);

    @Delete
    void delete(Location mLocation);
}
