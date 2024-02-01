package com.example.nflstats.model

data class Team : Entity() {
    override var uniqueAdds : MutableSet<String> = mutableSetOf()
    override var uniqueSubs : MutableSet<String> = mutableSetOf()

    override fun fetchStatValues() : Map<String, Double> {
        val stats = getStatNames()


    }
}