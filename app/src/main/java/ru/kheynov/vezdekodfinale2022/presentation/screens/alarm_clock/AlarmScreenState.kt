package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_clock

import ru.kheynov.vezdekodfinale2022.data.db.Alarm

sealed interface AlarmScreenState {
    object Loading : AlarmScreenState
    data class Loaded(val alarms: List<Alarm>) : AlarmScreenState
    object Error : AlarmScreenState
}