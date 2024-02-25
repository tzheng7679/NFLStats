package com.example.nflstats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nflstats.ui.theme.defaultTeamImageModifier
import com.example.nflstats.ui.theme.defaultPlayerImageModifier

@Composable
fun imageCircle(id : Int, isPlayer : Boolean = false) {
    val imageModifier = when(isPlayer) {
        true -> defaultPlayerImageModifier.border(BorderStroke(4.dp, MaterialTheme.colorScheme.secondary), CircleShape)
        false -> defaultTeamImageModifier.border(BorderStroke(4.dp, MaterialTheme.colorScheme.primary), CircleShape)
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