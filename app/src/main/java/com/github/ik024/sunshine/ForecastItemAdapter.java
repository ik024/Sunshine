package com.github.ik024.sunshine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ismail.Khan2 on 7/4/2016.
 */
public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.ForecastVH> {

    List<ForecastItem> mListItems = Collections.EMPTY_LIST;

    public ForecastItemAdapter(List<ForecastItem> items){
        mListItems = items;
    }

    @Override
    public ForecastVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_forecast, null);
        ForecastVH viewHolder = new ForecastVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastVH holder, int position) {
        holder.item.setText(mListItems.get(position).getItem());
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class ForecastVH extends RecyclerView.ViewHolder{

        TextView item;

        public ForecastVH(View view) {
            super(view);
            item = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        }
    }
}
