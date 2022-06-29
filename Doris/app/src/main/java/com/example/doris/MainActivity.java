package com.example.doris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.example.doris.Settings.lang;

public class MainActivity extends AppCompatActivity {
    Button micbtn,settbtn,realtimebtn;
    RelativeLayout relativeLayout;
    Intent speechrecognizerIntent;
    SpeechRecognizer speechRecognizer;
    String recoginizedtext = "";
    TextView textView,textview_rec_mess,translated_mess;
    String selected_lang,lang_code;
    int translator_lang_code;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,special,special_window;
    TextToSpeech textToSpeech;
    VideoView videoView;
    public final Bundle params = new Bundle();
    MediaPlayer mediaPlayer;


    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaPlayer mediaPlayer;
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.kong));
        }
        setContentView(R.layout.activity_main);

        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);

        permissionchecker();

        micbtn = findViewById(R.id.mic);
        settbtn = findViewById(R.id.settingbtn);
        realtimebtn = findViewById(R.id.realtime);
        textView = findViewById(R.id.dsfd);
        textview_rec_mess = findViewById(R.id.rec_mess);
        translated_mess = findViewById(R.id.translated_mess);
        relativeLayout = findViewById(R.id.relativelayout);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        speechrecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechrecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
        lang_updater();
        speechrecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang_code);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("My input");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("AI output");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Trip Data");
        special = FirebaseDatabase.getInstance().getReference().child("Special");
        special_window = FirebaseDatabase.getInstance().getReference().child("Special_window");
        videoView = (VideoView) findViewById(R.id.videotalkback);


        final Uri uri1 = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.blank);
        videoView.setVideoURI(uri1);
        videoView.start();

        settbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.doris.Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
            }
        });

        micbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videolisten();
                speechRecognizer.startListening(speechrecognizerIntent);
                recoginizedtext = "";

            }
        });

        realtimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,com.example.doris.Realtime.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left,R.anim.slide_out_right);
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                blankvideo(uri1);

            }

            @Override
            public void onResults(Bundle results) {


                blankvideo(uri1);
                ArrayList<String> thetext = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if (thetext != null) {
                    recoginizedtext = thetext.get(0);

                    textview_rec_mess.setText(recoginizedtext);

                    upload(recoginizedtext);

                    //translator();



                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });






