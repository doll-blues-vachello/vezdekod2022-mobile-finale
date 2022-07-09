package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_clock

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
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_scheduler.AlarmScheduler

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
            topBar = {/**/ },
            drawerContent = {/**/ },
            bottomBar = {/**/ },
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
    }
}