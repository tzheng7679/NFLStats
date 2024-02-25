@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.nflstats.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.nflstats.data.Status
import com.example.nflstats.data.Teams
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Team
import com.example.nflstats.ui.components.FailureMenu
import com.example.nflstats.ui.components.LoadingMenu
import com.example.nflstats.ui.components.imageCircle
import com.example.nflstats.ui.theme.defaultCardModifier
import com.example.nflstats.ui.theme.defaultTeamImageModifier

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

