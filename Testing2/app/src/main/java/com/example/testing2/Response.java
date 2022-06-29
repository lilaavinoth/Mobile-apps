package com.example.testing2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.testing2.ExampleService.phenom;

public class Response extends AppCompatActivity {

    DatabaseReference reference2;
    String str,lat,lon,my;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SharedRetphone = "45";
    public static final String phenom = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text1 = sharedPreferences.getString(phenom, "");
        String sharedphone = sharedPreferences.getString(SharedRetphone,"");

        Intent intent = getIntent();
        str = intent.getStringExtra("key");
        my = intent.getStringExtra("my");
        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");

//        my = getIntent().getStringExtra("my");




        if (str.equals("yes"))
        {
            updateAcceptedmsg(text1,sharedphone);
            Intent buttonAccept = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+lat+", "+lon+"&mode=1"));
        buttonAccept.setPackage("com.google.android.apps.maps");

        if (buttonAccept.resolveActivity(getPackageManager()) != null)
        {
            startActivity(buttonAccept);

        }
        }

}

    private void updateAcceptedmsg(String text,String sharedPhNo) {


        reference2 = FirebaseDatabase.getInstance().getReference().child("Accepted").child(sharedPhNo);
        Map<String, Object> alertedmesg = new HashMap<>();
        alertedmesg.put("Latitude", lat);
        alertedmesg.put("Longitude",lon);


        reference2.child(text).updateChildren(alertedmesg);

    }
    }