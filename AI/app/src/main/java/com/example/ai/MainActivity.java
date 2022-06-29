package com.example.ai;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button speakbtn,gui;
    TextView checker,result,finaltext;
    ArrayList <String> recievedtext;
    String finalcommand,aireply,finalcommandtest;
    String stage;
    TextToSpeech textToSpeech;


    @Override
    protected void onStart() {
        super.onStart();
        //result.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result);

        checker = (TextView) findViewById(R.id.checker);
        speakbtn = (Button) findViewById(R.id.speakbtn);
        finaltext = (TextView) findViewById(R.id.finaltext);
        gui = (Button) findViewById(R.id.gui);

        finalcommandtest = "onoroff room lightorfan position";



        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS)
                {
                    int result = textToSpeech.setLanguage(Locale.UK);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.e("tts","Language not available");
                    }

                }
                else {
                    Log.e("tts","Initialization Failed");
                }
            }

        });


        speakbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                resetspeech();

                try {
                    startActivityForResult(intent, 10);
                }
                catch (ActivityNotFoundException a)
                {}
            }
        });

        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,gui.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 10: {
                if (resultCode == RESULT_OK && null != data)
                {
                    recievedtext = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    result.setText(recievedtext.get(0));
                    finalcommand = result.getText().toString();


                }
                checkertest();
            }

        }

    }



    private void voicereply() {

        textToSpeech.speak(aireply, TextToSpeech.QUEUE_FLUSH, null);

    }



    private void checkertest()
    {
        if (finalcommand.contains("on"))
        {
            finalcommandtest = finalcommandtest.replaceAll("onoroff","on");
        }
        if (finalcommand.contains("light"))
        {
            finalcommandtest = finalcommandtest.replaceAll("lightorfan","light");
        }
        if (finalcommand.contains("hall"))
        {
            finalcommandtest = finalcommandtest.replaceAll("room","hall");
        }
        if (finalcommand.contains("first"))
        {
            finalcommandtest = finalcommandtest.replaceAll("position", "first");
        }
        if (finalcommand.contains("off"))
        {
            finalcommandtest = finalcommandtest.replaceAll("onoroff","off");
        }
        if (finalcommand.contains("check") || finalcommand.contains("is"))
        {
            finalcommandtest = finalcommandtest.replaceAll("onoroff","check");
        }
        if (finalcommand.contains("second"))
        {
            finalcommandtest = finalcommandtest.replaceAll("position","second");
        }


        HallLight();


    }


    private void HallLight() {

        if (finalcommandtest.contains("on") && finalcommandtest.contains("hall") && finalcommandtest.contains("light") && finalcommandtest.contains("first"))
        {
            checker.setText("i'll turn on the first light in the hall for you");
            String stage = "1";
            aireply = checker.getText().toString();
            voicereply();

            finaltext.setText(finalcommandtest);
        }
        else if (finalcommandtest.contains("on") && finalcommandtest.contains("hall") && finalcommandtest.contains("light") && finalcommandtest.contains("second"))
        {
            checker.setText("i'll turn on the second light in the hall for you");
            stage = "1";
            aireply = checker.getText().toString();
            voicereply();

            finaltext.setText(finalcommandtest);
        }
        if (finalcommandtest.contains("off") && finalcommandtest.contains("hall") && finalcommandtest.contains("light") && finalcommandtest.contains("first"))
        {
            checker.setText("i'll turn off the first light in the hall for you");
            stage = "0";
            aireply = checker.getText().toString();
            voicereply();

            finaltext.setText(finalcommandtest);
        }
        if (finalcommandtest.contains("onoroff") || finalcommandtest.contains("room") || finalcommandtest.contains("lightorfan") || finalcommandtest.contains("position"))
        {
            checker.setText("given command is not enough to make decisions");
            aireply = checker.getText().toString();
            voicereply();

            finaltext.setText(finalcommandtest);
        }
        if (finalcommandtest.contains("check") && finalcommandtest.contains("hall") && finalcommandtest.contains("light") && finalcommandtest.contains("first"))
        {

            if (stage.contains("1"))
            {
                checker.setText("the first hall light is still on");
                aireply = checker.getText().toString();
                voicereply();
                finaltext.setText(finalcommandtest);
            }
            if (stage.contains("0"))
            {
                checker.setText("the first hall light is off");
                aireply = checker.getText().toString();
                voicereply();
                finaltext.setText(finalcommandtest);
            }

        }

    }

    private void resetspeech()
    {
        finalcommandtest = "onoroff room lightorfan position";
    }



    @Override
    protected void onDestroy() {

        if (textToSpeech != null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }
}
