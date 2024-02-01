package com.example.nflstats.model

import com.example.nflstats.R
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.td

abstract class Entity() {
    //Have globalStats as the java equivalent of a static variable
    abstract var uniqueAdds : MutableSet<String>
    abstract var uniqueSubs : MutableSet<String>

    companion object { val Maps = Maps() }
    fun fetchStatValues() : Map<String, Double> {
        val stats = getStatNames()
        var returnMap = mutableMapOf<String, Double>()
        val year = R.integer.year

        skrape(HttpFetcher) {
            request {url = getURL()}

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

    abstract fun getURL() : String
    abstract fun getStatNames() : Set<String>

    open fun addLocalStat(name : String) {
        if (name !in Maps.dataStatMap.keys) throw Exception("DNE")
        uniqueAdds.add(name)
    }
    open fun removeLocalStat(name : String) { uniqueSubs.remove(name) }
}
