package com.example.allmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public static final int permission = 1;
    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    public String  currentlocation,currenttitle;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.lastitem);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this
            ,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]
                                {
                                        Manifest.permission.READ_EXTERNAL_STORAGE },permission);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]
                                {
                                        Manifest.permission.READ_EXTERNAL_STORAGE },permission);
            }
        }else {
            dostuff();



            //final String lastlocation = arrayList.get(arrayList.size()-1);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    playlastmusic();

//                    String pathway = Environment.getExternalStorageDirectory().getPath() + "/Record/PhoneRecord/";
//                    pathway = pathway+ currenttitle + ".aac";
//                    Toast.makeText(MainActivity.this,  pathway, Toast.LENGTH_SHORT).show();
//                    File file = new File(pathway);
//                    MediaPlayer mp = MediaPlayer.create(this,Uri.parse(pathway));
//                    mp.start();



                }
            });



        }

    }

    private void playlastmusic() {




        String pathway = Environment.getExternalStorageDirectory().getPath() + "/Record/PhoneRecord/";
        pathway = pathway+ currenttitle + ".aac";
        Log.d("main","Path: " + pathway);
        File file = new File(pathway);
        Log.d("Main", "Voice exists: " + file.exists() + " can read : " + file.canRead());
        MediaPlayer mediaPlayer = MediaPlayer.create(this,Uri.parse(pathway));
        mediaPlayer.start();
        Toast.makeText(this, pathway, Toast.LENGTH_SHORT).show();
    }

    private void dostuff() {

        listView = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<>();
        getmusic();
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);







    }

    private void getmusic() {

        ContentResolver contentResolver = getContentResolver();
        Uri songsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songcursor = contentResolver.query(songsuri,null,null,null,null);

        if (songcursor != null && songcursor.moveToFirst())
        {
            int songtitle = songcursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songlocation = songcursor.getColumnIndex(MediaStore.Audio.Media.DATA);


            do {
                currenttitle = songcursor.getString(songtitle);
                currentlocation = songcursor.getString(songlocation);
                arrayList.add(currenttitle + "\n" + currentlocation);


            }while (songcursor.moveToNext());



        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case (permission): {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        dostuff();
                    }
                }
                else {
                    Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;

            }



    }}
}