package com.example.nflstats.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import com.example.nflstats.model.json.APIPlayer
import com.example.nflstats.model.json.EntityStats
import com.example.nflstats.model.json.PlayerLinks
import com.example.nflstats.network.ESPNApi
import com.example.nflstats.network.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * ViewModel for the app; based mainly around doing every by itself after the Entity is set
 */
class StatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UIState(currTeam = null, currTeamStats = null, teamStatus = Status.LOADING, playerStatus = Status.LOADING, playerListStatus = Status.LOADING))
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    /**
     * Updates currEntity to entity, and sets the statistical values to those of the entity
     */
    fun setTeam(team: Team) {
        _uiState.update { it.copy(currTeam = team) }
        fetchAndSetStatValues(forCurrPlayer = false)
    }

    fun setPlayer(player: Player) {
        _uiState.update{ it.copy(currPlayer = player) }
        fetchAndSetStatValues(forCurrPlayer = true)
    }

    private fun fetchAndSetStatValues(forCurrPlayer: Boolean) {
        //set [setStatus] method and [currEntity] to appropriate values based on [forCurrPlayer]
        val setStatus: (Status) -> Unit
        val currEntity: Entity
        if(forCurrPlayer){
            setStatus = {it -> setTeamStatus(it) }
            currEntity = _uiState.value.currPlayer ?: Player("Error", "Man", 1, teamImageMap[Teams.WSH]!!)
        }
        else {
            setStatus = {it -> setPlayerStatus(it)}
            currEntity = _uiState.value.currTeam ?: Team(Teams.WSH)
        }

        setStatus(Status.LOADING)
        val stats = mutableListOf<Stat>()

        //request and add stats to [stats]
        viewModelScope.launch {
            try {
                //Get JSON
                val fetched: String = ESPNApi.servicer.fetchStatValues(
                    season = Entity.getSeason(),
                    id = currEntity.id,
                    type = currEntity.getType()
                )

                Log.d("HelpMe", fetched)

                //parse JSON with kotlin deserializer, with unknown keys excluded because we don't need them
                val result = json.decodeFromString<EntityStats>(fetched)

                result.splits.categories.forEach {category ->
                    val cat = category.name
                    category.stats.forEach {stat ->
                        val statAsObject = Stat(
                            name = stat.displayName,
                            value = stat.displayValue,
                            description = stat.description,
                            category = cat)
                        stats.add(statAsObject)
                    }
                }

                setStatus(Status.SUCCESS)
            } catch (e: Exception) {
                setStatus(Status.FAILURE)
                Log.d("HelpMe", e.stackTraceToString())
            }
        }

        setStats(stats, forCurrPlayer)
    }

    private fun setStats(values : List<Stat>, forCurrPlayer: Boolean) {
        when(forCurrPlayer) {
            false -> _uiState.update { it.copy(currTeamStats = values) }
            true -> _uiState.update { it.copy(currPlayerStats = values) }
        }
    }

    private fun setPlayerStatus(status : Status) {
        _uiState.update { it.copy(playerStatus = status) }
    }

    private fun setTeamStatus(status : Status) {
        _uiState.update { it.copy(teamStatus = status) }
    }

    private fun setPlayerListStatus(status : Status) {
        _uiState.update { it.copy(playerListStatus= status) }
    }

    fun setPlayers(team: Team = _uiState.value.currTeam ?: Team(Teams.WSH)) {
        val players = mutableListOf<Player>()
        setPlayerListStatus(Status.LOADING)

        viewModelScope.launch {
            try {
                //Get JSON
                val fetched: String = ESPNApi.servicer.fetchPlayers(
                    season = Entity.getSeason(),
                    teamID = team.id
                )

                val resultLinks = json.decodeFromString<PlayerLinks>(fetched)
                viewModelScope.launch {
                    try {
                        resultLinks.playerLinks.forEach { link ->
                            //format to use "https" instead of "http" because ESPN formats it as "http" for some reason
                            val fLink = StringBuilder(link.link).insert(4, 's').toString()

                            val info = ESPNApi.servicer.fetchPlayerInfo(fLink)
                            val resultPlayerInfo = json.decodeFromString<APIPlayer>(info)

                            val p = Player(
                                fName = resultPlayerInfo.firstName,
                                lName = resultPlayerInfo.lastName,
                                id = resultPlayerInfo.id.toInt(),
                                imageID = team.imageID
                            )
                            players.add(p)
                        }
                    } catch (e: Exception) {
                        Log.d("HelpMe", e.stackTraceToString())
                    }

                    _uiState.update { it.copy(currPlayersOfTeam = players) }
                }

                setPlayerListStatus(Status.SUCCESS)
            } catch (e: Exception) {
                setPlayerListStatus(status = Status.FAILURE)
                Log.d("HelpMe", e.stackTraceToString())
            }

            _uiState.update { it.copy(currPlayersOfTeam = players) }
        }

        Log.d("HelpMe", players.toString())
        Log.d("HelpMe", _uiState.value.currPlayersOfTeam.toString())
    }
}