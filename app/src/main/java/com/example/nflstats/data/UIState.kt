package com.example.nflstats.data

import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat

data class UIState(
    var currEntity: Entity?,
    var currStats : List<Stat>?,
    var status : Status,
    var currPlayers : List<Player> = emptyList()
)