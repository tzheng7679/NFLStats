package com.example.nflstats.model

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val name: String,
    var value: String,
    val description: String,
    val category: String) {
    /**
     * For our purposes, we only need to compare stats when we only desire they are describing the same thing, but not necessarily at the same time (basically they are the same stat, but not the same value)
     */
    override fun equals(other: Any?): Boolean {
        return when(other is Stat) {
            true -> name == other.name && category == other.category
            false -> false
        }
    }
}