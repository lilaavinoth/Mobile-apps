package com.example.whatsappreply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    
    EditText editText;
    Button startbtn,stopbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editText = (EditText) findViewById(R.id.edittextinput);
        startbtn = (Button) findViewById(R.id.start);
        stopbtn = (Button) findViewById(R.id.stop);
        
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startservicenow();
            }
        });
        
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopservicenow();
            }
        });
        
    }



    private void startservicenow() {

        String input = editText.getText().toString();

        Intent serviceintent = new Intent(this,ExampleService.class);
        serviceintent.putExtra("inputextra",input);
        startService(serviceintent);

    }

    private void stopservicenow() {
        Intent serviceintent = new Intent(this,ExampleService.class);
        startService(serviceintent);


    }
    
    
}