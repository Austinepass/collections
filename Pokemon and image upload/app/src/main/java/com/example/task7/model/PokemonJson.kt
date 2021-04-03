package com.example.task7.model

data class PokemonJson(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)