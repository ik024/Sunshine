package com.github.ik024.sunshine.presenters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.ik024.sunshine.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by ik on 17-07-2016.
 */
public class SettingsAdapter extends BaseAdapter {

    List<String> mItems = Collections.emptyList();
    SettingsVH holder;

    public SettingsAdapter(List<String> items){
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings_list,
                    parent, false);
            holder = new SettingsVH(convertView);
            convertView.setTag(holder);
        }else{
            holder = (SettingsVH) convertView.getTag();
        }
        String[] values = mItems.get(position).split("-");

        holder.tvTitle.setText(values[0]);
        holder.tvSubTitle.setText(values[1]);

        return convertView;
    }

    class SettingsVH {
        TextView tvTitle, tvSubTitle;
        public SettingsVH(View view){
            tvTitle = (TextView) view.findViewById(R.id.tv_settings_title);
            tvSubTitle = (TextView) view.findViewById(R.id.tv_settings_sub_title);
        }
    }
}
