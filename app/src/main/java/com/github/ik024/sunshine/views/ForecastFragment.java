package com.github.ik024.sunshine.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ik024.sunshine.presenters.ForecastItemAdapter;
import com.github.ik024.sunshine.R;
import com.github.ik024.sunshine.presenters.ForecastHelper;
import com.github.ik024.sunshine.models.ForecastItem;
import com.github.ik024.sunshine.models.IForecastListClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ForecastFragment extends Fragment implements IForecastListClickListener {

    private static final String TAG = ForecastFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    public ForecastFragment() {
    }

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    List<ForecastItem> forecastListItems;
    ForecastItemAdapter adapterForecast;
    RecyclerView rvForecast;
    ProgressDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        getForecastData("560005");

        rvForecast = (RecyclerView) view.findViewById(R.id.list_view_forecast);

        return view;
    }

    private void getForecastData(String postalCode){
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Please Wait");
        dialog.setMessage("Fetching Forecast...");
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        final String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
        Uri builtUri = Uri.parse(BASE_FORECAST_URL).buildUpon()
                            .appendQueryParameter("q", postalCode)
                            .appendQueryParameter("mode", "json")
                            .appendQueryParameter("units", "metric")
                            .appendQueryParameter("cnt", "7")
                            .appendQueryParameter("APPID", "47afbf8555df2d6e7178af7b2dd98c18")
                            .build();

        Log.d(TAG, "builtUri: "+builtUri.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                builtUri.toString(),
                null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String[] params = {response.toString(), "7"};
                new ParseForecastResponse().execute(params);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void forecastItemClicked(int position) {
        if (forecastListItems != null) {
            ForecastItem forecast = forecastListItems.get(position);
            Toast.makeText(getActivity(), ""+forecast.getItem(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ForecastDetailActivity.class);
            startActivity(intent);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class ParseForecastResponse extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... params) {
            try {

                return ForecastHelper.getWeatherDataFromJson(params[0], Integer.parseInt(params[1]));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            super.onPostExecute(weatherData);

            forecastListItems = new ArrayList<>();

            for(String data : weatherData){
                forecastListItems.add(new ForecastItem(data));
            }

            adapterForecast = new ForecastItemAdapter(ForecastFragment.this, forecastListItems);

            rvForecast.setAdapter(adapterForecast);

            rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));

            if(dialog != null) {
                dialog.cancel();
            }

        }

    }
}
