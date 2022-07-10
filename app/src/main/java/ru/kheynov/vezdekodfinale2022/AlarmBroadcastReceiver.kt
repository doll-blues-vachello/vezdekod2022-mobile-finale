package ru.kheynov.vezdekodfinale2022

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.kheynov.vezdekodfinale2022.presentation.QuizActivity


class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val state = intent?.extras!!.getString("state")

        val serviceIntent = Intent(context, RingtonePlayingService::class.java)

        serviceIntent.putExtra("state", state)

        context?.startService(serviceIntent)

        if (state == "alarm_on") {
            val i = Intent(context?.applicationContext, QuizActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context!!.startActivity(i)
        }

    }
}