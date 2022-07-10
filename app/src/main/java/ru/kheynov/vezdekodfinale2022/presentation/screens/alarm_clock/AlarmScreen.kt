package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_clock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kheynov.vezdekodfinale2022.AlarmBroadcastReceiver
import ru.kheynov.vezdekodfinale2022.data.db.Alarm
import ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_scheduler.AlarmScheduler

private const val TAG = "AlarmScreen"

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AlarmScreen(
    viewModel: AlarmScreenViewModel = hiltViewModel(),
) {
    val alarms = viewModel.alarms.observeAsState()

    val showAlarmScheduler = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current
    viewModel.alarms.observeForever { alarmsList ->
        alarmsList.forEach { alarm ->
            if (alarm.enabled) setAlarm(alarm, context = context)
            else resetAlarm(alarm, context = context)
        }
    }

    if (showAlarmScheduler.value)
        AlarmScheduler(onSchedule = { alarm ->
            showAlarmScheduler.value = false
            viewModel.scheduleAlarm(alarm)
            Log.i(TAG, "time: ${alarm.time}, date: ${alarm.date}")
        },
            onDismiss = { showAlarmScheduler.value = false }
        )
    else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { showAlarmScheduler.value = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "")
                }
            },
            content = { padding ->
                LazyColumn(modifier = Modifier.padding(padding)
                ) {
                    items(alarms.value ?: emptyList()) { alarm ->
                        AlarmListItem(alarm = alarm, onRemove = { viewModel.removeAlarm(alarm) },
                            isEnabled = alarm.enabled,
                            onEnabled = { viewModel.scheduleAlarm(alarm.copy(enabled = it)) })
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun setAlarm(alarm: Alarm, context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmBroadcastReceiver::class.java)
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, alarm.time.split(":")[0].toInt())
    calendar.set(Calendar.MINUTE, alarm.time.split(":")[1].toInt())
    calendar.set(Calendar.SECOND, 0)

    Log.i(TAG, "setAlarm: ${calendar.time}")
    intent.putExtra("state", "alarm_on")
    intent.putExtra("id", alarm.id)
    val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}

@RequiresApi(Build.VERSION_CODES.S)
private fun resetAlarm(alarm: Alarm, context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmBroadcastReceiver::class.java)
    intent.putExtra("id", alarm.id)
    intent.putExtra("state", "alarm_off")
    val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    alarmManager.cancel(pendingIntent)
    context.sendBroadcast(intent)
}