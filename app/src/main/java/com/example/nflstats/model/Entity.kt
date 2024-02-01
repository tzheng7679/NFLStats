package com.example.nflstats.model

abstract class Entity() {
    //Have globalStats as the java equivalent of a static variable
    abstract var uniqueAdds : MutableSet<String>
    abstract var uniqueSubs : MutableSet<String>

    abstract fun fetchStatValues() : Map<String, Double>
    abstract fun getStatNames() : Set<String>

    fun addLocalStat(name : String) { uniqueAdds.add(name) }
    fun removeLocalStat(name : String) { uniqueSubs.remove(name) }
}
