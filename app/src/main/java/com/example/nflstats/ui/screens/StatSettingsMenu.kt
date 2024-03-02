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
import androidx.compose.material3.Button
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
import com.example.nflstats.data.sampleStatOptions
import com.example.nflstats.data.sortedCategories
import com.example.nflstats.model.Stat
import com.example.nflstats.ui.components.ActionButton

@Composable
fun StatSettingsMenu(toTeams: () -> Unit, toPlayers: () -> Unit, toGlobalTeams: () -> Unit, toGlobalPlayers: () -> Unit ,modifier: Modifier = Modifier) {
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

        Button(onClick = toGlobalTeams) {
            Text("Change stats for global teams")
        }

        Button(onClick = toGlobalPlayers) {
            Text("Change stats for global players")
        }
    }
}

@Composable
fun StatsChangeMenu(options: Map<Stat, Boolean>, onUpdate: (Set<Stat>) -> Unit, modifier: Modifier = Modifier) {
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

    options.forEach {
        updatedOptions[it.key] = it.value
    }

    if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Column of options
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
//                                text = entry.key.name,
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
    }

    else {
        Row( verticalAlignment = Alignment.CenterVertically) {
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
//                                text = entry.key.name,
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
    StatsChangeMenu(sampleStatOptions, {})
}

@Composable
@Preview(device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape", showBackground = true)
fun StatsChangeMenuPreviewHorizontal() {
    StatsChangeMenu(sampleStatOptions, {})
}