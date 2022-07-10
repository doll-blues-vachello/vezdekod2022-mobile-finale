package ru.kheynov.vezdekodfinale2022.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeAPI {

    @GET("pokemon?limit=20")
    suspend fun getPokemons(
    ): Response<PokemonsListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int,
    ): Response<Pokemon>
}