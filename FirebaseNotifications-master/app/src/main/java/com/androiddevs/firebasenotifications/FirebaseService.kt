package com.androiddevs.firebasenotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random


private const val CHANNEL_ID = "my_channel"

private lateinit var mediaPlayer: MediaPlayer
private lateinit var runnable:Runnable
private var handler: Handler = Handler()
private var pause:Boolean = false



class FirebaseService : FirebaseMessagingService() {

    companion object {
        var sharedPref: SharedPreferences? = null

        var token: String?
        get() {
            return sharedPref?.getString("token", "")
        }
        set(value) {
            sharedPref?.edit()?.putString("token", value)?.apply()
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }

//    val soundUri: Uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.alarm_safety);
//
//    val VIBRATE_PATTERN = longArrayOf(0, 500)


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        var sounder: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.alarm_safety)

        
//        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.alarm_safety)
//        mediaPlayer.start()
//        Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()

//        if(pause){
//            mediaPlayer.seekTo(mediaPlayer.currentPosition)
//            mediaPlayer.start()
//            pause = false
//            Toast.makeText(this,"media playing",Toast.LENGTH_SHORT).show()
//        }else{
//
//
//
//        }




        val intent = Intent(this, MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)

        }



        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSound(sounder)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)

    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {

        var sounder: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.alarm_safety)
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            enableVibration(false)
            setSound(sounder, audioAttributes)
        }
        notificationManager.createNotificationChannel(channel)
    }

}