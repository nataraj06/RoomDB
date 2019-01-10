package com.android.myquotes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.myquotes.R;
import com.android.myquotes.model.Quote;

public class QuoteAdapter extends ListAdapter<Quote, QuoteAdapter.QuoteHolder> {
    private OnItemClickListener listener;

    public QuoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Quote> DIFF_CALLBACK = new DiffUtil.ItemCallback<Quote>() {
        @Override
        public boolean areItemsTheSame(Quote oldQuote, Quote newQuote) {
            return oldQuote.getId() == newQuote.getId();
        }

        @Override
        public boolean areContentsTheSame(Quote oldQuote, Quote newQuote) {
            return oldQuote.getQuotes().equals(newQuote.getQuotes());
        }
    };

    @NonNull
    @Override
    public QuoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotes_list_item, parent, false);
        return new QuoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteHolder holder, int position) {
        Quote currentQuote = getItem(position);
        holder.quoteTxt.setText(currentQuote.getQuotes());
    }

    public Quote getQuoteAt(int position) {
        return getItem(position);
    }

    class QuoteHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView quoteTxt;

        public QuoteHolder(View itemView) {
            super(itemView);
            quoteTxt = itemView.findViewById(R.id.quotes_txt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Quote quote);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
