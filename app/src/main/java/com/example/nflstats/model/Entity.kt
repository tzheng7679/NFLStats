package com.example.nflstats.model

import android.content.Context
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.Calendar

abstract class Entity(val imageID : Int) {
    //Have globalStats as the java equivalent of a static variable
    abstract var uniqueAdds : MutableSet<String>
    abstract var uniqueSubs : MutableSet<String>
    abstract val formattedName : Pair<String, String>
    var secondaryInformation : String = "FILLER"

    //set of maps and base string for PFR URL
    companion object {
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

    fun fetchStatValues(context : Context) : Map<String, Pair<Double, String>> {
        /*
        val queue = Volley.newRequestQueue(context)
        val url = "https://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/2021/types/2/teams/24/statistics"

        val text by remember { mutableStateOf(JSONObject()) }
        Text(text = text.toString())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response -> //onResponse
                val text = response
                val z = response.getJSONObject("splits")
                Log.d("HelpMe", text["splits.categories.0.name"].toString())
            },

            { error ->
                Log.d("HelpMe", error.stackTraceToString())
                Log.d("HelpMe", "Could not retrieve statistics from API")
            }
        )

        queue.add(jsonObjectRequest)
        queue.stop()
         */
        return mapOf<String, Pair<Double, String>>(
            "Completion Percentage" to Pair(67.3, "The percent of passes completed"),
            "Passing Yards" to Pair(4183.0, "The amount of yards passing"),
            "Passing Touchdowns" to Pair(27.0, "The amount of touchdowns the player threw"),
            "Interceptions" to Pair(14.0, "The amount of interceptions thrown"),
            "QBR" to Pair(63.0, "The QBR of a quarterback"),
            "Passer Rating" to Pair(92.6, "Passer rating"),
            "Passing LNG" to Pair(67.0, "Longest passing play"),
            "Sacks" to Pair(27.0, "Amount of sacks taken")
        )
    }

    /**
     * Super method that returns base url for child objects
     */
    abstract fun getURL() : String

    /**
     * Returns Set of string name for stats for this object
     */
    abstract fun getStatNames() : Set<String>

    /**
     * Adds local stat to Entity
     */
    open fun addLocalStat(name : String) { uniqueAdds.add(name) }

    /**
     * Removes local stat from Entity
     */
    open fun removeLocalStat(name : String) { uniqueSubs.remove(name) }
}
