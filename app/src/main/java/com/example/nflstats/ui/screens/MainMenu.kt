package com.example.nflstats.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nflstats.R

@Composable
fun MainMenu(onTeamSelectionButton : () -> Unit, onPlayerSelectionButton : () -> Unit, onSavedPlayerSelectionButton: () -> Unit, onChangeStatsButton : () -> Unit, modifier : Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onTeamSelectionButton) {
            Text(stringResource(R.string.view_stats_for_a_team))
        }

        Button(onClick = onPlayerSelectionButton) {
            Text(stringResource(R.string.view_stats_for_a_player))
        }

        Button(onClick = onSavedPlayerSelectionButton) {
            Text(stringResource(R.string.view_stats_for_a_saved_player))
        }

        Button(onClick = onChangeStatsButton) {
            Text(stringResource(R.string.change_statistics_shown))
        }
    }
}

@Composable
@Preview
fun MainMenuPreview() {
    MainMenu({}, {}, {}, {})
}