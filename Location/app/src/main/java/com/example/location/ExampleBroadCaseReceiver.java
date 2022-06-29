package com.example.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class ExampleBroadCaseReceiver extends BroadcastReceiver {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT1 = "text";

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGpsEnabled || isNetworkEnabled) {
                Log.d("Status","GPS Enabled");
//                editor.putString(TEXT1, "GPS Enabled");
//                editor.apply();
                Toast.makeText(context, "GPS Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("Status","GPS Disabled");
//                editor.putString(TEXT1, "GPS Disabled");
//                editor.apply();
                Toast.makeText(context, "GPS Disabled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
