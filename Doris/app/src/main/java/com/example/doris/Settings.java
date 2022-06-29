package com.example.doris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Settings extends AppCompatActivity {

    ListView sett_listView;
    public TextView textView;
    String[] languages = new String[]{"English","Tamil","Hindi","Bengali","Gujarati","Kannada","Malayalam","Marathi","Telugu","Urdu","Chinese", "French", "German", "Russian", "Spanish", "Turkish",
            "Afrikaans", "Amharic","Arabic","Basque","Bengali", "Bulgarian", "Catalan", "Cherokee","Croatian", "Czech", "Danish", "Dutch", "Estonian", "Filipino",
            "Finnish", "Greek", "Hebrew",  "Hungarian", "Icelandic", "Indonesian", "Italian", "Japanese", "Korean",
            "Latvian", "Lithuanian", "Malay", "Norwegian", "Polish", "Portuguese", "Romanian",
            "Serbian", "Slovak", "Slovenian", "Swahili", "Swedish", "Thai", "Ukrainian", "Vietnamese", "Zulu","Welsh"
    };
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String lang = "english";
    public String languageholder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        sett_listView = (ListView) findViewById(R.id.languagelist);
        textView = (TextView) findViewById(R.id.selectedlang);


        load();


        List<String> lang_list = new ArrayList<String>(Arrays.asList(languages));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lang_list);
        sett_listView.setAdapter(arrayAdapter);
        sett_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                textView.setText(selectedItem);
                gobacktomain(selectedItem);
            }
        });

    }

    private void load() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        languageholder = sharedPreferences.getString(lang, null);
        textView.setText(languageholder);
    }

    private void gobacktomain(String selectedItem) {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(lang, textView.getText().toString());
        editor.apply();

        Intent intent1 = new Intent(Settings.this, MainActivity.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);

    }
}



