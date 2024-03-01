package com.example.nflstats.ui

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.room.Query
import com.example.nflstats.data.database.AppDataContainer
import com.example.nflstats.data.database.PlayerRepo
import com.example.nflstats.data.database.TeamRepo
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

/**
 * ViewModel for the app; based mainly around doing every by itself after the Entity is set
 */
class StatSettingsViewModel(
    private val teamsRepo: TeamRepo,
    private val playersRepo: PlayerRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatSettingsUIState())
    val uiState = _uiState.asStateFlow()
// *
// *   Team methods
// *
    suspend fun upsert(team: Team) {
        teamsRepo.replaceOrInsert(team)
        _uiState.update {
            it.copy(teamSettingsList = getAllTeams().first())
        }
    }
    fun getTeam(id: Int): Flow<Team?> = teamsRepo.getTeam(id)
    fun getAllTeams(): Flow<List<Team>?> {
        val teams = teamsRepo.getAllTeams()
        runBlocking {
            _uiState.update {
                it.copy(teamSettingsList = teams.first())
            }
        }
        return teams
    }
    fun getFirstTeam(): Flow<Team?> = teamsRepo.getFirst()
    suspend fun getTeamSettings(id: Int): Set<Stat> {
        val team = getTeam(id)

        return team.first()?.uniqueSubs ?: emptySet()
    }

    suspend fun clearTeams() {
        teamsRepo.clearTeams()
        _uiState.update {
            it.copy(teamSettingsList = null)
        }
    }

//  *
//  * player functions
//  *
    suspend fun upsertPlayer(player: Player) {
        playersRepo.replaceOrInsert(player)
        _uiState.update {
            it.copy(playerSettingsList = getAllPlayers().first())
        }
    }

    fun getPlayer(id: Int): Flow<Player?> = playersRepo.getPlayer(id)

    fun getAllPlayers(): Flow<List<Player>?> {
        val players = playersRepo.getAllPlayers()
        runBlocking {
            Log.d("HelpMe", "Blah: " + players.first().toString())
            _uiState.update {
                it.copy(playerSettingsList = players.first())
            }
        }
        return players
    }
    
    fun getFirstPlayer(): Flow<Player?> = playersRepo.getFirst()
    suspend fun getPlayerSettings(id: Int): Set<Stat> {
        val team = getPlayer(id)

        return team.first()?.uniqueSubs ?: emptySet()
    }

    suspend fun clearPlayers() {
        playersRepo.clearPlayers()
        _uiState.update {
            it.copy(playerSettingsList = null)
        }
    }

    fun build() {
        getAllPlayers()
        getAllTeams()
    }
}