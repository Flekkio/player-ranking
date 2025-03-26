package com.flekkio.playeranking.models

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Player(
    val id: String = ObjectId().toHexString(),
    val pseudo: String,
    val points: Int
)