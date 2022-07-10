package ru.kheynov.vezdekodfinale2022.data.db

import kotlinx.coroutines.flow.Flow

class AlarmRepository(
    private val alarmDAO: AlarmDAO,
) {
    val alarms: Flow<List<Alarm>> = alarmDAO.getAlarms()

    suspend fun insertAlarm(alarm: Alarm) {
        alarmDAO.insertAlarm(alarm)
    }

    suspend fun deleteAlarm(alarm: Alarm) {
        alarmDAO.deleteAlarm(alarm)
    }
}