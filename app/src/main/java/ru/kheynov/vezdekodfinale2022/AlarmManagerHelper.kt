package ru.kheynov.vezdekodfinale2022

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.IntentFilter

class AlarmManagerHelper(context: Context) {
    private lateinit var alarmManager: AlarmManager
    private val broadcastReceiver = AlarmBroadcastReceiver()

    private val intent: Intent = Intent("alarm")

    init {
        alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        context.registerReceiver(broadcastReceiver, IntentFilter("alarm"))
        val pendingIntent = PendingIntent.getBroadcast(context, 1234, intent, 0)

    }
}