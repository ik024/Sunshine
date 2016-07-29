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
import android.widget.Toast;

import com.github.ik024.sunshine.R;
import com.github.ik024.sunshine.presenters.SettingsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    ListView lvSettings;
    SettingsAdapter adapterSettings;
    Toolbar toolbar;
    AlertDialog.Builder inputDialog, listDialog;
    EditText etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvSettings = (ListView) findViewById(R.id.lv_settings);
        adapterSettings = new SettingsAdapter(getSettingsOptions());
        lvSettings.setAdapter(adapterSettings);
        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(SettingsActivity.this, "Location", Toast.LENGTH_SHORT).show();
                        showInputDialog();
                        break;
                    case 1:
                        Toast.makeText(SettingsActivity.this, "Unit", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private List<String> getSettingsOptions(){
        List<String> settingsOptList = new ArrayList<>();
        //format: Title - SubTitle
        settingsOptList.add("Location-Select your location");
        settingsOptList.add("Unit-Select your preferred unit of measurement");

        return settingsOptList;
    }

    private void showInputDialog(){
        if(inputDialog == null){
            inputDialog = new AlertDialog.Builder(SettingsActivity.this);
            inputDialog.setTitle("Enter your Location");
            etLocation = new EditText(SettingsActivity.this);
            View view = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.settings_location_input_dialog, null);
            etLocation = (EditText) view.findViewById(R.id.et_settings_location_input);
            inputDialog.setView(view);
            inputDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            inputDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        inputDialog.show();
    }
}
