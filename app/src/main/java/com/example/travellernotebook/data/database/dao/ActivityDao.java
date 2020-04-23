package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Activity;
import com.example.travellernotebook.data.database.entities.Location;
import com.example.travellernotebook.data.database.entities.Trip;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activity")
    List<Activity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Activity mActivity);

    @Query("SELECT * FROM activity WHERE dbid = :activityId")
    Activity getRecord(String activityId);

    @Delete
    void delete(Activity mActivity);
}
