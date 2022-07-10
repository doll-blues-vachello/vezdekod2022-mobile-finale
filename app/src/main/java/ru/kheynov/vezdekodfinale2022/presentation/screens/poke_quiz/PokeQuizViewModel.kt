package ru.kheynov.vezdekodfinale2022.presentation.screens.poke_quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.kheynov.vezdekodfinale2022.data.api.Pokemon
import ru.kheynov.vezdekodfinale2022.data.api.PokemonRepository
import javax.inject.Inject

private const val TAG = "PokeQuizVM"

@HiltViewModel
class PokeQuizViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {
    val state = MutableLiveData<PokemonQuizState>()

    init {
        loadPokemonsList()
    }

    private fun loadPokemonsList() {
        state.value = PokemonQuizState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getPokemonsList()
                if (response.isSuccessful) {
                    val pokemonList = response.body()
                    val res = mutableListOf<Pokemon>()
                    coroutineScope {
                        pokemonList?.pokemons?.forEach { pokemonListItem ->
                            launch {
                                val pokemonResponse =
                                    repository.getPokemon(pokemonListItem.url[pokemonListItem.url
                                        .lastIndex - 1].digitToInt())
                                if (pokemonResponse.isSuccessful)
                                    pokemonResponse.body()?.let { res.add(it) }
                            }
                        }

                    }
                    state.value = PokemonQuizState.Loaded(res)
                } else {
                    state.value = PokemonQuizState.Error
                }
            } catch (e: Exception) {
                Log.e(TAG, e.stackTraceToString())
                state.value = PokemonQuizState.Error
            }
        }
    }

    fun takePokemonsForQuiz(): List<Pokemon> {
        val res = mutableListOf<Pokemon>()
        (state.value as PokemonQuizState.Loaded).data.forEach { pokemon ->
            if (res.find { pokemon.height == it.height } == null)
                res.add(pokemon)
        }
        return res.shuffled().take(5).sortedWith(compareBy { it.height })
    }
}