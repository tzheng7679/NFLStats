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

        return super.getURL() + "teams/$abbr/${getYear()}.htm" }

    /**
     * Returns players for this Team
     */
    fun fetchPlayers(): String {
        throw NotImplementedError()
        /*
        var players = ""

        /*
        skrape(HttpFetcher) {
            val URL = baseURL + "teams/${Maps.pfrAbbrMap[city]}/${getYear()}_roster.htm"
            request { url = URL }

            response {
                htmlDocument {

                }
            }
        }
         */

        htmlDocument("<td class=\"left \" data-append-csv=\"AdomCa00\" data-stat=\"player\" csk=\"Adomitis,Cal\"><a href=\"https://www.pro-football-reference.com/players/A/AdomCa00.htm?utm_source=direct&amp;utm_medium=Share&amp;utm_campaign=ShareTool\">Cal Adomitis</a></td>") {
            td {
                findFirst {
                    players = text
                }
            }
        }

        return players

         */
    }

    /**
     * Returns divisional place in format "{Place} in {Division}"
     */
    fun fetchDivisionPlace(): String {
        val year = getYear()
        var place = ""

        skrape(HttpFetcher) {
            request {url = getURL()}

            response {
                htmlDocument {
                    div {
                        withAttribute = "data-template" to "Partials/Team Summary"
                        p {
                            findFirst { place = text }
                        }
                    }
                }
            }
        }

        return place
    }

    override fun getStatNames(): Set<String> { return globalTeamStats union uniqueAdds subtract uniqueSubs }

    fun addGlobalStat(name : String) { globalTeamStats.add(name) }
    fun removeGlobalStat(name : String) { globalTeamStats.remove(name) }
}