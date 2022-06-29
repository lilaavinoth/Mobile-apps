package com.example.dorisaio;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.dorisaio.App.CHANNEL_1_ID;
import static com.example.dorisaio.App.CHANNEL_ID;

public class ExampleService extends Service {

    public static DatabaseReference reference;
    DatabaseReference databaseReference,noti;
    private NotificationManagerCompat notificationManager;

    int iter = 5;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, finalact.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Doris AI Engine Online")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_baseline_android_24)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        reference = FirebaseDatabase.getInstance().getReference().child("Client");
        Map<String, Object> beacon = new HashMap<>();
        beacon.put("start", "1");
        reference.updateChildren(beacon);

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

        noti = FirebaseDatabase.getInstance().getReference().child("Client").child("noti").child("notification");
        noti.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valu = dataSnapshot.getValue().toString();
                notificaionbuild(valu);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return START_STICKY;
    }

    private void notificaionbuild(String valu) {


        notificationManager = NotificationManagerCompat.from(this);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_android_24)
                .setContentTitle("Progress Status")
                .setContentText(valu)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(iter, notification);

        iter += 1;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
