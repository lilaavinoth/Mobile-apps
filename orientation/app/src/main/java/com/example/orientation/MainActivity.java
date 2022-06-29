package com.example.orientation;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Acclerometer acclerometer;
    private Gyroscope gyroscope;
    TextView x,y,z;

    @Override
    protected void onPostResume() {
        super.onPostResume();

        acclerometer.register();
        gyroscope.register();

    }

    @Override
    protected void onPause() {
        super.onPause();

        acclerometer.register();
        gyroscope.register();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = findViewById(R.id.xaxisvalue);
        y = findViewById(R.id.yaxisvalue);
        z = findViewById(R.id.zaxisvalue);

        acclerometer = new Acclerometer(this);
        gyroscope = new Gyroscope(this);

        acclerometer.setListener(new Acclerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                if (tx > 1.0f)
                {
//                    Log.d("X axis:", String.valueOf(tx));
//                    Log.d("Y axis:", String.valueOf(ty));
//                    Log.d("Z axis:", String.valueOf(tz));
                }
                else if (tx < -1.0f)
                {

                }

            }
        });

        gyroscope.setListener(new Acclerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                Log.d("X axis:", String.valueOf(tx));
                Log.d("Y axis:", String.valueOf(ty));
                Log.d("Z axis:", String.valueOf(tz));

                x.setText(String.valueOf(tx));
                y.setText(String.valueOf(ty));
                z.setText(String.valueOf(tz));

            }
        });




    }
}