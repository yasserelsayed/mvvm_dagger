package com.example.travellernotebook.data.database;



import com.example.travellernotebook.data.database.dao.ActivityDao;
import com.example.travellernotebook.data.database.dao.LocationDao;
import com.example.travellernotebook.data.database.dao.MediaDao;
import com.example.travellernotebook.data.database.dao.QuoteDao;
import com.example.travellernotebook.data.database.dao.TripDao;
import com.example.travellernotebook.data.database.entities.Activitydb;
import com.example.travellernotebook.data.database.entities.Locationdb;
import com.example.travellernotebook.data.database.entities.Mediadb;
import com.example.travellernotebook.data.database.entities.Quotedb;
import com.example.travellernotebook.data.database.entities.Tripdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Tripdb.class, Activitydb.class, Locationdb.class, Quotedb.class, Mediadb.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
    public abstract LocationDao locationDao();
    public abstract ActivityDao activityDao();
    public abstract QuoteDao quoteDao();
    public abstract MediaDao mediaDao();

}
