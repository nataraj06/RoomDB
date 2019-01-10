package com.android.myquotes.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.myquotes.model.Quote;

import java.util.List;

/*Once the table is created, the next step is to create DAO of those
 * DAO means data access object, the queries for accessing db will be written here.
 * we can also wrap into LiveData, so our activity or fragment gets notified as soon as a row in the queried database table changes*/
@Dao
public interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Quote... quotes);

    @Update
    void update(Quote quote);

    @Delete
    void delete(Quote quote);

    @Query("DELETE FROM quote_table")
    void deleteAllQuotes();

    @Query("SELECT * FROM quote_table")
    LiveData<List<Quote>> getAllQuotes();
}
