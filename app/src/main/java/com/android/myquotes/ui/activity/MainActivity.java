package com.android.myquotes.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.myquotes.R;
import com.android.myquotes.ViewModel.QuotesViewModel;
import com.android.myquotes.adapter.QuoteAdapter;
import com.android.myquotes.db.QuotesDataBase;
import com.android.myquotes.model.Quote;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuotesViewModel quotesViewModel;

    private static final int REQUEST_CODE_ADD_QUOTE = 100;
    private static final int REQUEST_CODE_EDIT_QUOTE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton addQuote = findViewById(R.id.fab_add_quote);
        addQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditQuoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_QUOTE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final QuoteAdapter adapter = new QuoteAdapter();
        recyclerView.setAdapter(adapter);
        quotesViewModel = ViewModelProviders.of(this).get(QuotesViewModel.class);
        quotesViewModel.getAllQuotes().observe(this, new Observer<List<Quote>>() {
            @Override
            public void onChanged(@Nullable List<Quote> quotes) {
                adapter.submitList(quotes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                quotesViewModel.delete(adapter.getQuoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Quote deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new QuoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quote quote) {
                Intent intent = new Intent(MainActivity.this, AddEditQuoteActivity.class);
                intent.putExtra(AddEditQuoteActivity.EXTRA_ID, quote.getId());
                intent.putExtra(AddEditQuoteActivity.EXTRA_QUOTE, quote.getQuotes());
                startActivityForResult(intent, REQUEST_CODE_EDIT_QUOTE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_delete_all_quotes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_quotes:
                quotesViewModel.deleteAllQuotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.recreate_db:
                QuotesDataBase.getInstance(this).reCreateDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_QUOTE && resultCode == RESULT_OK) {
            String retrievedQuote = data.getStringExtra(AddEditQuoteActivity.EXTRA_QUOTE);

            Quote quote = new Quote();
            quote.setQuotes(retrievedQuote);

            quotesViewModel.insert(quote);

            Toast.makeText(this, "Quote saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQUEST_CODE_EDIT_QUOTE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditQuoteActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Quote can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String retrievedQuote = data.getStringExtra(AddEditQuoteActivity.EXTRA_QUOTE);

            Quote quote = new Quote();
            quote.setQuotes(retrievedQuote);
            quote.setId(id);
            quotesViewModel.update(quote);

            Toast.makeText(this, "Quote updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Quote not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
