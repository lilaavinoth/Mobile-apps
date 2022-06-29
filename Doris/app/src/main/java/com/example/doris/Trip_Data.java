package com.example.doris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Trip_Data extends AppCompatActivity {

    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView tracktimedata,lat,lon,ignition,speed,odometer,direction,gpsstatus,enineTemp,tirepressure,light,theft,fuel,window,accident;
    Button locateCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip__data);
        tracktimedata = (TextView) findViewById(R.id.tracktimedata);
        lat = (TextView) findViewById(R.id.latdata);
        lon = (TextView) findViewById(R.id.londata);
        ignition = (TextView) findViewById(R.id.ignitiondata);
        speed = (TextView) findViewById(R.id.speeddata);
        odometer = (TextView) findViewById(R.id.ododata);
        direction = (TextView) findViewById(R.id.dirdata);
        gpsstatus = (TextView) findViewById(R.id.gpsdata);
        enineTemp = (TextView) findViewById(R.id.enginedata);
        tirepressure = (TextView) findViewById(R.id.tirepressuredata);
        light = (TextView) findViewById(R.id.lightdata);
//        locateCar = (Button) findViewById(R.id.lc);
        theft = (TextView) findViewById(R.id.theftdecdata);
        fuel = (TextView) findViewById(R.id.fueldata);
        window = (TextView) findViewById(R.id.windowdata);
        accident = (TextView) findViewById(R.id.accidentdata);





//        recyclerView = findViewById(R.id.recyclerTripdata);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Trip Data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a = dataSnapshot.child("track_time").getValue().toString();
                String b = dataSnapshot.child("lat").getValue().toString();
                String c = dataSnapshot.child("lon").getValue().toString();
                String d = dataSnapshot.child("ignition").getValue().toString();
                String e = dataSnapshot.child("speedOfVehicle").getValue().toString();
                String f = dataSnapshot.child("odometer").getValue().toString();
                String g = dataSnapshot.child("track_time").getValue().toString();
                String h = dataSnapshot.child("direction").getValue().toString();
                String i = dataSnapshot.child("Gps_status").getValue().toString();
                String j = dataSnapshot.child("lights").getValue().toString();
                String k = dataSnapshot.child("tire_pressure").getValue().toString();
                String l = dataSnapshot.child("engine_temp").getValue().toString();
                String m = dataSnapshot.child("Theft_Detection").getValue().toString();
                String n = dataSnapshot.child("Fuel").getValue().toString();
                String o = dataSnapshot.child("Window").getValue().toString();
                String p = dataSnapshot.child("Accident").getValue().toString();




                tracktimedata.setText(a);
                lat.setText(b);
                lon.setText(c);
                ignition.setText(d);
                speed.setText(e);
                odometer.setText(f);
                tracktimedata.setText(g);
                direction.setText(h);
                gpsstatus.setText(i);
                light.setText(j);
                tirepressure.setText(k);
                enineTemp.setText(l);
                theft.setText(m);
                fuel.setText(n);
                window.setText(o);
                accident.setText(p);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        locateCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Trip_Data.this,com.example.doris.Maps.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_left,R.anim.slide_out_right);
//            }
//        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerOptions<RecieverDataStore> options =
//                new FirebaseRecyclerOptions.Builder<RecieverDataStore>()
//                        .setQuery(databaseReference, RecieverDataStore.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<RecieverDataStore, Holder> adapter =
//                new FirebaseRecyclerAdapter<RecieverDataStore, Holder>(options) {
//                    @NonNull
//                    @Override
//                    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_data, parent, false);
//                        Holder holder = new Holder(view);
//                        return holder;
//                    }
//
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull final Holder holder, int position, @NonNull final RecieverDataStore model) {
//                        // holder.whatsappsender.setText(model.getSender());
//                        holder.title.setText(model.get());
//                        holder.data.setText(model.getTime());
//
//    }
}