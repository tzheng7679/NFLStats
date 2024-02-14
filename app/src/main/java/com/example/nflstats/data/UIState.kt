package com.example.nflstats.data

import com.example.nflstats.model.Entity

data class UIState(var currEntity: Entity?, var currStats : Map<String, Pair<Double, String>>?, var status : Status)