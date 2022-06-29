package com.example.doris;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class accidentDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        Context context;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Your friend met with an accident!")
                .setMessage("Help them out!")
                .setPositiveButton("locate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetdata();
                    }
                });
        return builder.create();
    }

    private void resetdata() {
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Special_Accident");
        HashMap<String,Object> voice = new HashMap<>();
        voice.put("AlertWindow","0");
        databaseReference2.updateChildren(voice);
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
}
