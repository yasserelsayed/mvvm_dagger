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

    @Query("SELECT * FROM qoute")
    List<Quotedb> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Quotedb mQuote);

    @Query("SELECT * FROM qoute WHERE dbid = :quoteId")
    Quotedb getRecord(String quoteId);

    @Delete
    void delete(Quotedb mQuote);
}
