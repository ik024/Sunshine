package com.github.ik024.sunshine.presenters;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ismail.khan2 on 8/1/2016.
 */
public class MySharedPreference {

    Context mContext;
    SharedPreferences sp;

    public final static String SHARED_PREF = "MyPreferences";
    public final static String LOCATION_PREF = "Location";
    public final static String UNIT_PREF = "Unit";
    public final static int CELSIUS_UNIT = 0;
    public final static int FAHRENHEIT_UNIT = 1;

    public MySharedPreference(Context context){
        mContext = context;
        sp = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void saveLocationPreference(String location){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LOCATION_PREF, location);
        editor.apply();
    }

    public String getLocationPreference(){
        return sp.getString(LOCATION_PREF, null);
    }

    public void saveUnitPreference(int unitSelected){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(UNIT_PREF, unitSelected);
        editor.apply();
    }

    public int getUnitPreference(){
        return sp.getInt(UNIT_PREF, -1);
    }

}
