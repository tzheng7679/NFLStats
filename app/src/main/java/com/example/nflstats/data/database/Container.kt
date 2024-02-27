package com.example.nflstats.data.database

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val playersRepo: PlayerRepo
    val teamsRepo: TeamRepo
}

class AppDataContainer(private val context: Context): AppContainer {
    val db = EntityDatabase.getDatabase(context)

    override val teamsRepo: TeamRepo by lazy {
        TeamRepo(db.getTeamDao())
    }

    override val playersRepo: PlayerRepo by lazy {
        PlayerRepo(db.getPlayerDao())
    }
}