//        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
////                    case MotionEvent.ACTION_DOWN:
////                        speechRecognizer.startListening(speechrecognizerIntent);
////                    recoginizedtext = "";
////                        break;
//                    case MotionEvent.ACTION_UP:
//                        speechRecognizer.stopListening();
//                        break;
//                }
//                return false;
//            }
//        });

        load();

        read_reply();

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }

            }
        });

        theftdetector();
        windowdetector();
        accidentdectector();
        frontrightdetector();
        bootdetector();
        frontleftdetector();
        rearrightdetector();
        rearleftdetector();
    }

    private void accidentdectector() {
        final DatabaseReference boot;
        boot = FirebaseDatabase.getInstance().getReference().child("Special_Accident");
        boot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String windowdata = snapshot.child("AlertWindow").getValue().toString();
                if(windowdata.equals("1")){
                    accidentdectecto();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void accidentdectecto() {

        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.accident);

        }
        mediaPlayer.start();
        accidentDialog dialog = new accidentDialog();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }

    private void rearleftdetector() {
        DatabaseReference boot;
        boot = FirebaseDatabase.getInstance().getReference().child("Special_rldw");
        boot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String windowdata = snapshot.child("rearleftdoorwarning").getValue().toString();
                if(windowdata.equals("1")){
                    rearleftdetecto();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void rearleftdetecto() {
        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.rearleftdoor_01);

        }
        mediaPlayer.start();
        RearLeftDoorOpen dialog = new RearLeftDoorOpen();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }

    private void rearrightdetector() {
        DatabaseReference boot;
        boot = FirebaseDatabase.getInstance().getReference().child("Special_rrdw");
        boot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String windowdata = snapshot.child("rearrightdoorwarning").getValue().toString();
                if(windowdata.equals("1")){
                    rearrightdetecto();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void rearrightdetecto() {
        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.rearrightdoor_01);

        }
        mediaPlayer.start();
        RearRightDoorOpen dialog = new RearRightDoorOpen();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }

    private void frontleftdetector() {

        DatabaseReference boot;
        boot = FirebaseDatabase.getInstance().getReference().child("Special_fldw");
        boot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String windowdata = snapshot.child("frontleftdoorwarning").getValue().toString();
                if(windowdata.equals("1")){
                    frontleftdetecto();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void frontleftdetecto() {
        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.frontleftdoor_01);

        }
        mediaPlayer.start();
        FrontLeftDoorOpenDialog dialog = new FrontLeftDoorOpenDialog();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }

    private void frontrightdetector() {
        DatabaseReference boot;
        boot = FirebaseDatabase.getInstance().getReference().child("Special_frdw");
        boot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String windowdata = snapshot.child("frontrightdoorwarning").getValue().toString();
                if(windowdata.equals("1")){
                    frontrightdetecto();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void frontrightdetecto() {
        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.frontrightdoor_01);

        }
        mediaPlayer.start();
        FrontRightDoorOpenDialog dialog = new FrontRightDoorOpenDialog();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }


    private void bootdetector() {
        DatabaseReference boot;
        boot = FirebaseDatabase.getInstance().getReference().child("Special");
        boot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String windowdata = snapshot.child("BootAlert").getValue().toString();
                if(windowdata.equals("1")){
                    openBootDialog();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openBootDialog() {


        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.boot);

        }
        mediaPlayer.start();
        BootDialog dialog = new BootDialog();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }

    private void windowdetector() {

        special_window.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String windowdata = snapshot.child("AlertWindow").getValue().toString();
                if(windowdata.equals("1")){
                    openWindowDialog();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                        Toast.makeText(MainActivity.this, "else part", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openWindowDialog() {
        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.windos);
        }
        mediaPlayer.start();
        WindowDialog dialog = new WindowDialog();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }

    private void theftdetector() {
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String theftdata = snapshot.child("Theft_Detection").getValue().toString();
                if(theftdata.equals("1")){
                    openDialog();
                }
                else {
                    if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialog() {
        if (mediaPlayer == null){
            Context context;
            mediaPlayer = MediaPlayer.create(this, R.raw.warn);
        }
        mediaPlayer.start();
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"Example dialog");
    }


    private void blankvideo(Uri uri1) {
        videoView.setVideoURI(uri1);
        videoView.start();
    }



    private void videolisten() {
        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.listen);
        videoView.setVideoURI(uri);
        videoView.start();
    }


    private void txtotspeech() {
        Uri uri2 = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.talkback);
        videoView.setVideoURI(uri2);
        videoView.start();

        String text = translated_mess.getText().toString();


        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {

            }

            @Override
            public void onDone(String s) {
                Uri uri = Uri.parse("android.resource://"
                        + getPackageName()
                        + "/"
                        + R.raw.blank);
                videoView.setVideoURI(uri);
                videoView.start();

//                check_countinue();


            }

            @Override
            public void onError(String s) {

            }
        });



        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,params,text);


    }



