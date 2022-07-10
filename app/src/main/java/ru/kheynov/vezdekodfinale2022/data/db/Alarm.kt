package ru.kheynov.vezdekodfinale2022.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @ColumnInfo(name = "time") val time: String = "",
    @ColumnInfo(name = "dates") val date: String = "",
    @ColumnInfo(name = "enabled") val enabled: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
)