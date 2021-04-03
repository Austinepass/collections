package com.example.task8.model

data class PokemonJson(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)