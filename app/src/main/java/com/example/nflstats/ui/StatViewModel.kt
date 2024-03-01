package com.example.nflstats.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nflstats.data.Status
import com.example.nflstats.data.Teams
import com.example.nflstats.data.teamImageMap
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import com.example.nflstats.model.json.APIPlayer
import com.example.nflstats.model.json.EntityStats
import com.example.nflstats.model.json.PlayerLinks
import com.example.nflstats.network.ESPNApi
import com.example.nflstats.network.json
import kotlinx.coroutines.async
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
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    /**
     * Updates currEntity to entity, and sets the statistical values to those of the entity
     */
    fun setTeam(team : Team) {
        _uiState.update { it.copy(currTeam = team) }
        fetchAndSetStatValues(false)
    }

    fun setPlayer(player: Player) {
        _uiState.update{ it.copy(currPlayer = player) }
        fetchAndSetStatValues(true)
    }

    private fun fetchAndSetStatValues(forPlayer: Boolean = false) {
        val currEntity = when(forPlayer) {
            false -> _uiState.value.currTeam ?: Team(Teams.WSH)
            true -> _uiState.value.currPlayer ?: Player("Error", "Man", 1, teamImageMap[Teams.WSH]!!)
        }

        val setStatus: (Status) -> Unit = when(forPlayer) {
            false -> { it -> setTeamStatus(it) }
            true -> { it -> setPlayerStatus(it) }
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
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
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
                setStats(stats, forPlayer)
                setStatus(Status.SUCCESS)
            } catch (e: Exception) {
                setStatus(Status.FAILURE)
                Log.d("HelpMe", e.stackTraceToString())
            }
        }
        Log.d("HelpMe", stats.toString())
    }

    private fun setStats(values : List<Stat>, forCurrPlayer: Boolean) {
        when(forCurrPlayer) {
            false -> _uiState.update { it.copy(currTeamStats = values) }
            true -> _uiState.update { it.copy(currPlayerStats = values) }
        }
    }
    fun setPlayers(team: Entity = _uiState.value.currTeam ?: Team(Teams.WSH)) {
        Log.d("RunningMan", "Why they be running me, man?")

        setPlayerListStatus(status = Status.LOADING)
        val players = mutableListOf<Player>()

        viewModelScope.launch {
            async {
                try {
                    //Get JSON
                    val fetched = ESPNApi.servicer.fetchPlayers(
                        season = Entity.getSeason(),
                        teamID = team.id
                    )

                    //parse JSON with kotlin deserializer, with unknown keys excluded because we don't need them
                    val resultLinks: PlayerLinks = json.decodeFromString<PlayerLinks>(fetched)

                    //for each link, go get the player's info and add it to [players]
                    resultLinks.playerLinks.forEach { link ->
                        //format to use "https" instead of "http" because ESPN formats it as "http" for some reason, and otherwise it'll cause a security exception
                        val fLink = StringBuilder(link.link).insert(4, 's').toString()

                        val info = ESPNApi.servicer.fetchPlayerInfo(fLink)
                        //Process JSON into an [APIPlayer]
                        val resultPlayerInfo = json.decodeFromString<APIPlayer>(info)

                        players.add(
                            Player(
                            fName = resultPlayerInfo.firstName,
                            lName = resultPlayerInfo.lastName,
                            id = resultPlayerInfo.id.toInt(),
                            imageID = team.imageID
                            )
                        )
                    }
                    _uiState.update { it.copy(currPlayerList = players) }
                    setPlayerListStatus(Status.SUCCESS)
                } catch (e: Exception) {
                    setPlayerListStatus(status = Status.FAILURE)
                    Log.d("HelpMe", e.stackTraceToString())
                }
            }.await()
        }
    }

    private fun setPlayerStatus(status : Status) {
        _uiState.update { it.copy(currPlayerStatus = status) }
    }

    private fun setTeamStatus(status: Status) {
        _uiState.update { it.copy(currTeamStatus = status) }
    }

    private fun setPlayerListStatus(status: Status) {
        _uiState.update { it.copy(currPlayerListStatus = status) }
    }
}