package com.android.myquotes.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.android.myquotes.dao.QuotesDao;
import com.android.myquotes.model.Quote;

/*The RoomDatabase is an abstract class that ties all the pieces together and connects the entities to their corresponding DAO. Just as in an SQLiteOpenHelper, we have to define a version number and a migration strategy. */
@Database(entities = {Quote.class}, version = 1)
public abstract class QuotesDataBase extends RoomDatabase {

    private static QuotesDataBase instance;

    public abstract QuotesDao quotesDao();

    /*fallbackToDestructiveMigration we can let Room recreate our database if we increase the version number.
We create our database in form of a static singleton with the databaseBuilder, where we have to pass our database class and a file name.*/
    public static synchronized QuotesDataBase getInstance(Context context) {
        String DB_NAME = "quotes_db";
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    QuotesDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        //onCreate will be called only once, it will not recreate each time unless change in
        //version number and added fallbackToDestructiveMigration()
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public void reCreateDB() {
        if (instance != null) {
            new PopulateDbAsyncTask(instance).execute();
        }
    }

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private QuotesDao quotesDao;

        private PopulateDbAsyncTask(QuotesDataBase dataBase) {
            quotesDao = dataBase.quotesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            quotesDao.deleteAllQuotes();

            Quote quoteOne = new Quote();
            quoteOne.setQuotes("You have to dream before your dreams can come true");

            Quote quoteTwo = new Quote();
            quoteTwo.setQuotes("If you want to shine like a sun, first burn like a sun");

            Quote quoteThree = new Quote();
            quoteThree.setQuotes(" பல வேடிக்கை மனிதரைப் போலே - நான் வீழ்வே னென்று நினைத் தாயோ?");

            Quote quoteFour = new Quote();
            quoteFour.setQuotes("Be yourself; everyone else is already taken");

            quotesDao.insert(quoteOne, quoteTwo, quoteThree, quoteFour);

            return null;
        }
    }
}
