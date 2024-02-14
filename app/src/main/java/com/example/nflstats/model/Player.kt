package com.example.nflstats.model
import com.example.nflstats.R
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.td

/**
 * Represents an NFL player
 */

class Player(val fName : String, val lName : String, override val id : Int, imageID : Int) : Entity(imageID) {
    override var uniqueAdds : MutableSet<String> = mutableSetOf()
    override var uniqueSubs : MutableSet<String> = mutableSetOf()

    override val formattedName: Pair<String, String>
        get() = Pair(fName, lName)

    //set of stats that will be displayed for all players
    companion object {
        var globalPlayerStats = mutableSetOf<String>()
    }

    /**
     * Returns URL linking to player splits for year
     */
    override fun getURLAddition() : String {
        return "${id}/statistics?lang=en&region=us/"
    }

    fun fetchTeam(): String {
        throw NotImplementedError()
    }

    override fun getStatNames(): Set<String> { return globalPlayerStats union uniqueAdds subtract uniqueSubs }

    fun addGlobalStat(name : String) { Team.globalTeamStats.add(name) }
    fun removeGlobalStat(name : String) { Team.globalTeamStats.remove(name) }
}