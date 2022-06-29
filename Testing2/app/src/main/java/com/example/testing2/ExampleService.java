package com.example.testing2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.example.testing2.App.CHANNEL_1_ID;
import static com.example.testing2.App.CHANNEL_ID;

public class ExampleService extends Service {


    private static final Object PERMISSIONS_FINE_LOCATION = 99;
    static MainActivity instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static DatabaseReference reference,alertedmsg,acceptedmsg;
    private NotificationManagerCompat notificationManager;
    private static final String TAG = "";
    float Latitude,Longitude,Speed;
    String address,Service_phone,RetrivedLatitude,RetrivedLongitude,RetrivedPhone,RetrivedAddress,RetrivedID;


    //Shared Preference
    public static final String SharedLatitude = "la";
    public static final String ADDRESS = "ad";
    public static final String SharedLongitude = "lo";
    public static final String phenom = "no";
    public static final String id = "45";
    public static final String SharedRetphone = "45";
    public static final String GPS = "ea";
    public static final String SHARED_PREFS = "sharedPrefs";

    private BroadcastReceiver MyReceiver = null;
    BroadcastReceiver broadcastReceiver;

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                Latitude = (float) location.getLatitude();
                Longitude = (float) location.getLongitude();
                Speed = location.getSpeed();

                Log.d("Latitude: ", Latitude + "");
                Log.d("Longitude: ", Longitude + "");

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(SharedLatitude, String.valueOf(Latitude));
                editor.putString(SharedLongitude, String.valueOf(Longitude));

                editor.apply();

                String GPS_Status = sharedPreferences.getString(GPS,"");

//                if (GPS_Status.equals("Disabled"))
//                {
//                    //to create a notification to alert when gps is turned off
//                    //create a if statement and notify when gps is turned off
//                }

                try {
                    addressfind(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    };



    private void addressfind(Location location) throws IOException {
        try
        {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(Latitude,Longitude,1);
            address = addressList.get(0).getAddressLine(0);
//        Log.d("Address: ", address + "");

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(ADDRESS, String.valueOf(address));
            editor.apply();
            Log.d("Add",sharedPreferences.getString(ADDRESS, ""));

            uploadLocation(location);
        }
        catch (Exception e)
        {
            uploadLocation(location);
            Log.d("Exception", String.valueOf(e));
        }

    }


    public static MainActivity getInstance() {
        return instance;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MyReceiver = new MyReceiver();
        broadcastReceiver = new GPS_state();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Service_phone = sharedPreferences.getString(phenom, "");


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        String input = intent.getStringExtra("inputExtra");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Women's Safety")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_baseline_android_24)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        firebaseDataReader();

        checkSettingsAndStartLocationUpdates();

        broadcastIntent();

        GPS_status();

        return START_STICKY;
    }



    private void GPS_status() {
        registerReceiver(broadcastReceiver,new IntentFilter("android.location.PROVIDERS_CHANGED"));
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }


    public static void autocleaner(String phonemy) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("threas","got here");
                reference = FirebaseDatabase.getInstance().getReference().child("Primary Alert");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String beforePhonechange = snapshot.child("Phone").getValue().toString();
                        if (phonemy.equals(beforePhonechange))
                        {
                            Map<String, Object> beacon = new HashMap<>();
                            beacon.put("Phone", "");
                            beacon.put("Latitude", "");
                            beacon.put("Longitude", "");
                            beacon.put("Address","");
                            beacon.put("ID","");
                            reference.updateChildren(beacon);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }, 30000);
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    private void firebaseDataReader() {

        reference = FirebaseDatabase.getInstance().getReference().child("Primary Alert");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                String mphone = sharedPreferences.getString(phenom,"");

                RetrivedLatitude = snapshot.child("Latitude").getValue().toString();
                RetrivedLongitude = snapshot.child("Longitude").getValue().toString();
                RetrivedPhone = snapshot.child("Phone").getValue().toString();
                RetrivedAddress = snapshot.child("Address").getValue().toString();
                RetrivedID = snapshot.child("ID").getValue().toString();

                Log.d("myadd",sharedPreferences.getString(ADDRESS, ""));
                Log.d("theiradd",RetrivedAddress);

                if (RetrivedPhone.length() != 0)
                {
                    if (!RetrivedPhone.equals(Service_phone))
                    {
                        Location_Alert.main(RetrivedAddress, sharedPreferences.getString(ADDRESS, ""));
                        if (Location_Alert.notifystate == 1)
                        {
                            notificationBuild(RetrivedLatitude, RetrivedLongitude);

                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void notificationBuild(String retrivedLatitude, String retrivedLongitude) {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedRetphone, RetrivedPhone);
        editor.apply();


        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);

        notificationManager = NotificationManagerCompat.from(this);

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);


        Intent buttonAccept = new Intent(this, Response.class);
        buttonAccept.putExtra("key", "yes");
        buttonAccept.putExtra("lat", retrivedLatitude);
        buttonAccept.putExtra("lon", retrivedLongitude);
        PendingIntent buttonPending = PendingIntent.getActivity(this,
                0, buttonAccept, 0);

        Intent buttonDecline = new Intent(this, Response.class);
        buttonDecline.putExtra("key", "no");

        PendingIntent buttonReject = PendingIntent.getActivity(this,
                0, buttonAccept, 0);


        remoteViews.setOnClickPendingIntent(R.id.accept, buttonPending);

        remoteViews.setOnClickPendingIntent(R.id.decline, buttonReject);

        Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_android_24)
                .setContentTitle("A girl is in DANGER and needs your help")
//                .setContentText(dd)
                .setAutoCancel(false)
                .setContentIntent(contentIntent)
                .setCustomBigContentView(remoteViews)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        notificationManager.notify(2, notification2);

        updateAlertedmsg();

    }

    private void updateAlertedmsg()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        alertedmsg = FirebaseDatabase.getInstance().getReference().child("Alerted").child(RetrivedPhone);
        Map<String, Object> alertedmesg = new HashMap<>();
        alertedmesg.put("Latitude", sharedPreferences.getString(SharedLatitude,""));
        alertedmesg.put("Longitude",sharedPreferences.getString(SharedLongitude,""));

        alertedmsg.child(sharedPreferences.getString(phenom,"")).updateChildren(alertedmesg);
    }


    private void uploadLocation(Location location) {
        reference = FirebaseDatabase.getInstance().getReference().child(Service_phone);
        HashMap<String,Object> locationdata = new HashMap<>();
        locationdata.put("Latitude",Latitude);
        locationdata.put("Longitude",Longitude);
//        locationdata.put("Altitude",location.getAltitude());
        locationdata.put("Accuracy",location.getAccuracy());
//        locationdata.put("Speed",Speed);
//        locationdata.put("Address",address);

        reference.updateChildren(locationdata);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