//    private void check_countinue() {
//        databaseReference2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull final DataSnapshot snapshot) {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        String state = snapshot.child("state").getValue().toString();
//                        if (state.equals("1"))
//                        {
//                            Handler handler2 = new Handler();
//                            handler2.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    micbtn.performClick();
//
//                                    HashMap<String,Object> state_change = new HashMap<>();
//                                    state_change.put("state","0");
//                                    databaseReference2.updateChildren(state_change);
//                                }
//                            }, 1000);
//                        }
//                    }
//                }, 1000);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }




    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {

            Toast.makeText(this, "on destroy", Toast.LENGTH_SHORT).show();
            textToSpeech.stop();
            textToSpeech.shutdown();

        }
        super.onDestroy();
    }

    private void read_reply() {

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                translated_mess.setText(snapshot.child("output").getValue().toString());
                txtotspeech();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void upload(String recoginizedtext) {


        HashMap<String,Object> voice = new HashMap<>();
        voice.put("input",recoginizedtext);
        voice.put("lang",lang_code);
        databaseReference.updateChildren(voice);


    }


    private void load() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        selected_lang = sharedPreferences.getString(lang, "");
        textView.setText(selected_lang);

    }


    private void permissionchecker() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED))
            {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    private void lang_updater() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        selected_lang = sharedPreferences.getString(lang, "");

        switch (selected_lang)
        {
            case "English":
                lang_code = "en";
                //translator_lang_code = FirebaseTranslateLanguage.EN;
                break;
            case "Tamil":
                lang_code = "ta";
                //translator_lang_code = FirebaseTranslateLanguage.EN;
                break;
            case "Chinese":
                lang_code = "zh-CN";
                //translator_lang_code = FirebaseTranslateLanguage.ZH;
                break;
            case "French":
                lang_code = "fr";
                //translator_lang_code = FirebaseTranslateLanguage.FR;
                break;
            case "German":
                lang_code = "de";
                //translator_lang_code = FirebaseTranslateLanguage.DE;
                break;
            case "Russian":
                lang_code = "ru";
                //translator_lang_code = FirebaseTranslateLanguage.RU;
                break;
            case "Spanish":
                lang_code = "es";
                //translator_lang_code = FirebaseTranslateLanguage.ES;
                break;
            case "Turkish":
                lang_code = "tr";
                //translator_lang_code = FirebaseTranslateLanguage.TR;
                break;
            case "Afrikaans":
                lang_code = "af";
                //translator_lang_code = FirebaseTranslateLanguage.AF;
                break;
            case "Amharic":
                lang_code = "am";
                break;
            case "Bulgarian":
                lang_code = "bg";
                break;
            case "Catalan":
                lang_code = "ca";
                break;
            case "Croatian":
                lang_code = "hr";
                break;
            case "Czech":
                lang_code = "cs";
                break;
            case "Danish":
                lang_code = "da";
                break;
            case "Dutch":
                lang_code = "nl";
                break;
            case "Estonian":
                lang_code = "et";
                break;
            case "Filipino":
                lang_code = "fil";
                break;
            case "Finnish":
                lang_code = "fi";
                break;
            case "Greek":
                lang_code = "el";
                break;
            case "Hebrew":
                lang_code = "iw";
                break;
            case "Hindi":
                lang_code = "hi";
                break;
            case "Hungarian":
                lang_code = "hu";
                break;
            case "Icelandic":
                lang_code = "is";
                break;
            case "Indonesian":
                lang_code = "id";
                break;
            case "Italian":
                lang_code = "it";
                break;
            case "Japanese":
                lang_code = "ja";
                break;
            case "Korean":
                lang_code = "ko";
                break;
            case "Latvian":
                lang_code = "lv";
                break;
            case "Lithuanian":
                lang_code = "lt";
                break;
            case "Malay":
                lang_code = "ms";
                break;
            case "Norwegian":
                lang_code = "no";
                break;
            case "Polish":
                lang_code = "pl";
                break;
            case "Portuguese":
                lang_code = "pt-PT";
                break;
            case "Romanian":
                lang_code = "ro";
                break;
            case "Serbian":
                lang_code = "sr";
                break;
            case "Slovak":
                lang_code = "sk";
                break;
            case "Slovenian":
                lang_code = "sl";
                break;
            case "Swahili":
                lang_code = "sw";
                break;
            case "Swedish":
                lang_code = "sv";
                break;
            case "Thai":
                lang_code = "th";
                break;
            case "Ukrainian":
                lang_code = "uk";
                break;
            case "Vietnamese":
                lang_code = "vi";
                break;
            case "Zulu":
                lang_code = "zu";
                break;
            case "Arabic":
                lang_code = "ar";
                break;
            case "Basque":
                lang_code = "eu";
                break;
            case "Bengali":
                lang_code = "bn";
                break;
            case "Cherokee":
                lang_code = "chr";
                break;
            case "Gujarati":
                lang_code = "gu";
                break;
            case "Kannada":
                lang_code = "kn";
                break;
            case "Malayalam":
                lang_code = "ml";
                break;
            case "Marathi":
                lang_code = "mr";
                break;
            case "Telugu":
                lang_code = "te";
                break;
            case "Urdu":
                lang_code = "ur";
                break;
            case "Welsh":
                lang_code = "cy";
                break;

        }

    }

}