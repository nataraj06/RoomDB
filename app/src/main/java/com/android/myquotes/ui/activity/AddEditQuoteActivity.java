package com.android.myquotes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.myquotes.R;

public class AddEditQuoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_QUOTE = "extra_quote";

    private AppCompatEditText editOrAddQuoteTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_quote_activity);
        editOrAddQuoteTxt = findViewById(R.id.add_edit_quote_txt);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Quote");
            editOrAddQuoteTxt.setText(intent.getStringExtra(EXTRA_QUOTE));
        } else {
            setTitle("Add Quote");
        }
    }

    private void saveQuote() {
        String quote = editOrAddQuoteTxt.getText().toString();

        if (TextUtils.isEmpty(quote)) {
            Toast.makeText(this, "Please enter some quote to be added", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_QUOTE, quote);

        //This -1 is because if the database already have the value or not
        //in simple terms we are checking whether the value should be updated or should added new
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save_quotes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_quote:
                saveQuote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
