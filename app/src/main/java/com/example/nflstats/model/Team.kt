package com.example.nflstats.model
import com.example.nflstats.R
import java.util.Calendar

/**
 * Represents a Team located in "city" (must be in lowercase short abbreviation)
 */
data class Team(val city : String, val name : String) : Entity() {
    //set of stats that will be displayed for all teams
    companion object {
        var globalTeamStats = mutableSetOf<String>()
    }
    override var uniqueAdds : MutableSet<String> = mutableSetOf()
    override var uniqueSubs : MutableSet<String> = mutableSetOf()

    /**
     * Returns URL linking to player splits for year
     */
    override fun getURL() : String {
        val abbr = Maps.pfrAbbrMap[city]
        val year = Calendar.getInstance().get(Calendar.YEAR) - 1

        return super.getURL() + "teams/$abbr/$year.htm" }

    fun fetchPlayers(): Set<String> {
        throw NotImplementedError()
    }

    fun fetchDivisionPlace(): String {
        throw NotImplementedError()
    }

    override fun getStatNames(): Set<String> { return globalTeamStats union uniqueAdds subtract uniqueSubs }

    fun addGlobalStat(name : String) { globalTeamStats.add(name) }
    fun removeGlobalStat(name : String) { globalTeamStats.remove(name) }
}