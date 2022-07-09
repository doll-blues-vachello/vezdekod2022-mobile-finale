package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_scheduler

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val TAG = "DayOfWeekChooser"

@Composable
fun DayOfWeekChooser(
    modifier: Modifier = Modifier,
    initialState: Array<Boolean>?,
    onStateChanged: (Int, Boolean) -> Unit,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        repeat(7) { i ->
            DayOfWeekElement(name = daysOfWeek[i],
                state = initialState?.get(i) ?: false,
                onChecked = { state ->
                    onStateChanged(i, state)
                })
        }
    }
}

@Composable
fun DayOfWeekElement(
    name: String,
    state: Boolean,
    onChecked: (Boolean) -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment
        .CenterHorizontally) {
        Checkbox(checked = state, onCheckedChange = onChecked)
        Text(text = name)
    }
}

@Preview
@Composable
fun DayOfWeekPreview() {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
        color = MaterialTheme.colors.background) {
        DayOfWeekChooser(initialState = arrayOf(true,
            true,
            true,
            true,
            false,
            true,
            true),
            onStateChanged = { index, state ->
                Log.i(TAG, "DayOfWeekPreview: index: $index, " +
                        "state: $state")
            })
    }
}

val daysOfWeek = arrayOf(
    "пн",
    "вт",
    "ср",
    "чт",
    "пт",
    "сб",
    "вс",
)