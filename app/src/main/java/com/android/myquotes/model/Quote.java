package com.android.myquotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


//Here entity refers to tables in the db, and here we have a table called quote.
//we are using foreign key because the same author might have written more than one quotes,
@Entity(tableName = "quote_table")
public class Quote {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "quotes")
    @NonNull
    public String quotes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(@NonNull String quotes) {
        this.quotes = quotes;
    }
}
