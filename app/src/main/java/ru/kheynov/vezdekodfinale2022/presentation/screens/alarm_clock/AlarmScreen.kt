package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_clock

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kheynov.vezdekodfinale2022.data.db.Alarm

@Composable
fun AlarmScreen(
    viewModel: AlarmScreenViewModel = hiltViewModel(),
) {
    val alarms = viewModel.alarms.observeAsState()
    Button(onClick = {
        viewModel.scheduleAlarm(
            Alarm(
                id = 1, //ONLY FOR ONE ALARM CLOCK
                time = System.currentTimeMillis(),
                date = 45345635,
            )
        )
    }) {

    }
}