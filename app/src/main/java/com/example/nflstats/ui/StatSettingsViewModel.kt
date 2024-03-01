package com.example.nflstats.ui

import androidx.lifecycle.ViewModel
import androidx.room.Query
import com.example.nflstats.data.database.AppDataContainer
import com.example.nflstats.data.database.PlayerRepo
import com.example.nflstats.data.database.TeamRepo
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

/**
 * ViewModel for the app; based mainly around doing every by itself after the Entity is set
 */
class StatSettingsViewModel(
    private val teamsRepo: TeamRepo,
    private val playersRepo: PlayerRepo
) : ViewModel() {
// *
// *   Team methods
// *
    suspend fun upsert(team: Team) = teamsRepo.replaceOrInsert(team)
    fun getTeam(id: Int): Flow<Team?> = teamsRepo.getTeam(id)
    fun getAllTeams(): Flow<List<Team>?> = teamsRepo.getAllTeams()
    fun getFirstTeam(): Flow<Team?> = teamsRepo.getFirst()
    suspend fun getTeamSettings(id: Int): Set<Stat> {
        val team = getTeam(id)

        return team.first()?.uniqueSubs ?: emptySet()
    }
    
//  *
//  * player functions
//  *
    suspend fun upsert(player: Player) = playersRepo.replaceOrInsert(player)

    fun getPlayer(id: Int): Flow<Player?> = playersRepo.getPlayer(id)

    fun getAllPlayers(): Flow<List<Player>?> = playersRepo.getAllPlayers()
    
    fun getFirstPlayer(): Flow<Player?> = playersRepo.getFirst()
    suspend fun getPlayerSettings(id: Int): Set<Stat> {
        val team = getPlayer(id)

        return team.first()?.uniqueSubs ?: emptySet()
    }
}