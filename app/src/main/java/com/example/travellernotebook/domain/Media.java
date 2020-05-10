package com.example.travellernotebook.domain;

import com.example.travellernotebook.data.database.entities.Mediadb;

import java.io.Serializable;

public class Media implements Serializable {
    int id;
    String path;
    String thumbnail;
    int parent;

    public String getPath() {
        return path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Media(Mediadb mMediadb) {
        this.id = mMediadb.dbid;
        this.path = mMediadb.path;
        this.thumbnail = mMediadb.thumbnail;
        this.parent = mMediadb.parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }


    public int getParent() {
        return parent;
    }

    public Media() {
    }

    public Mediadb getRow()
    {
        Mediadb mMediadb =  new Mediadb();
        mMediadb.dbid = id;
        mMediadb.path = path;
        mMediadb.thumbnail = thumbnail;
        mMediadb.parent = parent;
        return mMediadb;
    }

}
