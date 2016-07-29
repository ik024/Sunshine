package com.github.ik024.sunshine.presenters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ik024.sunshine.R;
import com.github.ik024.sunshine.models.ForecastItem;
import com.github.ik024.sunshine.models.IForecastListClickListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ismail.Khan2 on 7/4/2016.
 */
public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.ForecastVH> {

    List<ForecastItem> mListItems = Collections.EMPTY_LIST;
    IForecastListClickListener mListener;

    public ForecastItemAdapter(IForecastListClickListener listener, List<ForecastItem> items){
        mListener = listener;
        mListItems = items;
    }

    @Override
    public ForecastVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast_list, null);
        ForecastVH viewHolder = new ForecastVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastVH holder, int position) {
        holder.item.setText(mListItems.get(position).getItem());
        holder.item.setTag(position);
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.forecastItemClicked((int) item.getTag());
                    }
                }
            });
        }
    }
}
