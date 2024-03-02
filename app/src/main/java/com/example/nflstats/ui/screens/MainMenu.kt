package com.example.nflstats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.nflstats.R

@Composable
fun MainMenu(
    onTeamSelectionButton: () -> Unit,
    onPlayerSelectionButton: () -> Unit,
    onSavedPlayerSelectionButton: () -> Unit,
    onChangeStatsButton: () -> Unit
) {
    Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
        MainMenuButton(onClick = onTeamSelectionButton, imageId = R.drawable.groups_foreground, R.string.view_stats_for_a_team)

        MainMenuButton(onClick = onPlayerSelectionButton, imageId = R.drawable.player_icon_foreground, stringId = R.string.view_stats_for_a_player)

        MainMenuButton(onClick = onSavedPlayerSelectionButton, imageId = R.drawable.player_icon_foreground, stringId = R.string.view_stats_for_a_saved_player)

        MainMenuButton(onClick = onChangeStatsButton, imageId = R.drawable.settings_foreground, stringId = R.string.change_statistics_shown)
    }
}

@Composable
fun MainMenuButton(onClick: () -> Unit, imageId: Int, stringId: Int) {
    val width = (LocalConfiguration.current.screenWidthDp * .9).dp
    val iconSize = (LocalConfiguration.current.screenHeightDp * .1).dp
    Button(onClick = onClick, modifier = Modifier.width(width)) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(imageId),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.width(30.dp))
            AutosizedText(stringResource(stringId), baseSize = 4.5.em, textAlign = TextAlign.Center, maxLines = 3, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun MainMenuButton(onClick: () -> Unit, imageId: Int, stringId: Int, backgroundImageId: Int) {
    val width = (LocalConfiguration.current.screenWidthDp * .9).dp
    val iconSize = (LocalConfiguration.current.screenHeightDp * .1).dp

    Button(onClick = onClick, modifier = Modifier.width(width)) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(imageId),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(iconSize).paint(painterResource(backgroundImageId), alpha = .25F)
            )
            Spacer(modifier = Modifier.width(30.dp))
            AutosizedText(stringResource(stringId), baseSize = 4.5.em, textAlign = TextAlign.Center, maxLines = 3, fontWeight = FontWeight.ExtraBold)
        }
    }
}
@Composable
@Preview
fun MainMenuPreview() {
    MainMenu({}, {}, {}) {}
}