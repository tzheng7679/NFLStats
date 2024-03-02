package com.example.nflstats.model
import androidx.room.PrimaryKey
import com.example.nflstats.data.Teams
import com.example.nflstats.data.abbrToID
import com.example.nflstats.data.globalStats
import com.example.nflstats.data.teamImageMap
import com.example.nflstats.data.teamNameMap

/**
 * Represents a Team located in "city" (must be in lowercase short abbreviation)
 */
@androidx.room.Entity(tableName = "teams")
data class Team(
    val abbr: Teams,
    @PrimaryKey override val id: Int = abbrToID[abbr] ?: 1,
    override val imageID: Int = teamImageMap[abbr] ?: 0,
    override var uniqueSubs: MutableSet<Stat> = mutableSetOf<Stat>(),
    override var possibleStats: List<Stat> = globalStats
) : Entity() {
    override val formattedName: Pair<String, String>
            get() {
                val x = teamNameMap[abbr]!!
                val z = x.lastIndexOf(" ")

                return Pair(x.substring(0, z), x.substring(z + 1))
            }
    /**
     * Returns URL linking to player splits for year
     */
    override fun getURLAddition() : String {
        return "teams/${id}/statistics?lang=en&region=us/"
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
}