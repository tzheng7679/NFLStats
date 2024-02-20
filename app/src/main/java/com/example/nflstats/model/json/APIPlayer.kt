package com.example.nflstats.model.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIPlayer(
    val firstName: String,
    val lastName: String,
    val id: String
)