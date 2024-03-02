package com.example.nflstats.ui

import com.example.nflstats.data.Status
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team

data class StatSettingsUIState(
    var playerSettingsList: List<Player>? = null,
    var teamSettingsList: List<Team>? = null,
    var statSuperSet: Map<Stat, Boolean> = emptyMap(),
    var status: Status = Status.LOADING
)