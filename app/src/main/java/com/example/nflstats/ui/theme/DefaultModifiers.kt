package com.example.nflstats.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Modifiers for preview image
val defaultTeamImageModifier = Modifier
    .size(100.dp)
    .clip(CircleShape)
    .border(BorderStroke(4.dp, Color.Gray), CircleShape)

val defaultPlayerImageModifier = Modifier
    .size(100.dp)
    .clip(CircleShape)
    .border(BorderStroke(4.dp, Color.DarkGray), CircleShape)

private val cardShape = RoundedCornerShape(40.dp)
val defaultCardModifier = Modifier
    .border(3.dp, Color.Black, shape = cardShape)
    .clip(shape = cardShape)
    .background(color = Color.White, shape = cardShape)
    .fillMaxWidth()