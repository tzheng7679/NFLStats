package com.example.nflstats.model

data class Stat(
    val name: String,
    var value: String,
    val description: String,
    val category: String = "") {
    init {
        //add % sign to value if stat is a percentage stat
        value += when {
            name.contains("%") -> "%"
            else -> ""
        }
    }
}