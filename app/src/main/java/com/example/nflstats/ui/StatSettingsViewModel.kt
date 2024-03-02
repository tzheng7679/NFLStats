package com.example.nflstats.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nflstats.data.database.PlayerRepo
import com.example.nflstats.data.database.TeamRepo
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import com.example.nflstats.model.statInList
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

    /**
     * Returns map of possible stats and whether or not to show them for [Entity] of id [id]
     */
    suspend fun getStatsShowMap(id: Int, forPlayer: Boolean): Map<Stat, Boolean> {
        val entity = when(forPlayer) {
            true -> getPlayer(id)
            false -> getTeam(id)
        }.first()

        val statsTrue = entity?.getStatsToShow() ?: emptySet()

        return entity?.possibleStats?.associateWith { it in statsTrue } ?: emptyMap()
    }

    /**
     * Sets team or player (depends on [forPlayer]) of [Entity.id] [id] to have a sub set that reflects [newStats] (i.e. removes stats that aren't in [newStats])
     */
    suspend fun setUpdatedEntity(newStats: Set<Stat>, id: Int, forPlayer: Boolean): Entity {
        var subSet = mutableSetOf<Stat>()
        val entity = when(forPlayer) {
            true -> getPlayer(id)
            false -> getTeam(id)
        }.first()!!

        entity.possibleStats.forEach {prevStat ->
            //Construct the subset for the entity
            if(!statInList(prevStat, newStats)) subSet.add(prevStat)
        }
        val newEntity = entity.apply { uniqueSubs = subSet }

        if(newEntity is Player) upsertPlayer(newEntity)
        else if(newEntity is Team) upsert(newEntity)

        return newEntity
    }

    /**
     * Returns list of a team's players, with the player replaced if already in storage with the stored version
     */
    suspend fun getPlayersWithReplace(fetchedPlayers: List<Player>?): List<Player> {
        Log.d("HelpMe", "________")
        Log.d("HelpMe", fetchedPlayers.toString())
        val allPlayers = getAllPlayers().first() ?: emptyList()

        val newPlayers = mutableListOf<Player>()
        (fetchedPlayers ?: emptyList()).forEach {fetchedPlayer ->
            var inStorage: Player? = null
            for(storedPlayer in allPlayers) {
                if(storedPlayer.id == fetchedPlayer.id) {
                    inStorage = storedPlayer
                    break
                }
            }

            if(inStorage != null) newPlayers.add(inStorage) else newPlayers.add(fetchedPlayer)
        }

        Log.d("HelpMe", newPlayers.toString())
        Log.d("HelpMe", "________")
        return newPlayers
    }

    /**
     * Returns superset of all stats for either [Team] or [Player]; returns all the possible stats that exist for all of the players or teams in storage
     */
    suspend fun getStatSuperSet(isPlayer: Boolean): Set<Stat> {
        val superEntities = (if(isPlayer) getAllPlayers() else getAllTeams()).first() ?: emptyList()
        var superSet = mutableSetOf<Stat>()

        superEntities.forEach {
            it.possibleStats.forEach { stat ->
                superSet.add(stat)
            }
        }

        return superSet
    }

    /**
     * Sets all of the sub sets for all of either [Team] or [Player] in storage to the appropriate value for [newStats]
     */
    suspend fun setAllStats(newStats: Set<Stat>, forPlayer: Boolean) {
        (
            (if(forPlayer) getAllPlayers() else getAllTeams())
                .first() ?: emptyList()
        ).forEach {entity ->
                        setUpdatedEntity(newStats = newStats, id = entity.id, forPlayer = forPlayer)
                    }
    }
}