package com.example.ai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class gui extends AppCompatActivity {

    ImageView frontbulb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui);


        frontbulb = (ImageView) findViewById(R.id.firstlight);

    }
}
