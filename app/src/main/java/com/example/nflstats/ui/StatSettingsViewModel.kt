package com.example.nflstats.ui

import androidx.lifecycle.ViewModel
import com.example.nflstats.data.database.AppDataContainer
import com.example.nflstats.data.database.PlayerRepo
import com.example.nflstats.data.database.TeamRepo

/**
 * ViewModel for the app; based mainly around doing every by itself after the Entity is set
 */
class StatSettingsViewModel(
    private val teamsRepo: TeamRepo,
    private val playersRepo: PlayerRepo
) : ViewModel() {
    lateinit var container: AppDataContainer
}