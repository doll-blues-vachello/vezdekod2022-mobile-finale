package ru.kheynov.vezdekodfinale2022.presentation.screens.poke_quiz

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kheynov.vezdekodfinale2022.AlarmBroadcastReceiver
import ru.kheynov.vezdekodfinale2022.R
import ru.kheynov.vezdekodfinale2022.data.api.Pokemon
import ru.kheynov.vezdekodfinale2022.presentation.theme.VezdekodFinale2022Theme

private const val TAG = "PokeQuiz"

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun PokeQuizScreen(
    viewModel: PokeQuizViewModel = hiltViewModel(),
    onComplete: () -> Unit = {},
) {
    val state = viewModel.state.observeAsState()
    viewModel.state.observeForever {
        Log.i(TAG, "State: $it")
    }

    val context = LocalContext.current

    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        when (state.value) {
            is PokemonQuizState.Error -> Text(text = "Error", textAlign = TextAlign.Center,
                fontSize = 24.sp)
            is PokemonQuizState.Loading -> Text(text = "Loading", textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            is PokemonQuizState.Loaded -> Quiz(viewModel.takePokemonsForQuiz(),
                onComplete = {
                    Log.i(TAG, "PokeQuizScreen: complete")
                    resetAlarm(context)
                    onComplete()
                },
                onError = {
                    Log.i(TAG, "PokeQuizScreen: mistake")
                    Toast.makeText(context, "Неверно, попробуйте еще раз", Toast.LENGTH_SHORT)
                        .show()
                }
            )
            else -> {}
        }
    }
}

@Composable
fun Quiz(
    pokemons: List<Pokemon>,
    onComplete: () -> Unit = {},
    onError: () -> Unit = {},
) {
    val tallest = pokemons.last()
    val list = pokemons.shuffled()
    Column {
        Text(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = "Какой из покемонов самый высокий?",
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 16.dp)
            .background(Color.LightGray))
        LazyColumn {
            items(list) { item ->
                QuizElement(
                    pokemon = item,
                    onClick = {
                        if (item.height == tallest.height) onComplete()
                        else onError()
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun resetAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmBroadcastReceiver::class.java)
    intent.putExtra("state", "alarm_off")
    val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    alarmManager.cancel(pendingIntent)
    context.sendBroadcast(intent)

}

@Composable
fun QuizElement(
    pokemon: Pokemon,
    onClick: () -> Unit = {},
) {
    Row(Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.weight(4f),
            text = pokemon.name.uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Button(modifier = Modifier.weight(1f), onClick = onClick) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_check_24),
                contentDescription = "",
                colorFilter =
                if (!isSystemInDarkTheme())
                    ColorFilter.tint(Color.White)
                else
                    null,
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun QuizPreview() {
    VezdekodFinale2022Theme {
        Surface(Modifier
            .fillMaxWidth()
            .height(300.dp)
        ) {
            Quiz(listOf(
                Pokemon(
                    "squirtle",
                    17
                ),
                Pokemon(
                    "charmander",
                    12
                ),
                Pokemon(
                    "pikachu",
                    10
                ),
                Pokemon(
                    "bulbasavr",
                    21
                ),
            ))
        }
    }
}