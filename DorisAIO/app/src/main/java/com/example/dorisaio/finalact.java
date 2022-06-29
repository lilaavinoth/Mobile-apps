package com.example.dorisaio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import static com.example.dorisaio.App.CHANNEL_1_ID;

public class finalact extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    TextView textView;
    Integer integer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalact);

        if (haveNetwork()){
            integer = 1;
        } else if (!haveNetwork()) {
            integer = 0;
        }

        notificationManager = NotificationManagerCompat.from(this);
    }



    private boolean haveNetwork(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }

    public void startService(View v) {

        if (integer == 1)
        {
            Intent serviceIntent = new Intent(this, ExampleService.class);
            serviceIntent.putExtra("inputExtra", "Connected with Doris U.S Servers");

            ContextCompat.startForegroundService(this, serviceIntent);
        }
        if (integer == 0)
        {
            Toast.makeText(this, "Internet Connection Required", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);
    }
}