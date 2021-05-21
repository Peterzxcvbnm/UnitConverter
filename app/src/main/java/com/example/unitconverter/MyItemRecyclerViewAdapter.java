package com.example.unitconverter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final MutableLiveData<List<String>> mValues;

    public MyItemRecyclerViewAdapter(MutableLiveData<List<String>> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.getValue().get(position);
        holder.mContentView.setText(mValues.getValue().get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final Button mContentView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (Button) view.findViewById(R.id.listItemButton);
            mContentView.setOnClickListener(param -> {
                Context context = view.getContext();
                Intent conversionIntent = new Intent(context, ConversionActivity.class);
                Log.i("SPINNER", "Setting intent text to: " + mContentView.getText());
                conversionIntent.putExtra(Intent.EXTRA_TEXT, mContentView.getText());
                context.startActivity(conversionIntent);
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}