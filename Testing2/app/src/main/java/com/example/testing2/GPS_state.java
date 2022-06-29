package com.example.testing2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

import static android.content.Context.MODE_PRIVATE;

public class GPS_state extends BroadcastReceiver {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String GPS = "ea";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (isGpsEnabled || isNetworkEnabled) {
                Toast.makeText(context, "GPS is Enabled", Toast.LENGTH_SHORT).show();
//                Log.d("GPS","Enabled");
                editor.putString(GPS, "Enabled");
                editor.apply();
            } else {
                Toast.makeText(context, "GPS is Disabled", Toast.LENGTH_SHORT).show();
//                Log.d("GPS","Disabled");
                editor.putString(GPS, "Disabled");
                editor.apply();
            }
        }
    }
}
