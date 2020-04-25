package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Activitydb;
import com.example.travellernotebook.data.database.entities.Mediadb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MediaDao {

    @Query("SELECT * FROM media")
    List<Mediadb> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mediadb mActivity);

    @Query("SELECT * FROM media WHERE dbid = :mediaId")
    Activitydb getRecord(String mediaId);

    @Delete
    void delete(Mediadb mMedia);
}
