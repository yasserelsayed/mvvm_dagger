package com.example.travellernotebook.domain;

import com.example.travellernotebook.data.database.entities.Activitydb;

import java.io.Serializable;
import java.util.List;

public class Activity implements Serializable {
    int id;
    String activityName;
    int parent;

    List<Quote>lstQuotes;

    public void setLstQuotes(List<Quote> lstQuotes) {
        this.lstQuotes = lstQuotes;
    }

    public List<Quote> getLstQuotes() {
        return lstQuotes;
    }

    public Activity(Activitydb mActivitydb) {
        this.id = mActivitydb.dbid;
        this.activityName = mActivitydb.activityName;
        this.parent = mActivitydb.parent;

    }

    public Activity() {
    }

    public Activitydb getRow()
    {
        Activitydb mActivitydb =  new Activitydb();
        mActivitydb.dbid = id;
        mActivitydb.activityName = activityName;
        mActivitydb.parent = parent;
        return mActivitydb;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getId() {
        return id;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getParent() {
        return parent;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setParent(int parent) {
        this.parent = parent;
    }
}
