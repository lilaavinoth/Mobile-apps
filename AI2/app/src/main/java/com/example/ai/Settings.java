package com.example.ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Settings extends AppCompatActivity {


    ListView sett_listView;
    public TextView textView;
    String[] languages = new String[] {"English", "Chinese", "French", "German","Russian","Spanish","Turkish",
            "Afrikaans","Amharic","Bulgarian","Catalan","Croatian","Czech","Danish","Dutch","Estonian","Filipino",
            "Finnish","Greek","Hebrew","Hindi","Hungarian","Icelandic","Indonesian","Italian","Japanese","Korean",
            "Latvian","Lithuanian","Malay","Norwegian","Polish","Portuguese","Romanian",
            "Serbian","Slovak","Slovenian","Swahili","Swedish","Thai","Ukrainian","Vietnamese","Zulu"
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

        Intent intent1 = new Intent(Settings.this,MainActivity.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.slide_left,R.anim.slide_out_right);

    }



}