package com.example.travellernotebook.domain;

import com.example.travellernotebook.data.database.entities.Quotedb;

import java.io.Serializable;

public class Quote implements Serializable {
    int id;
    String quote;
    int parent;
    int location;

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    public Quote(Quotedb mQuotedb) {
        this.id = mQuotedb.dbid;
        this.quote = mQuotedb.quote;
        this.parent = mQuotedb.parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getQuote() {
        return quote;
    }

    public int getParent() {
        return parent;
    }

    public Quote() {
    }

    public Quotedb getRow()
    {
        Quotedb mQuotedb =  new Quotedb();
        mQuotedb.dbid = id;
        mQuotedb.quote = quote;
        mQuotedb.parent = parent;
        return mQuotedb;
    }

}
