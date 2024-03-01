package com.example.nflstats.data.database

import androidx.room.TypeConverter
import com.example.nflstats.data.Teams
import com.example.nflstats.data.abbrToID
import com.example.nflstats.data.idToAbbr
import com.example.nflstats.model.Stat
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Converters for database; converts sets to string, and back
 */
object Converters {
    @TypeConverter
    fun uniqueListToString(toEncode: MutableSet<Stat>): String = Json.encodeToString(toEncode)
    @TypeConverter
    fun toUniqueList(str: String): MutableSet<Stat> = Json.decodeFromString<MutableSet<Stat>>(str)

    @TypeConverter
    fun abbrToId(abbr: Teams): Int = abbrToID[abbr]!!

    @TypeConverter
    fun idToAbbr(id: Int): Teams = idToAbbr[id]!!

    @TypeConverter
    fun posStatsToString(toEncode: List<Stat>): String = Json.encodeToString(toEncode)

    @TypeConverter
    fun stringToPosStat(toDecode: String): List<Stat> = Json.decodeFromString<List<Stat>>(toDecode)
}