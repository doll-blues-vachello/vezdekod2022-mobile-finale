package ru.kheynov.vezdekodfinale2022.presentation.screens.poke_quiz

import ru.kheynov.vezdekodfinale2022.data.api.Pokemon

sealed interface PokemonQuizState {
    object Loading : PokemonQuizState
    data class Loaded(val data: List<Pokemon>) : PokemonQuizState
    object Error : PokemonQuizState
}