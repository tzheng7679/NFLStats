package com.example.nflstats.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.nflstats.data.Teams
import java.io.File
import com.example.nflstats.R

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

enum class ColorTypes {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

val teamColorMap: Map<Teams, Map<ColorTypes, Color>> = mapOf(
    Teams.ARI to mapOf(
        ColorTypes.PRIMARY to Color(135,0,39),
        ColorTypes.SECONDARY to Color(0,0,0),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.ATL to mapOf(
        ColorTypes.PRIMARY to Color(163,13,45),
        ColorTypes.SECONDARY to Color(0,0,0),
        ColorTypes.TERTIARY to Color(166,174,176)
    ),

    Teams.BAL to mapOf(
        ColorTypes.PRIMARY to Color(26,25,95),
        ColorTypes.SECONDARY to Color(0,0,0),
        ColorTypes.TERTIARY to Color(187,147,52),
    ),

    Teams.BUF to mapOf(
        ColorTypes.PRIMARY to Color(12,46,130),
        ColorTypes.SECONDARY to Color(213,10,10),
        ColorTypes.TERTIARY to Color(0,39,77),
    ),

    Teams.CAR to mapOf(
        ColorTypes.PRIMARY to Color(0,133,202),
        ColorTypes.SECONDARY to Color(0,0,0),
        ColorTypes.TERTIARY to Color(191,192,191),
    ),

    Teams.CHI to mapOf(
        ColorTypes.PRIMARY to Color(11,22,42),
        ColorTypes.SECONDARY to Color(200,56,3),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.CIN to mapOf(
        ColorTypes.PRIMARY to Color(251,79,20),
        ColorTypes.SECONDARY to Color(0,0,0),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.CLE to mapOf(
        ColorTypes.PRIMARY to Color(49,29,0),
        ColorTypes.SECONDARY to Color(255,60,0),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.DAL to mapOf(
        ColorTypes.PRIMARY to Color(0,34,68),
        ColorTypes.SECONDARY to Color(134,147,151),
        ColorTypes.TERTIARY to Color(0,51,141)
    ),

    Teams.DEN to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.DET to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.GB to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.HOU to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.IND to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.JAX to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.KC to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.LAC to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.LAR to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.LV to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.MIA to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.MIN to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.NE to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.NO to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.NYG to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.NYJ to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.PHI to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.PIT to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.SEA to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.SF to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.TB to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.TEN to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    ),

    Teams.WSH to mapOf(
        ColorTypes.PRIMARY to Color(1,2,3),
        ColorTypes.SECONDARY to Color(1,2,3),
        ColorTypes.TERTIARY to Color(1,2,3),
    )
)