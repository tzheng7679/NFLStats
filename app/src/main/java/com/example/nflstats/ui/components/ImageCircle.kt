package com.example.nflstats.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.nflstats.ui.theme.defaultTeamImageModifier
import com.example.nflstats.ui.theme.defaultPlayerImageModifier

@Composable
fun imageCircle(id : Int, isPlayer : Boolean = false) {
    val imageModifier = when(isPlayer) {
        true -> defaultPlayerImageModifier
        else -> defaultTeamImageModifier
    }

    Image(
        modifier = imageModifier,
        painter = painterResource(id = id),
        contentDescription = ""
    )
}

@Composable
fun imageCircle(id : Int, imageModifier: Modifier) {
    Image(
        modifier = imageModifier,
        painter = painterResource(id = id),
        contentDescription = ""
    )
}