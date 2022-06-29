package com.example.plantcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button override;
    TextView values;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        override = (Button) findViewById(R.id.refresh);
        values = (TextView) findViewById(R.id.values);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("plant");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valu = dataSnapshot.getValue().toString();
                values.setText(valu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        override.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                over();
            }
        });

    }

    private void over() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap <String,String > hashMap = new HashMap<>();
        hashMap.put("override" , "true");
        databaseReference.setValue(hashMap);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

                HashMap <String,String > hashMap = new HashMap<>();
                hashMap.put("override" , "false");
                databaseReference.setValue(hashMap);
            }
        }, 10000); // Millisecond 1000 = 1 sec
    }
}