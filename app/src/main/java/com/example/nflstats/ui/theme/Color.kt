package com.example.nflstats.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.nflstats.data.Teams

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
        ColorTypes.PRIMARY to Color(255,82,0),
        ColorTypes.SECONDARY to Color(76,35,0),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.DET to mapOf(
        ColorTypes.PRIMARY to Color(0, 118, 182),
        ColorTypes.SECONDARY to Color(176,183,188),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.GB to mapOf(
        ColorTypes.PRIMARY to Color(28,45,37),
        ColorTypes.SECONDARY to Color(238,173,30),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.HOU to mapOf(
        ColorTypes.PRIMARY to Color(163,13,45),
        ColorTypes.SECONDARY to Color(0,7,28),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.IND to mapOf(
        ColorTypes.PRIMARY to Color(1,51,105),
        ColorTypes.SECONDARY to Color(155,161,162),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.JAX to mapOf(
        ColorTypes.PRIMARY to Color(159,121,44),
        ColorTypes.SECONDARY to Color(0,101,118),
        ColorTypes.TERTIARY to Color(0,0,0),
    ),

    Teams.KC to mapOf(
        ColorTypes.PRIMARY to Color(227,24,55),
        ColorTypes.SECONDARY to Color(238,173,30),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.LAC to mapOf(
        ColorTypes.PRIMARY to Color(0,128,197),
        ColorTypes.SECONDARY to Color(238,173,30),
        ColorTypes.TERTIARY to Color(0,21,50),
    ),

    Teams.LAR to mapOf(
        ColorTypes.PRIMARY to Color(255,221,0),
        ColorTypes.SECONDARY to Color(0,0,225),
        ColorTypes.TERTIARY to Color(236,238,240),
    ),

    Teams.LV to mapOf(
        ColorTypes.PRIMARY to Color(166,174,176),
        ColorTypes.SECONDARY to Color(0,0,0),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.MIA to mapOf(
        ColorTypes.PRIMARY to Color(0,142,151),
        ColorTypes.SECONDARY to Color(245,130,32),
        ColorTypes.TERTIARY to Color(0,87,120),
    ),

    Teams.MIN to mapOf(
        ColorTypes.PRIMARY to Color(79,38,131),
        ColorTypes.SECONDARY to Color(255,198,47),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.NE to mapOf(
        ColorTypes.PRIMARY to Color(0,21,50),
        ColorTypes.SECONDARY to Color(213,10,10),
        ColorTypes.TERTIARY to Color(176,183,188),
    ),

    Teams.NO to mapOf(
        ColorTypes.PRIMARY to Color(0,0,0),
        ColorTypes.SECONDARY to Color(159,137,88),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.NYG to mapOf(
        ColorTypes.PRIMARY to Color(163,13,45),
        ColorTypes.SECONDARY to Color(1,35,82),
        ColorTypes.TERTIARY to Color(155,161,162),
    ),

    Teams.NYJ to mapOf(
        ColorTypes.PRIMARY to Color(0,63,45),
        ColorTypes.SECONDARY to Color(255,255,255),
        ColorTypes.TERTIARY to Color(0,0,0),
    ),

    Teams.PHI to mapOf(
        ColorTypes.PRIMARY to Color(0,76,84),
        ColorTypes.SECONDARY to Color(166,174,176),
        ColorTypes.TERTIARY to Color(0,0,0),
    ),

    Teams.PIT to mapOf(
        ColorTypes.PRIMARY to Color(0,0,0),
        ColorTypes.SECONDARY to Color(238,173,30),
        ColorTypes.TERTIARY to Color(155,161,162),
    ),

    Teams.SEA to mapOf(
        ColorTypes.PRIMARY to Color(0,21,50),
        ColorTypes.SECONDARY to Color(105,190,40),
        ColorTypes.TERTIARY to Color(155,161,162),
    ),

    Teams.SF to mapOf(
        ColorTypes.PRIMARY to Color(170,0,0),
        ColorTypes.SECONDARY to Color(175,146,93),
        ColorTypes.TERTIARY to Color(255,255,255),
    ),

    Teams.TB to mapOf(
        ColorTypes.PRIMARY to Color(213,10,10),
        ColorTypes.SECONDARY to Color(52,48,43),
        ColorTypes.TERTIARY to Color(255,121,0),
    ),

    Teams.TEN to mapOf(
        ColorTypes.PRIMARY to Color(0,21,50),
        ColorTypes.SECONDARY to Color(68,149,210),
        ColorTypes.TERTIARY to Color(191,192,191),
    ),

    Teams.WSH to mapOf(
        ColorTypes.PRIMARY to Color(238,173,30),
        ColorTypes.SECONDARY to Color(63,16,16),
        ColorTypes.TERTIARY to Color(255,255,255),
    )
)