package ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_clock

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kheynov.vezdekodfinale2022.R
import ru.kheynov.vezdekodfinale2022.data.db.Alarm
import ru.kheynov.vezdekodfinale2022.presentation.screens.alarm_scheduler.daysOfWeek
import ru.kheynov.vezdekodfinale2022.presentation.theme.VezdekodFinale2022Theme

private const val TAG = "AlarmListItem"

@Composable
fun AlarmListItem(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    onRemove: () -> Unit = {},
    isEnabled: Boolean = false,
    onEnabled: (Boolean) -> Unit = {},
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)) {
        Row(modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (Modifier.weight(1f)){
                Text(text = alarm.time, fontSize = 36.sp)
                Text(text = alarm.getDaysOfWeek().joinToString(" "))
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Switch(checked = isEnabled, onCheckedChange = onEnabled)
            }
            Column(
                Modifier.fillMaxSize().weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End) {

                Box(modifier = modifier.clickable {
                    onRemove()
                },
                    contentAlignment = Alignment.CenterEnd) {
                    Image(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = "",
                        colorFilter =
                        if (isSystemInDarkTheme())
                            ColorFilter.tint(Color.White)
                        else
                            null,
                    )
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .height(1.dp)
            .background(color = Color.LightGray))
    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AlarmListItemPreview() {
    VezdekodFinale2022Theme {

        Surface(color = MaterialTheme.colors.background) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(4) {
                    AlarmListItem(alarm = Alarm("15:30", "t,t,f,t,t,t,t"))
                }
            }
        }
    }
}

fun Alarm.getDaysOfWeek(): List<String> {
    val res = mutableListOf<String>()
    this.date.split(",").forEachIndexed { index, state ->
        if (state == "t") res.add(daysOfWeek[index])
    }
    return res.toList()
}