package com.example.pokemon.servicio

import com.example.pokemon.modelo.PokemonListResponse
import com.example.pokemon.modelo.PokemonUnitario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon")
    fun getPokemonList(): Call<PokemonListResponse>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<PokemonUnitario>
}