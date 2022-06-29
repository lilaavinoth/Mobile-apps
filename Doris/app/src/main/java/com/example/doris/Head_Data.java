package com.example.doris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Head_Data extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView waveband,programmetye,freqch,progridetif,stationname,source,timeonchannel,starttimech,endtimech;
    TextView categname,appname,statename,btconnstatus,timebt,starttimebt,endtimebt;
    TextView key,sourcehp,directon;
    TextView catname,appnem,screenname,keytype,wabeband;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head__data);


        waveband = (TextView) findViewById(R.id.wbdata);
        programmetye = (TextView) findViewById(R.id.ptdata);
        freqch = (TextView) findViewById(R.id.cfdata);
        progridetif = (TextView) findViewById(R.id.pidata);
        stationname = (TextView) findViewById(R.id.sndata);
        source = (TextView) findViewById(R.id.srcdata);
        timeonchannel = (TextView) findViewById(R.id.tsdata);
        starttimech = (TextView) findViewById(R.id.stdata);
        endtimech = (TextView) findViewById(R.id.etdata);
        categname = (TextView) findViewById(R.id.cndata);
        appname = (TextView) findViewById(R.id.apndata);
        statename = (TextView) findViewById(R.id.stadata);
        btconnstatus = (TextView) findViewById(R.id.btsdata);
        timebt = (TextView) findViewById(R.id.btctdata);
        starttimebt = (TextView) findViewById(R.id.btstdata);
        endtimebt = (TextView) findViewById(R.id.btetdata);
        key = (TextView) findViewById(R.id.keydata);
        sourcehp = (TextView) findViewById(R.id.sourcedata);
        directon = (TextView) findViewById(R.id.diredata);
        catname = (TextView) findViewById(R.id.catdata);
        appnem = (TextView) findViewById(R.id.andata);
        screenname = (TextView) findViewById(R.id.sceentimedata);
        keytype = (TextView) findViewById(R.id.keytdata);
        wabeband = (TextView) findViewById(R.id.wabdata);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Head Unit Data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a = dataSnapshot.child("waveband").getValue().toString();
                String b = dataSnapshot.child("programme_type").getValue().toString();
                String c = dataSnapshot.child("Frequency_of_channel").getValue().toString();
                String d = dataSnapshot.child("programme_identification").getValue().toString();
                String e = dataSnapshot.child("stationName").getValue().toString();
                String f = dataSnapshot.child("source").getValue().toString();
                String g = dataSnapshot.child("Time_Spent_on_the_Channel").getValue().toString();
                String h = dataSnapshot.child("Start_time_on_Channel").getValue().toString();
                String i = dataSnapshot.child("End_time_on_Channel").getValue().toString();
                String j = dataSnapshot.child("CategoryName").getValue().toString();
                String k = dataSnapshot.child("AppName").getValue().toString();
                String l = dataSnapshot.child("State").getValue().toString();
                String m = dataSnapshot.child("BT_Connectivity_Status").getValue().toString();
                String n = dataSnapshot.child("Time_BT_is_connected").getValue().toString();
                String o = dataSnapshot.child("Start_time_for_BT_connectivity").getValue().toString();
                String p = dataSnapshot.child("End_time_for_BT_connectivity").getValue().toString();
                String q = dataSnapshot.child("key").getValue().toString();
                String r = dataSnapshot.child("source_headUnit").getValue().toString();
                String s = dataSnapshot.child("direction_headUnit").getValue().toString();
                String t = dataSnapshot.child("CategoryName_headUnit").getValue().toString();
                String u = dataSnapshot.child("AppName_headUnit").getValue().toString();
                String v = dataSnapshot.child("ScreenName").getValue().toString();
                String w = dataSnapshot.child("KeyType").getValue().toString();
                String x = dataSnapshot.child("Action").getValue().toString();


                waveband.setText(a);
                programmetye.setText(b);
                freqch.setText(c);
                progridetif.setText(d);
                stationname.setText(e);
                source.setText(f);
                timeonchannel.setText(g);
                starttimech.setText(h);
                endtimech.setText(i);
                categname.setText(j);
                appname.setText(k);
                statename.setText(l);
                btconnstatus.setText(m);
                timebt.setText(n);
                starttimebt.setText(o);
                endtimebt.setText(p);
                key.setText(q);
                sourcehp.setText(r);
                directon.setText(s);
                catname.setText(t);
                appnem.setText(u);
                screenname.setText(v);
                keytype.setText(w);
                wabeband.setText(x);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}