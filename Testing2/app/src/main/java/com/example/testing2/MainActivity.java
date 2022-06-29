package com.example.testing2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    Button signout, sosbtn, trust;
    TextView textView;
    GoogleSignInClient mGoogleSignInClient;

    DatabaseReference mDatabase,databaseAlert;

    public static final String Login = "Lo";
    public static final String phenom = "no";
    public static final String SharedLatitude = "la";
    public static final String SharedLongitude = "lo";
    public static final String ADDRESS = "ad";
    public static final String id = "45";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String networkState = "n";
    public static final String GPS = "ea";


    static final int PERMISSION_ALL = 123;

    DatabaseHelper databaseHelper;

    FusedLocationProviderClient fusedLocationProviderClient;

    private BroadcastReceiver MyReceiver = null;

    private int LocationPermissionCode = 1;

    static MainActivity instance;


    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        MyReceiver = new MyReceiver();

        broadcastIntent();

        databaseHelper = new DatabaseHelper(this);

//        checkAllPermissions();


        textView = findViewById(R.id.third);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            textView.setText(personName + " has signed in");
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signout = findViewById(R.id.signoutlast);
        sosbtn = findViewById(R.id.sosbtn);
        trust = findViewById(R.id.trusted);

        trust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Trustedlist.class);
                startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signoutla();
            }
        });

        sosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                String status = sharedPreferences.getString(networkState, "");
                System.out.println(status);

                switch(status) {
                    case "Wifi enabled":
                    case "Mobile data enabled":
                        defaultInternetOperation();
                        Toast.makeText(MainActivity.this, "Wifi enabled", Toast.LENGTH_SHORT).show();
                        break;
                    case "No internet is available":
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        sendSMS();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Default", Toast.LENGTH_SHORT).show();
                        // code block
                }
            }
        });
    }

    private void checkAllPermissions() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                getPackageName(),null);
        intent.setData(uri);
        startService(intent);
    }

    private void sendSMS() {
        
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String la = sharedPreferences.getString(SharedLatitude, "");
        String lo = sharedPreferences.getString(SharedLongitude, "");


        SmsManager sms=SmsManager.getDefault();

        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 2
            //then add it to the ArrayList
            listData.add(data.getString(2));
        }

        for (int i = 0; i < listData.size(); i++)
        {
            sms.sendTextMessage(listData.get(i), null, "SaftAlrtMsg\n/"+la+"/"+lo, pi,null);
        }

        Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();
    }



    private void defaultInternetOperation() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Primary Alert");
        Map<String, Object> beacon = new HashMap<>();
        beacon.put("Phone", sharedPreferences.getString(phenom, ""));
        beacon.put("Latitude", sharedPreferences.getString(SharedLatitude, ""));
        beacon.put("Longitude", sharedPreferences.getString(SharedLongitude,""));
        beacon.put("Address",sharedPreferences.getString(ADDRESS, ""));
        beacon.put("ID",sharedPreferences.getString(id, ""));


        mDatabase.updateChildren(beacon);

        databaseAlert = FirebaseDatabase.getInstance().getReference().child(Objects.requireNonNull(sharedPreferences.getString(id, "")));
        Map<String, Object> alerted = new HashMap<>();
        beacon.put("Count", sharedPreferences.getString(phenom,""));


        databaseAlert.child("Alerted").updateChildren(alerted);

        ExampleService.autocleaner(sharedPreferences.getString(phenom, ""));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Login,"3");
        editor.apply();

        Intent intent = new Intent(MainActivity.this,SOS_Activity.class);
        startActivity(intent);
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(MyReceiver);
    }

    private void signoutla() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);

                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Login, "0");
                        editor.apply();
                    }
                });
    }


    public void startService(View v) {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) +
                (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS)) +
                (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECEIVE_SMS)) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this
            ,Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this
                            ,Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this
                            ,Manifest.permission.RECEIVE_SMS))
            {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Required")
                        .setMessage("This permission is required to keep you safe")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestAllPermissions();

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
            else
                {
                    requestAllPermissions();

//            requestLocationPermission();



            }


        }else {
            GPS_status();
        }

    }

    private void requestAllPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]
                {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS

                },PERMISSION_ALL
        );
    }

    private void GPS_status() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        LocationManager lm = (LocationManager) getSystemService(Context. LOCATION_SERVICE ) ;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
//        try {
//            network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
//        } catch (Exception e) {
//            e.printStackTrace() ;
//        }
        if (!gps_enabled) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(GPS, "Disabled");
            editor.apply();
//            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//            Log.d("Location mode","PRIORITY_BALANCED_POWER_ACCURACY");

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS is OFF")
                    .setMessage("GPS has to be enabled to get your location")
                    .setPositiveButton("Turn on", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .create();
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
            alertDialog.show();

        }
        if (gps_enabled)
        {
            Toast.makeText(MainActivity.this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();

            Intent serviceIntent = new Intent(this, ExampleService.class);
            serviceIntent.putExtra("inputExtra", "Your location is being sent to Doris server");
            ContextCompat.startForegroundService(this, serviceIntent);
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is required to keep you safe")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LocationPermissionCode);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LocationPermissionCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0 && grantResults[0] + grantResults[1] + grantResults [2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void stopService(View v) {
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

        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);
    }
}