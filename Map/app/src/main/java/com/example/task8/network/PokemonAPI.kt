package com.example.task8.network

import com.example.task8.model.PokemonJson
import com.example.task8.model.SinglePokemon
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonAPI  {


    @GET("pokemon")
    fun getPoke(
        @Query("limit") limit : Int
    ): Observable<PokemonJson>




    @GET("pokemon/{id}")
    fun getPokemonDetails(
            @Path("id") id: String) : Observable<SinglePokemon>


}