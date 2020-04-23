package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Activity;
import com.example.travellernotebook.data.database.entities.Media;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MediaDao {

    @Query("SELECT * FROM media")
    List<Media> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Media mActivity);

    @Query("SELECT * FROM media WHERE dbid = :mediaId")
    Activity getRecord(String mediaId);

    @Delete
    void delete(Media mMedia);
}
