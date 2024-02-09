package com.example.nflstats.model
import com.example.nflstats.R
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.p
import it.skrape.selects.html5.tbody
import it.skrape.selects.html5.td
import it.skrape.selects.text

/**
 * Represents a Team located in "city" (must be in lowercase short abbreviation)
 */
class Team(val city : String, val name : String) : Entity() {
    //set of stats that will be displayed for all teams
    companion object {
        var globalTeamStats = mutableSetOf<String>()
        val baseURL = "http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/2023/types/2/teams/12/"
    }
    override var uniqueAdds : MutableSet<String> = mutableSetOf()
    override var uniqueSubs : MutableSet<String> = mutableSetOf()

    /**
     * Returns URL linking to player splits for year
     */
    override fun getURL() : String {
        throw NotImplementedError()
    }

    /**
     * Returns players for this Team
     */
    fun fetchPlayers(): String {
        throw NotImplementedError()
    }

    /**
     * Returns divisional place in format "{Place} in {Division}"
     */
    fun fetchDivisionPlace(): String {
        throw NotImplementedError()
    }

    override fun getStatNames(): Set<String> { return globalTeamStats union uniqueAdds subtract uniqueSubs }

    fun addGlobalStat(name : String) { globalTeamStats.add(name) }
    fun removeGlobalStat(name : String) { globalTeamStats.remove(name) }
}