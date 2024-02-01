package com.example.nflstats.model
import com.example.nflstats.R

data class Player(val fName : String, val lName : String, val playerID : String) : Entity() {
    override var uniqueAdds : MutableSet<String> = mutableSetOf()
    override var uniqueSubs : MutableSet<String> = mutableSetOf()

    //set of stats that will be displayed for all players
    companion object {
        var globalPlayerStats = mutableSetOf<String>()
    }

    /**
     * Returns URL linking to player splits for year
     */
    override fun getURL() : String { return "https://www.pro-football-reference.com/players/$playerID/splits/${R.integer.year}/" }

    fun fetchPlayers(): Set<String> {
        throw NotImplementedError()
    }

    fun fetchDivisionPlace(): String {
        throw NotImplementedError()
    }

    override fun getStatNames(): Set<String> { return globalPlayerStats union uniqueAdds subtract uniqueSubs }

    fun addGlobalStat(name : String) { Team.globalTeamStats.add(name) }
    fun removeGlobalStat(name : String) { Team.globalTeamStats.remove(name) }
}