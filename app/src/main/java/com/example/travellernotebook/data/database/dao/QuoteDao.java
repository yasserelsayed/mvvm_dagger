package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Quotedb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface QuoteDao {

    @Query("SELECT * FROM quote WHERE activity_id = :activityId")
    List<Quotedb> getAll(int activityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Quotedb mQuote);

    @Delete
    void delete(Quotedb mQuote);

    @Query("DELETE FROM quote")
    void reset();
}
