package com.example.orientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Gyroscope {
    public interface Listener
    {
        void onRotation(float tx, float ty, float tz);
    }

    private Acclerometer.Listener listener;

    public void setListener(Acclerometer.Listener listener2)
    {
        listener = listener2;
    }


    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;


    Gyroscope(Context context)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (listener  != null)
                {
                    listener.onTranslation(sensorEvent.values[0],sensorEvent.values[1], sensorEvent.values[2]);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }
    public void register()
    {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister()

    {
        sensorManager.unregisterListener(sensorEventListener);
    }

}
