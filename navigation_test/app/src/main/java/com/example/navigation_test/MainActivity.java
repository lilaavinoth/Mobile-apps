package com.example.navigation_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent buttonAccept = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+10.835088+", "+78.668427+"&mode=1"));
                buttonAccept.setPackage("com.google.android.apps.maps");
                startActivity(buttonAccept);

            }
        });
    }
}