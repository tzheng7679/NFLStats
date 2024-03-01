package com.example.nflstats.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nflstats.data.database.PlayerRepo
import com.example.nflstats.data.database.TeamRepo
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

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
            _uiState.update {
                it.copy(playerSettingsList = players.first())
            }
        }
        return players
    }
    
    fun getFirstPlayer(): Flow<Player?> = playersRepo.getFirst()

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

    suspend fun getStatsShowMap(id: Int, forPlayer: Boolean): Map<Stat, Boolean> {
        val entity = when(forPlayer) {
            true -> getPlayer(id)
            false -> getTeam(id)
        }.first()

        val statsTrue = entity?.getStatsToShow() ?: emptySet()

        return entity?.possibleStats?.associateWith { it in statsTrue } ?: emptyMap()
    }

    suspend fun setUpdatedEntity(newStats: Set<Stat>, id: Int, forPlayer: Boolean): Entity {
        var subSet = mutableSetOf<Stat>()
        val entity = when(forPlayer) {
            true -> getPlayer(id)
            false -> getTeam(id)
        }.first()!!

        entity.possibleStats.forEach {prevStat ->
            if(prevStat !in newStats) subSet.add(prevStat)
        }
        val newEntity = entity.apply { uniqueSubs = subSet }

        if(newEntity is Player) upsertPlayer(newEntity)
        else if(newEntity is Team) upsert(newEntity)

        return newEntity
    }
}