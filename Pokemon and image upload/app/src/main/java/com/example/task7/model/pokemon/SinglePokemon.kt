package com.example.task7.model.pokemon

import com.example.task7.model.Type

data class SinglePokemon(
    val abilities: List<Ability>,
    val base_experience: Int,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val weight: Int,
    val types: List<Type>
)