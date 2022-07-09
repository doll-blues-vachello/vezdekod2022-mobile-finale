package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_clock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.kheynov.vezdekodfinale2022.data.db.Alarm
import ru.kheynov.vezdekodfinale2022.data.db.AlarmRepository
import javax.inject.Inject


private const val TAG = "AlarmScreenViewModel"

@HiltViewModel
class AlarmScreenViewModel @Inject constructor(
    private val repository: AlarmRepository,
) : ViewModel() {
    val state = MutableLiveData<AlarmScreenState>()

    val alarms: LiveData<List<Alarm>> = repository.alarms.asLiveData()

    init {
        alarms.observeForever {
            Log.i(TAG, "alarms List: $it")
        }
    }

    fun scheduleAlarm(alarm: Alarm) {
        viewModelScope.launch {
            repository.insertAlarm(alarm)
        }
    }
}

