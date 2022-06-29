package com.example.dorisaio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class blocker extends AppCompatActivity {

    TextView textView,textView1;
    MediaPlayer mediaPlayer;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocker);

        textView = findViewById(R.id.phone);
        textView1 = findViewById(R.id.blo);

        String android_id = "Android ID: "+Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        String devicemodel = "Manufacturer: "+android.os.Build.MANUFACTURER+"\n"+"Model: "+android.os.Build.MODEL+"\n"+"Brand: "+android.os.Build.BRAND+"\n"+"Serial: "+android.os.Build.SERIAL;

        textView.setText(android_id);
        textView1.setText(devicemodel);

        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.block);

        }
        mediaPlayer.start();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Client").child("allower");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valu = dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}