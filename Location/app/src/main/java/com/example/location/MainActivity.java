package com.example.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcast;
    TextView textView;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT1 = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        broadcast = new MyReceiver();
        textView = findViewById(R.id.texter);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String string = sharedPreferences.getString(TEXT1, "");
        textView.setText(string);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        IntentFilter intentFilter = new IntentFilter("android.location.PROVIDERS_CHANGED");
//        intentFilter.addCategory(Intent.ACTION_PROVIDER_CHANGED);
//        registerReceiver(broadcast,intentFilter);
//    }
}