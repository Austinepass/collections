package com.example.task7.api

import com.example.task7.model.PokemonJson
import com.example.task7.model.pokemon.SinglePokemon
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://pokeapi.co/api/v2/"

val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokemonAPI::class.java)

interface PokemonAPI  {

    @GET("pokemon?limit=50")
    suspend fun getPoke() : PokemonJson


    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
            @Path("id") id: String) : SinglePokemon


}