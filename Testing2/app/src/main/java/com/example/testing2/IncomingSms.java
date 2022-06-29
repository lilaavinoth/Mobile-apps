package com.example.testing2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
public class IncomingSms extends BroadcastReceiver {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String smsla = "po";
    public static final String smslo = "es";
    public static final String smsph = "ew";
    public static String[] smsbody = new String[0];
    public static final String networkState = "n";

    DatabaseReference mDatabase,reference;


    final SmsManager sms = SmsManager.getDefault();


    public void onReceive(Context context, Intent intent) {


        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();


        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

//                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    // Show Alert
//                    Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, Toast.LENGTH_SHORT).show();

                    if (message.contains("SaftAlrtMsg"))
                    {
                        smsbody = message.split("/");

//                        Log.d("the 1",smsbody[1]);
//                        Log.d("the 2",smsbody[2]);

                        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(smsph, senderNum);
                        editor.putString(smsbody[1], smsla);
                        editor.putString(smsbody[2], smslo);

                        editor.apply();

                        String status = sharedPreferences.getString(networkState, "");

                        switch(status) {
                            case "Wifi enabled":
                            case "Mobile data enabled":
                                uploadAltMsg(Double.parseDouble(smsbody[1]),Double.parseDouble(smsbody[2]),senderNum,context);
                                break;
                            default:
                                // code block
                        }

                    }



                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

    private void uploadAltMsg(double lat, double lon, String smsph, Context context) {
        try
        {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(lat,lon,1);
            String address = addressList.get(0).getAddressLine(0);
//        Log.d("Address: ", address + "");

            if (address.length() != 0)
            {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Primary Alert");
                Map<String, Object> beacon = new HashMap<>();
                beacon.put("Phone", smsph);
                beacon.put("Latitude", lat);
                beacon.put("Longitude", lon);
                beacon.put("Address",address);
                beacon.put("ID",("SMS"));

                mDatabase.updateChildren(beacon);

                countDown(smsph);
            }


        }
        catch (Exception e)
        {
            Log.d("Exception", String.valueOf(e));
        }

    }

    private void countDown(String smsph) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reference = FirebaseDatabase.getInstance().getReference().child("Primary Alert");
                reference.child("Phone").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful())
                        {
                            Log.e("firebase", "Error getting data", task.getException());
                        }else
                        {
                            String beforePhonechange = String.valueOf(task.getResult().getValue());
                            if (smsph.equals(beforePhonechange))
                            {
                                Map<String, Object> beacon = new HashMap<>();
                                beacon.put("Phone", "");
                                beacon.put("Latitude", "");
                                beacon.put("Longitude", "");
                                beacon.put("Address","");
                                beacon.put("ID","");
                                reference.updateChildren(beacon);
                            }
                        }
                    }
                });
            }
        }, 30000);
    }
}
