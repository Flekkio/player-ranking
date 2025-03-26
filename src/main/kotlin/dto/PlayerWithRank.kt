package com.flekkio.playeranking.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerWithRank(
    val id: String,
    val pseudo: String,
    val points: Int,
    val rank: Int
)