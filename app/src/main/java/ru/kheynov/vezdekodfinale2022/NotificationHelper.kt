package ru.kheynov.vezdekodfinale2022

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(val context: Context) {
    companion object {
        const val CHANNEL_ID = "channelID"
        const val NOTIFICATION_ID = 101
    }

    private var builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("My notification")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentText("Пора покормить кота")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    fun showNotification() {
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}