package com.example.task7.model.pokemon

import kotlin.Int

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
)