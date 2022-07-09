package ru.kheynov.vezdekodfinale2022

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val state = intent?.extras!!.getString("state")

        val serviceIntent = Intent(context, RingtonePlayingService::class.java)

        serviceIntent.putExtra("state", state)

        context?.startService(serviceIntent)

    }
}