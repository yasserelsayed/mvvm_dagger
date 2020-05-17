package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Tripdb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    List<Tripdb> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tripdb mTrip);

    @Query("UPDATE trip SET backend_id = :bid WHERE dbid = :tripId")
    void updateTipBackendid(int tripId,String bid);

    @Delete
    void delete(Tripdb mTrip);

    @Query("DELETE FROM trip")
    void reset();
}
