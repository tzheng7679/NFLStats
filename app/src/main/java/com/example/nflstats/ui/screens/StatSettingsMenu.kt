@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.nflstats.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StatSettingsMenu(toGlobalTeams: () -> Unit, toTeams: () -> Unit, toGlobalPlayers: () -> Unit, toPlayers: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Route for changing for teams globally
        Button(onClick = toGlobalTeams) {
            Text("Change stats for all teams")
        }

        //Route for changing a team
        Button(onClick = toTeams) {
            Text("Change stats for a single team")
        }

        //Route for changing for players globally
        Button(onClick = toGlobalPlayers) {
            Text("Change stats for all players")
        }

        //Route for changing a player
        Button(onClick = toPlayers) {
            Text("Change stats for a single player")
        }
    }
}
@Composable
@Preview(showBackground = true)
fun StatSettingsMenuPreview() {
    StatSettingsMenu({}, {}, {}, {})
}

