package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_scheduler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.kheynov.vezdekodfinale2022.data.db.Alarm
import javax.inject.Inject

@HiltViewModel
class AlarmSchedulerViewModel @Inject constructor() : ViewModel() {

    private var _dayOfWeekChooserState = Array(7) { true }
    val dayOfWeekChooserState = MutableLiveData<Array<Boolean>>()

    private fun updateDayChooserState() {
        dayOfWeekChooserState.value = _dayOfWeekChooserState.clone()
    }

    var time: String = ""

    init {
        updateDayChooserState()
    }

    fun onDayChooserStateUpdated(index: Int, state: Boolean) {
        _dayOfWeekChooserState[index] = state
        updateDayChooserState()
    }

    fun selectAllDays() {
        for (i in _dayOfWeekChooserState.indices) {
            _dayOfWeekChooserState[i] = true
        }
        updateDayChooserState()
    }

    fun getAlarm(): Alarm? {
        if (time.isEmpty()) return null
        return Alarm(
            time = time,
            date = dayOfWeekStateToString()
        )
    }

    private fun dayOfWeekStateToString(): String {
        val res = StringBuilder()
        _dayOfWeekChooserState.forEach {
            res.append(if (it) "t," else "f,")
        }
        res.deleteCharAt(res.length - 1)
        return res.toString()
    }
}
