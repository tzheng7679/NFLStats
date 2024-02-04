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

    //set of maps and url base
    companion object {
        val Maps = Maps()
        val baseURL = "https://www.pro-football-reference.com/"
    }
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
    open fun getURL() : String {
        return baseURL
    }
    abstract fun getStatNames() : Set<String>

    open fun addLocalStat(name : String) {
        if (name !in Maps.dataStatMap.keys) throw Exception("DNE")
        uniqueAdds.add(name)
    }
    open fun removeLocalStat(name : String) { uniqueSubs.remove(name) }
}
