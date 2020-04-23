package com.example.travellernotebook.data.database;



import com.example.travellernotebook.data.database.dao.ActivityDao;
import com.example.travellernotebook.data.database.dao.LocationDao;
import com.example.travellernotebook.data.database.dao.MediaDao;
import com.example.travellernotebook.data.database.dao.QuoteDao;
import com.example.travellernotebook.data.database.dao.TripDao;
import com.example.travellernotebook.data.database.entities.Activity;
import com.example.travellernotebook.data.database.entities.Location;
import com.example.travellernotebook.data.database.entities.Media;
import com.example.travellernotebook.data.database.entities.Quote;
import com.example.travellernotebook.data.database.entities.Trip;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Trip.class,Activity.class, Location.class, Quote.class, Media.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
    public abstract LocationDao locationDao();
    public abstract ActivityDao activityDao();
    public abstract QuoteDao quoteDao();
    public abstract MediaDao mediaDao();

}
