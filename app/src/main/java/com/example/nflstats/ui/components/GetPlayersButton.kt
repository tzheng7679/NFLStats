package com.example.nflstats.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GetPlayersButton(onGetPlayers: () -> Unit) {
    Spacer(Modifier.height(5.dp))
    Button(
        onClick = onGetPlayers,
        modifier = Modifier.width(100.dp)
    ) {
        Text(
            text = "Get Players",
            fontSize = 9.sp
        )
    }
}