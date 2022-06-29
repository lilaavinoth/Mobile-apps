package com.example.testing2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SOS_Activity extends AppCompatActivity {

    Button stopalert;
    TextView acceptedData,alertedData;

    DatabaseReference databaseReferenceq,databaseReferenceAcc,deleteAlerted,deleteAccepted;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String Login = "Lo";
    public static final String phenom = "no";

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Your ALERT is active", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_o_s_);

        acceptedData = (TextView) findViewById(R.id.fetcher);
        alertedData = (TextView) findViewById(R.id.alertedfetcher);
        stopalert = (Button) findViewById(R.id.stopalert) ;

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String ph = sharedPreferences.getString(phenom, "");

        Log.d("SOS phone no",ph);

        assert ph != null;
        databaseReferenceq = FirebaseDatabase.getInstance().getReference().child("Alerted").child(ph);
        databaseReferenceq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int countAlerted = (int) snapshot.getChildrenCount();
                    alertedData.setText(countAlerted+"");
                }
                else
                {
                    alertedData.setText("Fetching");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceAcc = FirebaseDatabase.getInstance().getReference().child("Accepted").child(ph);
        databaseReferenceAcc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int countAlerted = (int) snapshot.getChildrenCount();
                    acceptedData.setText(countAlerted+"");
                }
                else
                {
                    acceptedData.setText("Fetching");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        stopalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Login, "2");

                editor.apply();

                clearData();

                deleteAlertAccept();

                Intent intent = new Intent(SOS_Activity.this,MainActivity.class);
                finish();


            }
        });

    }

    private void deleteAlertAccept() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String myphone = sharedPreferences.getString(phenom,"");
        deleteAlerted = FirebaseDatabase.getInstance().getReference().child("Alerted").child(myphone);
        deleteAlerted.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Delete alerted action", "onCancelled", error.toException());
            }
        });
        deleteAccepted = FirebaseDatabase.getInstance().getReference().child("Accepted").child(myphone);
        deleteAccepted.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Delete accepted action", "onCancelled", error.toException());
            }
        });

    }

    private void clearData() {
        databaseReferenceq = FirebaseDatabase.getInstance().getReference().child("Primary Alert");
        Map<String, Object> beacon = new HashMap<>();
        beacon.put("Phone", "");
        beacon.put("Latitude", "");
        beacon.put("Longitude", "");
        beacon.put("Address","");
        beacon.put("ID","");
        databaseReferenceq.updateChildren(beacon);
    }
}