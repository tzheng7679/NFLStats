@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.nflstats.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nflstats.data.sampleStatOptions
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat

@Composable
fun StatSettingsMenu(toTeams: () -> Unit, toPlayers: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Route for changing a team
        Button(onClick = toTeams) {
            Text("Change stats for a single team")
        }

        //Route for changing a player
        Button(onClick = toPlayers) {
            Text("Change stats for a single player")
        }
    }
}

@Composable
fun StatsChangeMenu(options: Map<Stat, Boolean>, onUpdate: (Set<Stat>) -> Unit, modifier: Modifier = Modifier) {
    //create an updated list and initialize it
    val updatedOptions = remember {
        mutableStateMapOf<Stat, Boolean>()
    }
    options.forEach {
        updatedOptions[it.key] = it.value
    }
    Column(verticalArrangement = Arrangement.Bottom , horizontalAlignment = Alignment.CenterHorizontally) {
        //Column of options
        LazyColumn {
            updatedOptions.forEach { entry ->
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                updatedOptions[entry.key] = !entry.value
                            })
                            .border(
                                width = .5.dp,
                                color = Color.Gray
                            )
                            .padding(5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = entry.value,
                                onCheckedChange = { updatedOptions[entry.key] = !entry.value }
                            )
                            Text(
                                text = entry.key.name
                            )
                        }

                        Text(
                            text = entry.key.category
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                val optionsOut = updatedOptions.filter { it.value }.keys
                onUpdate(optionsOut)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StatSettingsMenuPreview() {
    StatSettingsMenu({}, {})
}

@Composable
@Preview(showBackground = true)
fun StatsChangeMenuPreview() {
    StatsChangeMenu(sampleStatOptions, {})
}
