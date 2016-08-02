package com.github.ik024.sunshine.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.github.ik024.sunshine.R;

public class ForecastDetailActivity extends AppCompatActivity implements ForecastDetailFragment.OnFragmentInteractionListener {

    String forecastItem = "";
    ForecastDetailFragment fdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fdf = (ForecastDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frament_forecast_details);

        Intent intent = getIntent();
        if(intent.hasExtra(ForecastFragment.FORECAST_ITEM)){
            forecastItem = intent.getStringExtra(ForecastFragment.FORECAST_ITEM);
        }



        fdf.forecastItemSelected(forecastItem);
    }

}
