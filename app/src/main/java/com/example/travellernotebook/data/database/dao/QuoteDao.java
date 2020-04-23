package com.example.travellernotebook.data.database.dao;

import com.example.travellernotebook.data.database.entities.Activity;
import com.example.travellernotebook.data.database.entities.Quote;
import com.example.travellernotebook.data.database.entities.Trip;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface QuoteDao {

    @Query("SELECT * FROM qoute")
    List<Quote> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Quote mQuote);

    @Query("SELECT * FROM QOUTE WHERE dbid = :quoteId")
    Quote getRecord(String quoteId);

    @Delete
    void delete(Quote mQuote);
}
