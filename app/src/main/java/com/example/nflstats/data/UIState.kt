package com.example.nflstats.data

import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat

data class UIState(
    var currTeam: Entity? = null,
    var currTeamStats: List<Stat>? = null,
    var currTeamStatus: Status = Status.LOADING,

    var currPlayerList: List<Player>? = emptyList(),
    var currPlayerListStatus: Status = Status.LOADING,

    var currPlayer: Entity? = null,
    var currPlayerStats: List<Stat>? = null,
    var currPlayerStatus: Status = Status.LOADING
)