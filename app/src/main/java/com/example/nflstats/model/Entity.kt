package com.example.nflstats.model

import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Calendar

abstract class Entity(val imageID : Int) {
    //Have globalStats as the java equivalent of a static variable
    abstract var uniqueAdds : MutableSet<String>
    abstract var uniqueSubs : MutableSet<String>
    abstract val formattedName : Pair<String, String>
    abstract val id : Int
    var secondaryInformation : String = "FILLER"

    //set of maps and base string for PFR URL
    companion object {
        const val BASE_URL = "http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/"

        /**
         * Returns the the season to be accessed (assuming that the season kicks off on the first Thus. after labor day
         */
        fun getSeason() : Int {
            val cal = Calendar.getInstance()
            cal.set(2002, 8, 6)

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
    abstract fun getStatNames() : Set<String>

    abstract fun getURLAddition() : String
    /**
     * Adds local stat to Entity
     */
    open fun addLocalStat(name : String) { uniqueAdds.add(name) }

    /**
     * Removes local stat from Entity
     */
    open fun removeLocalStat(name : String) { uniqueSubs.remove(name) }

    fun getType() : String {
        return when(this) {
            is Team -> "team"
            else -> "athlete"
        }
    }
}
