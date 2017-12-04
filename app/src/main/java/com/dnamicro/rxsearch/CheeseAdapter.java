package com.dnamicro.rxsearch;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joselito Narte Jr. on 04/12/2017.
 */

public class CheeseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> cheeses = new ArrayList<>();

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cheese_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(cheeses.get(position));
    }

    @Override
    public int getItemCount() {
        return cheeses.size();
    }

    public void swapItems(List<String> value) {
        this.cheeses = value;
        notifyDataSetChanged();
    }

    class ViewHolder extends  RecyclerView.ViewHolder {

        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
