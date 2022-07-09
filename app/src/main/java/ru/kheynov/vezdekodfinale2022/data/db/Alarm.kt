package ru.kheynov.vezdekodfinale2022.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "time") val time: Long = 0,
    @ColumnInfo(name = "date") val date: Long = 0,
)