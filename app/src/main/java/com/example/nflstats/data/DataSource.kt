package com.example.nflstats.data

import com.example.nflstats.R

//maps teams to image resources
val teamImageMap : Map<String, Int> = mapOf(
    "ari" to R.drawable.ari,
    "atl" to R.drawable.atl,
    "bal" to R.drawable.bal,
    "buf" to R.drawable.buf,
    "car" to R.drawable.car,
    "chi" to R.drawable.chi,
    "cin" to R.drawable.cin,
    "cle" to R.drawable.cle,
    "dal" to R.drawable.dal,
    "den" to R.drawable.den,
    "det" to R.drawable.det,
    "gb" to R.drawable.gb,
    "hou" to R.drawable.hou,
    "ind" to R.drawable.ind,
    "jax" to R.drawable.jax,
    "kc" to R.drawable.kc,
    "lac" to R.drawable.lac,
    "lar" to R.drawable.lar,
    "lv" to R.drawable.lv,
    "mia" to R.drawable.mia,
    "min" to R.drawable.min,
    "ne" to R.drawable.ne,
    "no" to R.drawable.no,
    "nyg" to R.drawable.nyg,
    "nyj" to R.drawable.nyj,
    "phi" to R.drawable.phi,
    "pit" to R.drawable.pit,
    "sea" to R.drawable.sea,
    "sf" to R.drawable.sf,
    "tb" to R.drawable.tb,
    "ten" to R.drawable.ten,
    "wsh" to R.drawable.was,
)

//maps teams to full names
val teamNameMap : Map<String, String> = mapOf(
    "ari" to "Arizona Cardinals",
    "atl" to "Atlanta Falcons",
    "bal" to "Baltimore Ravens",
    "buf" to "Buffalo Bills",
    "car" to "Carolina Panthers",
    "chi" to "Chicago Bears",
    "cin" to "Cincinnati Bengals",
    "cle" to "Cleveland Browns",
    "dal" to "Dallas Cowboys",
    "den" to "Denver Broncos",
    "det" to "Detroit Lions",
    "gb" to "Green Bay Packers",
    "hou" to "Houston Texans",
    "ind" to "Indianapolis Colts",
    "jax" to "Jacksonville Jaguars",
    "kc" to "Kansas City Chiefs",
    "lac" to "Los Angeles Chargers",
    "lar" to "Los Angeles Rams",
    "lv" to "Las Vegas Raiders",
    "mia" to "Miami Dolphins",
    "min" to "Minnesota Vikings",
    "ne" to "New England Patriots",
    "no" to "New Orleans Saints",
    "nyg" to "New York Giants",
    "nyj" to "New York Jets",
    "phi" to "Philadelphia Eagles",
    "pit" to "Pittsburgh Steelers",
    "sea" to "Seattle Seahawks",
    "sf" to "San Francisco 49ers",
    "tb" to "Tampa Bay Buccaneers",
    "ten" to "Tennessee Titans",
    "wsh" to "Washington Commanders",
)

val abbr = (teamImageMap.keys).toList()