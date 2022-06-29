package com.example.messageviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Realtime extends AppCompatActivity {

    TextView sender;
    TextView message;
    DatabaseReference databaseReference,databaseReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        sender = (TextView) findViewById(R.id.realtimesender);
        message = (TextView) findViewById(R.id.contentm);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Realtime");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a = dataSnapshot.child("Message").getValue().toString();
                String b = dataSnapshot.child("Sender").getValue().toString();
                message.setText(a);
                sender.setText(b);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}