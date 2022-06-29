package com.example.doris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Realtime extends AppCompatActivity {



    Button candatabtn,tripdatabtn,headunitdatabtn,locatecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        candatabtn = findViewById(R.id.candata);
        tripdatabtn = findViewById(R.id.tripdata);
        headunitdatabtn = findViewById(R.id.headunit);
        locatecar = findViewById(R.id.map);

        tripdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Realtime.this,com.example.doris.Trip_Data.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left,R.anim.slide_out_right);

            }
        });
        candatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Realtime.this,com.example.doris.Can_Data.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left,R.anim.slide_out_right);

            }
        });
        headunitdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Realtime.this,com.example.doris.Head_Data.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left,R.anim.slide_out_right);

            }
        });
        locatecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Trip Data");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String lat = snapshot.child("lat").getValue().toString();
                        String lon = snapshot.child("lon").getValue().toString();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+lat+","+lon+"&mode=d"));
//                intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}