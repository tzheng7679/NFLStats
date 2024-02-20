package com.example.nflstats.model.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerLinks(
    @SerialName("items")
    val playerLinks: List<Link>
)

@Serializable
data class Link(
    @SerialName("\$ref")
    val link: String
)