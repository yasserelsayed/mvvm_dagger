package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Locationdb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location WHERE trip_id = :tripId")
    List<Locationdb> getAll(int tripId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Locationdb mLocation);

    @Query("SELECT * FROM location WHERE dbid = :locationId")
    Locationdb getRecord(String locationId);

    @Delete
    void delete(Locationdb mLocation);

    @Query("DELETE FROM location")
    void reset();
}
