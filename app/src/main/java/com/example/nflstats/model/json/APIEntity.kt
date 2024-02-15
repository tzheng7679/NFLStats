package com.example.nflstats.model.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntityStats(
    @SerialName("splits")
    val splits : Splits
)

@Serializable
data class Splits(
    val categories: List<Category>
)

@Serializable
data class Category(
    val stats: List<APIStat>
)

@Serializable
data class APIStat(
    val displayValue: String,
    val name: String,
    val description: String,
)