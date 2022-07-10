package ru.kheynov.vezdekodfinale2022.data.api

import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokeAPI: PokeAPI,
) {
    suspend fun getPokemonsList() = pokeAPI.getPokemons()

    suspend fun getPokemon(id: Int) = pokeAPI.getPokemon(id)
}