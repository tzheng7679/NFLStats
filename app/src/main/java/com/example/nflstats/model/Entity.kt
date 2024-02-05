package com.example.nflstats.model

import com.example.nflstats.R
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.td
import java.util.Calendar

abstract class Entity() {
    //Have globalStats as the java equivalent of a static variable
    abstract var uniqueAdds : MutableSet<String>
    abstract var uniqueSubs : MutableSet<String>

    //set of maps and base string for PFR URL
    companion object {
        val Maps = Maps()
        const val baseURL = "https://www.pro-football-reference.com/"
    }

    /**
     * Returns current year for URL constructing purpoess
     */
    fun getYear() : Int {
        return Calendar.getInstance().get(Calendar.YEAR) - 1
    }

    /**
     * Returns Map of String stats and their Double value scraped from PFR
     */
    fun fetchStatValues() : Map<String, Double> {
        val stats = getStatNames()
        val returnMap = mutableMapOf<String, Double>()

        skrape(HttpFetcher) {
            val URL = getURL()
            request {url = URL}

            response {
                htmlDocument {
                    stats.forEach { it ->
                        td {
                            withAttribute = "data-stat" to Maps.dataStatMap[it]!!
                            findFirst { returnMap[it] = text.toDouble() }
                        }
                    }
                }
            }
        }
        return returnMap
    }

    /**
     * Super method that returns base url for child objects
     */
    open fun getURL() : String {
        return baseURL
    }

    /**
     * Returns Set of string name for stats for this object
     */
    abstract fun getStatNames() : Set<String>

    /**
     * Adds local stat to Entity
     */
    open fun addLocalStat(name : String) {
        if (name !in Maps.dataStatMap.keys) throw Exception("DNE")
        uniqueAdds.add(name)
    }

    /**
     * Removes local stat from Entity
     */
    open fun removeLocalStat(name : String) { uniqueSubs.remove(name) }
}
