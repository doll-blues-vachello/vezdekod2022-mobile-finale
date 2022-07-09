package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_clock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kheynov.vezdekodfinale2022.AlarmBroadcastReceiver

private const val TAG = "AlarmScreen"

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AlarmScreen(
    viewModel: AlarmScreenViewModel = hiltViewModel(),
) {
    val alarms = viewModel.alarms.observeAsState()

    val showAlarmScheduler = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, AlarmBroadcastReceiver::class.java)

    lateinit var pendingIntent: PendingIntent

    Column() {
        Button(onClick = {
            val calendar = Calendar.getInstance()
//            calendar.set(Calendar.HOUR_OF_DAY, alarms.value?.get(0)?.time?.split(":")!![0].toInt())
//            calendar.set(Calendar.HOUR_OF_DAY, alarms.value?.get(0)?.time?.split(":")!![1].toInt())
            intent.putExtra("state", "alarm_on")
            pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, 5000, pendingIntent)
        }) {
            Text(text = "Установить будильник")
        }
        Button(onClick = {
            alarmManager.cancel(pendingIntent)
            intent.putExtra("state", "alarm_off")
            context.sendBroadcast(intent)
        }) {
            Text(text = "Выключить будильник")
        }

    }

    /*   if (showAlarmScheduler.value)
           AlarmScheduler(onSchedule = { alarm ->
               showAlarmScheduler.value = false
               viewModel.scheduleAlarm(alarm)
               Log.i(TAG, "time: ${alarm.time}, date: ${alarm.date}")
           },
               onDismiss = { showAlarmScheduler.value = false }
           )
       else {
           Scaffold(
               topBar = {*//**//* },
            drawerContent = {*//**//* },
            bottomBar = {*//**//* },
            floatingActionButton = {
                FloatingActionButton(onClick = { showAlarmScheduler.value = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "")
                }
            },
            content = { padding ->
                LazyColumn(modifier = Modifier.padding(padding)
                ) {
                    items(alarms.value ?: emptyList()) {
                        AlarmListItem(alarm = it, onRemove = { viewModel.removeAlarm(it) })
                    }
                }
            }
        )
    }*/
}