package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_scheduler

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.kheynov.vezdekodfinale2022.data.db.Alarm

private const val TAG = "AlarmScheduler"

@Composable
fun AlarmScheduler(
    onSchedule: (Alarm) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Button(onClick = {
            Log.i(TAG, "AlarmScheduler: dateTimePicker toggled!")
        }) {
            Text(text = "Выберите время будильника")
        }
    }
}
