package ru.kheynov.vezdekodfinale2022.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonsListResponse(
    @SerialName("results") val pokemons: List<PokemonNameUrl>,
)

@Serializable
data class PokemonNameUrl(
    val name: String,
    val url: String,
)

@Serializable
data class Pokemon(
    val name: String,
    val height: Int,
)