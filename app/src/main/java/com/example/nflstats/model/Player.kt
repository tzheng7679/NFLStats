package com.example.nflstats.model
import androidx.room.PrimaryKey

/**
 * Represents an NFL player
 */
@androidx.room.Entity(tableName = "players")
data class Player(
    val fName: String,
    val lName: String,
    @PrimaryKey override val id: Int,
    override val imageID: Int
) : Entity() {
    override var uniqueAdds : MutableSet<String> = mutableSetOf()
    override var uniqueSubs : MutableSet<String> = mutableSetOf()

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

    override fun getStatNames(globalStats: Set<String>): Set<String> { return globalStats union uniqueAdds subtract uniqueSubs }
}