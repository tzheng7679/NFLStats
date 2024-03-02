package com.example.nflstats.model

/**
 * Returns if a stat of the same [name] and [category] are in the [stats] collection, as the [in] keyword does not work this way
 */
fun statInCollection(stat1: Stat, stats: Collection<Stat>): Boolean {
    stats.forEach {
        if(it.name == stat1.name && it.category == stat1.category) return true
    }
    return false
}