package ru.kheynov.vezdekodfinale2022.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import ru.kheynov.vezdekodfinale2022.presentation.screens.poke_quiz.PokeQuizScreen
import ru.kheynov.vezdekodfinale2022.presentation.theme.VezdekodFinale2022Theme

@AndroidEntryPoint
class QuizActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VezdekodFinale2022Theme {
                Surface(color = MaterialTheme.colors.background) {
                    PokeQuizScreen(onComplete = { finish() })
                }
            }
        }
    }
}