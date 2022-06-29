package com.example.naturalvoice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button spkbtn;
    TextView spoken;
    Random random;
    MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spkbtn = (Button) findViewById(R.id.speakbtn);
        spoken = (TextView) findViewById(R.id.spokentext);
        random = new Random();





        spkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                speakCustomVoice();






            }


        });



    }

    private void hearvoice() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak up");

        try {
            startActivityForResult(intent,1);
        }catch (ActivityNotFoundException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void speakCustomVoice() {
        int randomno = random.nextInt(4) + 1;
        switch (randomno)
        {
            case 1:
                if (mediaPlayer == null)
                {
                    mediaPlayer = MediaPlayer.create(this, R.raw.one);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            hearvoice();
                        }
                    });

                }
                break;
            case 2:
                if (mediaPlayer == null)
                {
                    mediaPlayer = MediaPlayer.create(this, R.raw.two);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            hearvoice();
                        }
                    });
                }
                break;
            case 3:
                if (mediaPlayer == null)
                {
                    mediaPlayer = MediaPlayer.create(this, R.raw.three);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            hearvoice();
                        }
                    });
                }
                break;
            case 4:
                if (mediaPlayer == null)
                {
                    mediaPlayer = MediaPlayer.create(this, R.raw.four);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            hearvoice();
                        }
                    });
                }
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    spoken.setText(result.get(0));
                }
                    break;
        }
    }
}