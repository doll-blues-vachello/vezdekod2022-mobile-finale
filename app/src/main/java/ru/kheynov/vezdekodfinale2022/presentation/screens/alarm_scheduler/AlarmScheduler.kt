package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_scheduler

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import ru.kheynov.vezdekodfinale2022.R
import ru.kheynov.vezdekodfinale2022.data.db.Alarm

private const val TAG = "AlarmScheduler"

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AlarmScheduler(
    viewModel: AlarmSchedulerViewModel = hiltViewModel(),
    onSchedule: (Alarm) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val dayOfWeekChooserState = viewModel.dayOfWeekChooserState.observeAsState()

    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val time = remember {
        MutableLiveData("")
    }

    time.observeForever {
        if (it.isNotEmpty()) {
            Log.i(TAG, "time: ${time.value}")
            viewModel.time = time.value.toString()
        }
    }

    val timePickerDialog = TimePickerDialog(
        context,
        { _, mHour: Int, mMinute: Int ->
            time.value = "$mHour:$mMinute"
        }, hour, minute, true
    )
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart) {
        Box(Modifier.clickable { onDismiss() }) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(36.dp),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "back button",
                colorFilter =
                if (isSystemInDarkTheme())
                    ColorFilter.tint(Color.White)
                else
                    null,
            )
        }

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (time.observeAsState().value?.isNotEmpty() == true) {
            Text(
                text = "Выбранное время будильника: \n ${time.observeAsState().value}",
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
            )
        }

        Button(onClick = {
            timePickerDialog.show()
            Log.i(TAG, "AlarmScheduler: dateTimePicker toggled!")
        }) {
            Text(text = "Выберите время будильника")
        }

        Column {
            Button(onClick = { viewModel.selectAllDays() }) {
                Text(text = "Повторять всегда")
            }
            DayOfWeekChooser(
                initialState = dayOfWeekChooserState.value,
                onStateChanged =
                { index, state -> viewModel.onDayChooserStateUpdated(index, state) },
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                val res = viewModel.getAlarm()
                if (res != null) {
                    onSchedule(res)
                } else {
                    Toast.makeText(context, "Сначала укажите время будильника!", Toast
                        .LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Установить будильник")
        }
    }
}
