package ru.kheynov.vezdekodfinale2022

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi

private const val TAG = "RingtoneService"


class RingtonePlayingService : Service() {
    private var mediaSong: MediaPlayer? = null
    private var isLaunchedBefore = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val state = intent.extras!!.getString("state")
        isLaunchedBefore = startId % 2
        Log.i(TAG, "onStartCommand: state: $state")

        val notifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?

        val intentStopAlarm = Intent(this.applicationContext, AlarmBroadcastReceiver::class.java)
        intentStopAlarm.putExtra("state", "alarm_off")

        val pendingIntentStopAlarm = PendingIntent.getBroadcast(this, 0,
            intentStopAlarm, FLAG_IMMUTABLE)

        val notificationPopup: Notification = Notification.Builder(this, "channelID")
            .setContentTitle("An alarm is going off!")
            .setContentText("Click me!")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntentStopAlarm)
            .setAutoCancel(true)
            .build()
        assert(state != null)

        if (state == "alarm_on") {
            notifyManager!!.notify(0, notificationPopup)
            mediaSong = MediaPlayer.create(this, R.raw.alarm).also { it.isLooping = true }
            mediaSong!!.start()
        } else {
            mediaSong!!.stop()
            mediaSong!!.reset()
        }

        return START_NOT_STICKY
    }
}
