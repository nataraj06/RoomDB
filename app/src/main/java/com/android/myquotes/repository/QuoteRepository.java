package com.android.myquotes.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.android.myquotes.dao.QuotesDao;
import com.android.myquotes.db.QuotesDataBase;
import com.android.myquotes.model.Quote;

import java.util.List;

public class QuoteRepository {

    private QuotesDao quotesDao;
    private LiveData<List<Quote>> allQuotes;

    public QuoteRepository(Application application) {
        QuotesDataBase dataBase = QuotesDataBase.getInstance(application);
        quotesDao = dataBase.quotesDao();
        allQuotes = quotesDao.getAllQuotes();
    }

    public void insert(Quote quote) {
        new InsertQuoteAsyncTask(quotesDao).execute(quote);
    }

    public void update(Quote quote) {
        new UpdateQuoteAsyncTask(quotesDao).execute(quote);
    }

    public void delete(Quote quote) {
        new DeleteQuoteAsyncTask(quotesDao).execute(quote);
    }

    public void deleteAllQuotes() {
        new DeleteAllQuoteAsyncTask(quotesDao).execute();
    }

    public LiveData<List<Quote>> getAllQuotes() {
        return allQuotes;
    }

    private static class InsertQuoteAsyncTask extends AsyncTask<Quote, Void, Void> {
        private QuotesDao quotesDao;

        private InsertQuoteAsyncTask(QuotesDao quotesDao) {
            this.quotesDao = quotesDao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {
            quotesDao.insert(quotes[0]);
            return null;
        }
    }

    private static class UpdateQuoteAsyncTask extends AsyncTask<Quote, Void, Void> {
        private QuotesDao quotesDao;

        private UpdateQuoteAsyncTask(QuotesDao quotesDao) {
            this.quotesDao = quotesDao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {
            quotesDao.update(quotes[0]);
            return null;
        }
    }

    private static class DeleteQuoteAsyncTask extends AsyncTask<Quote, Void, Void> {
        private QuotesDao quotesDao;

        private DeleteQuoteAsyncTask(QuotesDao quotesDao) {
            this.quotesDao = quotesDao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {
            quotesDao.delete(quotes[0]);
            return null;
        }
    }

    private static class DeleteAllQuoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private QuotesDao quotesDao;

        private DeleteAllQuoteAsyncTask(QuotesDao quotesDao) {
            this.quotesDao = quotesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            quotesDao.deleteAllQuotes();
            return null;
        }
    }
}
