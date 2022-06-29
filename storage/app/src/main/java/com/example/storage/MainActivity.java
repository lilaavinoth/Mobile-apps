package com.example.storage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button Button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button = (Button) findViewById(R.id.btn);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tooglebtn(view);

            }
        });
    }



    private void tooglebtn(View view) {
        Intent intent = new Intent(MainActivity.this,service.class);
        startService(intent);
        Toast.makeText(this, "startesd !!!", Toast.LENGTH_SHORT).show();

    }
        }



