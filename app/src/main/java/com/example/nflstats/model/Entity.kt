package com.example.nflstats.model

import androidx.room.Entity
import java.util.Calendar

@Entity
abstract class Entity() {
    //Have globalStats as the java equivalent of a static variable
    abstract val id : Int

    abstract var uniqueAdds: MutableSet<Stat>
    abstract var uniqueSubs : MutableSet<Stat>
    abstract val formattedName : Pair<String, String>
    abstract val imageID : Int
    var secondaryInformation : String = "FILLER"

    //base string for ESPN URL and a method to return the appropriate season
    companion object {
        const val BASE_URL = "http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/"

        /**
         * Returns the the season to be accessed (assuming that the season kicks off on the first Thus. after labor day
         */
        fun getSeason() : Int {
            val cal = Calendar.getInstance()

            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            //if before season
            if(month < 8 || day < 4) return year - 1
            //if after kickoff day
            if(month > 8 || day > 10) return year

            cal.set(year, 8, 1)
            var laborDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
            var laborDay = 1
            while(laborDayOfWeek != Calendar.MONDAY) {
                laborDay += 1
                laborDayOfWeek += 1
            }

            //if past kickoff day, then return current year; else return past year
            if(day > laborDay + 3) return year

            return year - 1
        }
    }

    /**
     * Returns Set of string name for stats for this object
     */
    open fun getStatNames(globalStats: Set<Stat>): Set<Stat> { return globalStats union uniqueAdds subtract uniqueSubs }

    abstract fun getURLAddition() : String
    /**
     * Adds local stat to Entity
     */
    open fun addLocalStat(stat: Stat) = uniqueSubs.remove(stat)
    /**
     * Removes local stat from Entity
     */
    open fun removeLocalStat(stat: Stat) { uniqueSubs.remove(stat) }

    fun getType() : String {
        return when(this) {
            is Team -> "teams"
            else -> "athletes"
        }
    }
}
