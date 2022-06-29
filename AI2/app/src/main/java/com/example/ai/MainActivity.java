package com.example.ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.ai.Settings.lang;

public class MainActivity extends AppCompatActivity {

    Button micbtn,settbtn;
    RelativeLayout relativeLayout;
    Intent speechrecognizerIntent;
    SpeechRecognizer speechRecognizer;
    String recoginizedtext = "";
    TextView textView,textview_rec_mess,translated_mess;
    String selected_lang,lang_code;
    int translator_lang_code;
    DatabaseReference databaseReference,databaseReference1;
    TextToSpeech textToSpeech;


    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        permissionchecker();

        micbtn = findViewById(R.id.mic);
        settbtn = findViewById(R.id.settingbtn);
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



        settbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.ai.Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
            }
        });

        micbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speechRecognizer.startListening(speechrecognizerIntent);
                recoginizedtext = "";

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

            }

            @Override
            public void onResults(Bundle results) {

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




        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        speechRecognizer.startListening(speechrecognizerIntent);
//                    recoginizedtext = "";
//                        break;
                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        break;
                }
                return false;
            }
        });

        load();

        read_reply();

        textchangedlistener();

    }

    private void textchangedlistener() {
        translated_mess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtotspeech();
            }
        });
    }

    private void txtotspeech() {
        textToSpeech = new TextToSpeech(this, new OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TXTtoSpeech","lang not supported");
                    }else {
                        Log.e("TXTtoSpeech","lang supported");
                    }

                }
            }
        });
    }

    private void read_reply() {

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                translated_mess.setText(snapshot.child("output").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void upload(String recoginizedtext) {


        HashMap<String,Object> voice = new HashMap<>();
        voice.put("input",recoginizedtext);
        databaseReference.updateChildren(voice);


    }

    private void translator() {
        // Create an English-German translator:

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(translator_lang_code)
                        .setTargetLanguage(FirebaseTranslateLanguage.EN)
                        .build();
        final FirebaseTranslator englishGermanTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);
        englishGermanTranslator.translate(recoginizedtext)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                translated_mess.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                            }
                        });
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
                lang_code = "en-US";
                //translator_lang_code = FirebaseTranslateLanguage.EN;
                break;
            case "Chinese":
                lang_code = "zh-HK";
                //translator_lang_code = FirebaseTranslateLanguage.ZH;
                break;
            case "French":
                lang_code = "fr-FR";
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
                lang_code = "es-ES";
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
                lang_code = "he";
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

        }

    }

}