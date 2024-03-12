@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.nflstats.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nflstats.R
import com.example.nflstats.data.Status
import com.example.nflstats.data.sampleStatOptions
import com.example.nflstats.data.sortedCategories
import com.example.nflstats.model.Stat
import com.example.nflstats.ui.components.ActionButton
import com.example.nflstats.ui.components.LoadingMenu

@Composable
fun StatSettingsMenu(toTeams: () -> Unit, toPlayers: () -> Unit, toGlobalTeams: () -> Unit, toGlobalPlayers: () -> Unit ,modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
        //Route for changing a team
        MainMenuButton(onClick = toTeams, stringId = R.string.change_stats_for_a_single_team, imageId = R.drawable.groups_foreground)

        //Route for changing a player
        MainMenuButton(onClick = toPlayers, stringId = R.string.change_stats_for_a_single_player, imageId = R.drawable.player_icon_foreground)

        MainMenuButton(onClick = toGlobalTeams, stringId = R.string.change_stats_for_global_teams, imageId = R.drawable.groups_foreground, backgroundImageId = R.drawable.global_foreground)

        MainMenuButton(onClick = toGlobalPlayers, stringId = R.string.change_stats_for_global_players, imageId = R.drawable.player_icon_foreground, backgroundImageId = R.drawable.global_foreground)
    }
}

@Composable
fun StatsChangeMenu(options: Map<Stat, Boolean>, onUpdate: (Set<Stat>) -> Unit, status: Status, modifier: Modifier = Modifier) {
    when(status) {
        Status.SUCCESS -> SuccessStatsChangeMenu(options = options, onUpdate = onUpdate, modifier = modifier)
        else -> LoadingMenu()
    }
}

@Composable
fun SuccessStatsChangeMenu(options: Map<Stat, Boolean>, onUpdate: (Set<Stat>) -> Unit, modifier: Modifier = Modifier) {
    //create an updated list and initialize it
    val updatedOptions = remember {
        mutableStateMapOf<Stat, Boolean>()
    }

    /**
     * Settings in alphabetical order
     */
    val orderedOptions = options.keys.sortedBy{
        it.name
    }.sortedBy {
        sortedCategories[it.category] ?: 12
    }

    //initialize [updatedOptions]
    options.forEach {
        updatedOptions[it.key] = it.value
    }

    /**
     * Shared content between the portrait and landscape configurations
     */
    val content = @Composable {
        ButtonSet(
            onUpdate = {
                val optionsOut = updatedOptions.filter { it.value }.keys
                onUpdate(optionsOut)
            },
            onSelectAll = {
                updatedOptions.forEach { updatedOptions[it.key] = true }
            },
            onDeselectAll = {
                updatedOptions.forEach { updatedOptions[it.key] = false }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        LazyColumn {
            orderedOptions.forEach { key ->
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                updatedOptions[key] = !(updatedOptions[key]!!)
                            })
                            .border(
                                width = .5.dp,
                                color = Color.Gray
                            )
                            .padding(5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = updatedOptions[key]!!,
                                onCheckedChange = {
                                    updatedOptions[key] = !updatedOptions[key]!!
                                }
                            )

                            val newLinePosPos = key.name.indexOf(" ", 15)
                            Text(
                                text = when (newLinePosPos) {
                                    -1 -> key.name
                                    else -> key.name.replaceRange(
                                        newLinePosPos..newLinePosPos,
                                        "\n"
                                    )
                                }
                            )
                        }

                        Text(
                            text = key.category.replace(" ", "\n"),
                            textAlign = TextAlign.Right,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }

    if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { content() }
    }

    else {
        Row( verticalAlignment = Alignment.CenterVertically) { content() }
    }
}

@Composable
private fun ButtonSet(onUpdate: () -> Unit, onSelectAll: () -> Unit, onDeselectAll: () -> Unit) {
    Column {
        ActionButton(
            onClick = onUpdate
        ) {
            Text("Update")
        }
        //Select all
        ActionButton(
            onClick = onSelectAll
        ) {
            Text(text = "Select all")
        }
        //Deselect all
        ActionButton(
            onClick = onDeselectAll
        ) {
            Text(text = "Deselect all")
        }
    }
}
@Composable
@Preview(showBackground = true)
fun StatSettingsMenuPreview() {
    StatSettingsMenu({}, {}, {}, {})
}

@Composable
@Preview(showBackground = true)
fun StatsChangeMenuPreview() {
    StatsChangeMenu(sampleStatOptions, {}, Status.SUCCESS)
}

@Composable
@Preview(device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape", showBackground = true)
fun StatsChangeMenuPreviewHorizontal() {
    StatsChangeMenu(sampleStatOptions, {}, Status.SUCCESS)
}