package com.github.ik024.sunshine.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ik024.sunshine.R;

public class ForecastDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ForecastDetailFragment() {
    }

    public static ForecastDetailFragment newInstance(){
        return new ForecastDetailFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()
            + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if(mShareActionProvider != null){
            Intent myShareIntent = new Intent(Intent.ACTION_SEND);
            myShareIntent.setType("text/plain");
            myShareIntent.putExtra(Intent.EXTRA_TEXT, forecast);
            setShareIntent(myShareIntent);
        }
    }

    public void forecastItemSelected(String forecastItem){
        forecast = forecastItem;
        Toast.makeText(getActivity(), forecastItem, Toast.LENGTH_SHORT).show();
        tvForecastSelected.setText(forecast);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.menu_item_share:
                Toast.makeText(getActivity(), "Action Share", Toast.LENGTH_SHORT).show();
                Intent myShareIntent = new Intent(Intent.ACTION_SEND);
                myShareIntent.setType("text/plain");
                myShareIntent.putExtra(Intent.EXTRA_TEXT, forecast);
                setShareIntent(myShareIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private ShareActionProvider mShareActionProvider;
    private String forecast="";
    private TextView tvForecastSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
        tvForecastSelected = (TextView) view.findViewById(R.id.tv_forecast_selected);
        return view;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public interface OnFragmentInteractionListener{
    }
}
