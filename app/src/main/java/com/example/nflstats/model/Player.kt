package com.example.nflstats.model
import androidx.room.PrimaryKey
import com.example.nflstats.data.Teams
import com.example.nflstats.data.globalStats

/**
 * Represents an NFL player
 */
@androidx.room.Entity(tableName = "players")
data class Player(
    val fName: String,
    val lName: String,
    @PrimaryKey override val id: Int,
    override val imageID: Int,
    override var uniqueSubs : MutableSet<Stat> = mutableSetOf<Stat>(),
    override var possibleStats: List<Stat> = globalStats,
    var team: Teams
) : Entity() {

    override val formattedName: Pair<String, String>
        get() = Pair(fName, lName)


    /**
     * Returns URL linking to player splits for year
     */
    override fun getURLAddition() : String {
        return "${id}/statistics?lang=en&region=us/"
    }

    fun fetchTeam(): String {
        throw NotImplementedError()
    }
}