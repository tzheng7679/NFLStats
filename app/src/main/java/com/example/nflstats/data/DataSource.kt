package com.example.nflstats.data

import com.example.nflstats.R
import com.example.nflstats.model.Stat

enum class Teams {
    ARI,
    ATL,
    BAL,
    BUF,
    CAR,
    CHI,
    CIN,
    CLE,
    DAL,
    DEN,
    DET,
    GB,
    HOU,
    IND,
    JAX,
    KC,
    LAC,
    LAR,
    LV,
    MIA,
    MIN,
    NE,
    NO,
    NYG,
    NYJ,
    PHI,
    PIT,
    SEA,
    SF,
    TB,
    TEN,
    WSH
}

enum class Status {
    SUCCESS,
    LOADING,
    FAILURE
}

//maps teams to image resources
val teamImageMap : Map<Teams, Int> = mapOf(
    Teams.ARI to R.drawable.ari,
    Teams.ATL to R.drawable.atl,
    Teams.BAL to R.drawable.bal,
    Teams.BUF to R.drawable.buf,
    Teams.CAR to R.drawable.car,
    Teams.CHI to R.drawable.chi,
    Teams.CIN to R.drawable.cin,
    Teams.CLE to R.drawable.cle,
    Teams.DAL to R.drawable.dal,
    Teams.DEN to R.drawable.den,
    Teams.DET to R.drawable.det,
    Teams.GB to R.drawable.gb,
    Teams.HOU to R.drawable.hou,
    Teams.IND to R.drawable.ind,
    Teams.JAX to R.drawable.jax,
    Teams.KC to R.drawable.kc,
    Teams.LAC to R.drawable.lac,
    Teams.LAR to R.drawable.lar,
    Teams.LV to R.drawable.lv,
    Teams.MIA to R.drawable.mia,
    Teams.MIN to R.drawable.min,
    Teams.NE to R.drawable.ne,
    Teams.NO to R.drawable.no,
    Teams.NYG to R.drawable.nyg,
    Teams.NYJ to R.drawable.nyj,
    Teams.PHI to R.drawable.phi,
    Teams.PIT to R.drawable.pit,
    Teams.SEA to R.drawable.sea,
    Teams.SF to R.drawable.sf,
    Teams.TB to R.drawable.tb,
    Teams.TEN to R.drawable.ten,
    Teams.WSH to R.drawable.was,
)

//maps teams to full names
val teamNameMap : Map<Teams, String> = mapOf(
    Teams.ARI to "Arizona Cardinals",
    Teams.ATL to "Atlanta Falcons",
    Teams.BAL to "Baltimore Ravens",
    Teams.BUF to "Buffalo Bills",
    Teams.CAR to "Carolina Panthers",
    Teams.CHI to "Chicago Bears",
    Teams.CIN to "Cincinnati Bengals",
    Teams.CLE to "Cleveland Browns",
    Teams.DAL to "Dallas Cowboys",
    Teams.DEN to "Denver Broncos",
    Teams.DET to "Detroit Lions",
    Teams.GB to "Green Bay Packers",
    Teams.HOU to "Houston Texans",
    Teams.IND to "Indianapolis Colts",
    Teams.JAX to "Jacksonville Jaguars",
    Teams.KC to "Kansas City Chiefs",
    Teams.LAC to "Los Angeles Chargers",
    Teams.LAR to "Los Angeles Rams",
    Teams.LV to "Las Vegas Raiders",
    Teams.MIA to "Miami Dolphins",
    Teams.MIN to "Minnesota Vikings",
    Teams.NE to "New England Patriots",
    Teams.NO to "New Orleans Saints",
    Teams.NYG to "New York Giants",
    Teams.NYJ to "New York Jets",
    Teams.PHI to "Philadelphia Eagles",
    Teams.PIT to "Pittsburgh Steelers",
    Teams.SEA to "Seattle Seahawks",
    Teams.SF to "San Francisco 49ers",
    Teams.TB to "Tampa Bay Buccaneers",
    Teams.TEN to "Tennessee Titans",
    Teams.WSH to "Washington Commanders",
)

val abbrToID: Map<Teams, Int> = mapOf(
    Teams.ARI to 22,
    Teams.ATL to 1,
    Teams.BAL to 33,
    Teams.BUF to 2,
    Teams.CAR to 29,
    Teams.CHI to 3,
    Teams.CIN to 4,
    Teams.CLE to 5,
    Teams.DAL to 6,
    Teams.DEN to 7,
    Teams.DET to 8,
    Teams.GB to 9,
    Teams.HOU to 34,
    Teams.IND to 11,
    Teams.JAX to 30,
    Teams.KC to 12,
    Teams.LAC to 13,
    Teams.LAR to 24,
    Teams.LV to 14,
    Teams.MIA to 15,
    Teams.MIN to 16,
    Teams.NE to 17,
    Teams.NO to 18,
    Teams.NYG to 19,
    Teams.NYJ to 20,
    Teams.PHI to 21,
    Teams.PIT to 23,
    Teams.SEA to 26,
    Teams.SF to 25,
    Teams.TB to 27,
    Teams.TEN to 10,
    Teams.WSH to 28
)

val sampleStats = listOf(
    Stat("Completion Percentage", "67.3", "The percent of passes completed", "Passing"),
    Stat("Passing Yards" , "4183", "The amount of yards passing", "Passing"),
    Stat("Passing Touchdowns", "27", "The amount of touchdowns the player threw", "Passing"),
    Stat("Interceptions", "14", "The amount of interceptions thrown", "Passing"),
    Stat("QBR", "63", "The QBR of a quarterback", "Passing"),
    Stat("Passer Rating", "92", "Passer rating", "Passing"),
    Stat("Passing LNG", "67", "Longest passing play", "Rushing"),
    Stat("Sacks", "27", "Amount of sacks taken", "Rushing")
)