package com.github.ik024.sunshine.views;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.github.ik024.sunshine.R;
import com.github.ik024.sunshine.presenters.MySharedPreference;
import com.github.ik024.sunshine.presenters.SettingsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    ListView lvSettings;
    SettingsAdapter adapterSettings;
    Toolbar toolbar;
    AlertDialog.Builder locationInputDialog, unitOptionDialog;

    MySharedPreference mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSharedPref = new MySharedPreference(this);

        lvSettings = (ListView) findViewById(R.id.lv_settings);
        adapterSettings = new SettingsAdapter(getSettingsOptions());
        lvSettings.setAdapter(adapterSettings);
        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showLocationInputDialog();
                        break;
                    case 1:
                        showUnitOptionDialog();
                        break;
                }
            }
        });
    }

    private List<String> getSettingsOptions() {
        List<String> settingsOptList = new ArrayList<>();
        //format: Title - SubTitle
        settingsOptList.add("Location-Select your location");
        settingsOptList.add("Unit-Select your preferred unit of measurement");

        return settingsOptList;
    }

    private void showLocationInputDialog() {
        locationInputDialog = null;

        locationInputDialog = new AlertDialog.Builder(SettingsActivity.this);
        locationInputDialog.setTitle("Enter your Location");
        View view = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.settings_location_input_dialog, null);
        final EditText etLocation = (EditText) view.findViewById(R.id.et_settings_location_input);

        String saveLocation = mSharedPref.getLocationPreference();

        if (saveLocation != null) {
            etLocation.setText(saveLocation);
        }

        locationInputDialog.setView(view);
        locationInputDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredLocation = etLocation.getText().toString();
                if (enteredLocation.length() > 0) {
                    mSharedPref.saveLocationPreference(enteredLocation);
                }
            }
        });
        locationInputDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        locationInputDialog.show();
    }

    private void showUnitOptionDialog() {
        unitOptionDialog = null;

        unitOptionDialog = new AlertDialog.Builder(SettingsActivity.this);
        unitOptionDialog.setTitle("Select preferred Unit");
        View view = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.settings_unit_input_dialog, null);
        final RadioButton celsius = (RadioButton) view.findViewById(R.id.settings_unit_celcius);
        final RadioButton fahrenheit = (RadioButton) view.findViewById(R.id.settings_unit_farenhite);

        int selectedUnitPref = mSharedPref.getUnitPreference();
        if (selectedUnitPref == MySharedPreference.CELSIUS_UNIT) {
            celsius.setChecked(true);
        } else if (selectedUnitPref == MySharedPreference.FAHRENHEIT_UNIT) {
            fahrenheit.setChecked(true);
        }

        unitOptionDialog.setView(view);
        unitOptionDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (celsius.isChecked()) {
                    mSharedPref.saveUnitPreference(MySharedPreference.CELSIUS_UNIT);
                } else if (fahrenheit.isChecked()) {
                    mSharedPref.saveUnitPreference(MySharedPreference.FAHRENHEIT_UNIT);
                }
            }
        });
        unitOptionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        unitOptionDialog.show();
    }
}
