package ru.kheynov.vezdekodfinale2022.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Alarm::class],
    version = 1,
    exportSchema = false,
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val alarmDAO: AlarmDAO

    companion object {
        const val DATABASE_NAME = "alarms"
    }
}