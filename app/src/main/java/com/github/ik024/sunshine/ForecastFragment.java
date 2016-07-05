package com.github.ik024.sunshine;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ForecastFragment extends Fragment {

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

        fakeData();

        getForecastData("560005");

        rvForecast = (RecyclerView) view.findViewById(R.id.list_view_forecast);

        adapterForecast = new ForecastItemAdapter(forecastListItems);

        rvForecast.setAdapter(adapterForecast);

        rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));



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
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                builtUri.toString(),
                null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //dialog.cancel();
                Log.d(TAG, "Response: " + response.toString());
                new ParseForecastResponse().execute(response);//parseResponse(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });

        queue.add(stringRequest);
    }


    private void fakeData() {
        forecastListItems = new ArrayList<>();
        forecastListItems.add(new ForecastItem("Today - Sunny - 88/63"));
        forecastListItems.add(new ForecastItem("Tomorrow - Foggy - 88/63"));
        forecastListItems.add(new ForecastItem("Wednesday - Sunny - 88/63"));
        forecastListItems.add(new ForecastItem("Thursday - Sunny - 88/63"));
        forecastListItems.add(new ForecastItem("Friday - Sunny - 88/63"));
        forecastListItems.add(new ForecastItem("Saturday - Sunny - 88/63"));
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class ParseForecastResponse extends AsyncTask<JSONObject, Void, Void>{

        @Override
        protected Void doInBackground(JSONObject... params) {
            try {

                JSONArray list = params[0].getJSONArray("list");

                for(int i=0; i<list.length(); i++){

                }

                for(int i=0; i<list.length(); i++){
                    if(list.length() < 7) {
                        JSONObject day = list.getJSONObject(i);
                        if (day.has("temp")) {
                            JSONObject temp = day.getJSONObject("temp");
                            double max = temp.getDouble("max");
                            Log.d(TAG, "max temp: " + max);
                        } else {
                            Log.d(TAG, "max temp: -1");
                        }
                    }else{
                        Log.d(TAG, "max temp: -1");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void getMaxTemp(String json){

        }
    }
}
