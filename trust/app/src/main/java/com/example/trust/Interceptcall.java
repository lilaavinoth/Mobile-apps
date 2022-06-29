package com.example.trust;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Interceptcall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Toast.makeText(context, "ringing", Toast.LENGTH_SHORT).show();
            recordaudio();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void recordaudio() {
    }
}
