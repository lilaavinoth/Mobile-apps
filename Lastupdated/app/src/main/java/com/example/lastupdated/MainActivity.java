package com.example.lastupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView last;
    File lastModifiedFile;
    ArrayList<File> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        last = (TextView) findViewById(R.id.last);

        files = new ArrayList<>();

        String d = String.valueOf(Environment.getExternalStorageDirectory());
        files = listf(d, files);

        File finalmodified = checkLastmod(files);
        last.setText(finalmodified.getAbsolutePath());
    }

    private ArrayList<File> listf(String d, ArrayList<File> files) {
        File directory = new File(d);

        // get all the files from a directory
        File[] fList = directory.listFiles(new AudioFilter());
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
        return files;
    }

    private File checkLastmod(ArrayList<File> audiofiles) {
        long latmod = Long.MIN_VALUE;
        lastModifiedFile = null;
        for(File file: audiofiles)
        {
            if(file.lastModified() > latmod){
                lastModifiedFile = file;
                latmod = file.lastModified();
            }
        }
        return lastModifiedFile;
    }


    public class AudioFilter implements FileFilter {

        // only want to see the following audio file types
        private String[] extension = {".aac", ".mp3", ".wav", ".ogg", ".midi", ".3gp", ".mp4", ".m4a", ".amr", ".flac"};

        @Override
        public boolean accept(File pathname) {

            // if we are looking at a directory/file that's not hidden we want to see it so return TRUE
            if (pathname.isDirectory() && !pathname.isHidden()) {
                return true;
            }

            // loops through and determines the extension of all files in the directory
            // returns TRUE to only show the audio files defined in the String[] extension array
            for (String ext : extension) {
                if (pathname.getName().toLowerCase().endsWith(ext)) {
                    return true;
                }
            }

            return false;
        }

    }
}