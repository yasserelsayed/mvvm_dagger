package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Activitydb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activity WHERE location_id = :locationId")
    List<Activitydb> getAll(int locationId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Activitydb mActivity);

    @Query("SELECT * FROM activity WHERE dbid = :activityId")
    Activitydb getRecord(String activityId);

    @Delete
    void delete(Activitydb mActivity);

    @Query("DELETE FROM activity")
    void reset();
}
