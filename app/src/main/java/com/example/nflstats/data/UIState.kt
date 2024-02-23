package com.example.nflstats.data

import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team

/**
 * [currTeam] is the current team selected, or player from the main menu selected
 * [currPlayer] is the current player selected from the Team menu
 */
data class UIState(
    var currTeam: Team?,
    var currTeamStats: List<Stat>?,
    var currPlayersOfTeam: List<Player> = emptyList(),
    var playerListStatus: Status,

    var currPlayer: Player? = null,
    var currPlayerStats: List<Stat>? = null,

    var teamStatus: Status,
    var playerStatus: Status
)