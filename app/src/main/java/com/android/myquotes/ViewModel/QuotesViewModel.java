package com.android.myquotes.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.android.myquotes.model.Quote;
import com.android.myquotes.repository.QuoteRepository;

import java.util.List;

public class QuotesViewModel extends AndroidViewModel {

    private QuoteRepository quoteRepository;

    private LiveData<List<Quote>> allQuotes;

    public QuotesViewModel(@NonNull Application application) {
        super(application);
        quoteRepository = new QuoteRepository(application);
        allQuotes = quoteRepository.getAllQuotes();
    }

    public void insert(Quote quote) {
        quoteRepository.insert(quote);
    }

    public void update(Quote quote) {
        quoteRepository.update(quote);
    }

    public void delete(Quote quote) {
        quoteRepository.delete(quote);
    }

    public void deleteAllQuotes() {
        quoteRepository.deleteAllQuotes();
    }

    public LiveData<List<Quote>> getAllQuotes() {
        return allQuotes;
    }
}
