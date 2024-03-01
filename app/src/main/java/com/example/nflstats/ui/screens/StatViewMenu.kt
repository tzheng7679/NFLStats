@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nflstats.ui.screens

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.nflstats.data.Status
import com.example.nflstats.data.Teams
import com.example.nflstats.ui.UIState
import com.example.nflstats.data.idToAbbr
import com.example.nflstats.data.sampleStats
import com.example.nflstats.data.teamImageMap
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import com.example.nflstats.ui.components.FailureMenu
import com.example.nflstats.ui.components.GetPlayersButton
import com.example.nflstats.ui.components.LoadingMenu
import com.example.nflstats.ui.components.OnAddEntityButton
import com.example.nflstats.ui.components.imageCircle
import com.example.nflstats.ui.theme.StatViewTheme
import com.example.nflstats.ui.theme.defaultCardModifier
import com.example.nflstats.ui.theme.defaultDescriptionModifier
import com.example.nflstats.ui.theme.expandedCardModifier
import com.example.nflstats.ui.theme.teamColorMap
import kotlin.math.min


/**
 * Feeder function to view stats, load, or failure screen
 */
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun StatViewMenu(uiState: UIState, onGetPlayers: () -> Unit, onAddEntity: (Entity) -> Unit, viewPlayer: Boolean = false, modifier: Modifier = Modifier) =
    StatViewTheme(teamColorMap[idToAbbr[uiState.currTeam!!.id]]!!) {
        when(
            when(viewPlayer) {true -> uiState.currPlayerStatus false -> uiState.currTeamStatus}
        ) {
            Status.SUCCESS -> SuccessMenu(uiState = uiState, orientation = LocalConfiguration.current.orientation, onGetPlayers = onGetPlayers, onAddEntity = onAddEntity, viewPlayer = viewPlayer, modifier = modifier)
            Status.LOADING -> LoadingMenu()
            else -> FailureMenu()
        }
    }

/**
 * Function for displaying header and stats to screen
 */
@Composable
fun SuccessMenu(uiState: UIState, orientation: Int, onGetPlayers: () -> Unit, onAddEntity: (Entity) -> Unit, viewPlayer: Boolean = false, modifier: Modifier = Modifier) {
    when(orientation) {
        Configuration.ORIENTATION_PORTRAIT -> PortraitSuccessMenu(uiState = uiState, onAddEntity = onAddEntity, onGetPlayers = onGetPlayers, viewPlayer = viewPlayer)
        else -> LandscapeSuccessMenu(uiState = uiState, onAddEntity = onAddEntity, onGetPlayers = onGetPlayers, viewPlayer = viewPlayer)
    }
}


/**
 * Portrait version of viewing stats
 */
@Composable
private fun PortraitSuccessMenu(uiState: UIState, onAddEntity: (Entity) -> Unit, onGetPlayers: () -> Unit, viewPlayer: Boolean, modifier: Modifier = Modifier) {
    val entity = when(viewPlayer) {
        false -> uiState.currTeam ?: Player("Error", "Player could not be found", -1, teamImageMap[Teams.WSH]!!)
        true -> uiState.currPlayer ?: Player("Error", "Player could not be found", -1, teamImageMap[Teams.WSH]!!)
    }
    val stats = when(viewPlayer) {
        false -> uiState.currTeamStats ?: emptyList<Stat>()
        true -> uiState.currPlayerStats ?: emptyList<Stat>()
    }

    Scaffold(
        topBar = @Composable { Header(entity = entity, onAddEntity = onAddEntity, secondaryInformation = entity.secondaryInformation, onGetPlayers = onGetPlayers) }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy((5).dp)
        ) {
            stats.forEach {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    StatCard(it)
                }
            }
        }
    }
}

/**
 * Landscape version of viewing stats
 */
@Composable
private fun LandscapeSuccessMenu(uiState: UIState, onGetPlayers: () -> Unit, onAddEntity: (Entity) -> Unit, viewPlayer: Boolean, modifier: Modifier = Modifier) {
    val entity = when(viewPlayer) {
        false -> uiState.currTeam ?: Player("Error", "Player could not be found", -1, teamImageMap[Teams.WSH]!!)
        true -> uiState.currPlayer ?: Player("Error", "Player could not be found", -1, teamImageMap[Teams.WSH]!!)
    }
    val stats = when(viewPlayer) {
        false -> uiState.currTeamStats ?: emptyList<Stat>()
        true -> uiState.currPlayerStats ?: emptyList<Stat>()
    }

    val it = 5.dp
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
        Lefter(entity = entity, secondaryInformation = "DSLKFJLSDJFLDSJF", onGetPlayers = onGetPlayers, onAddEntity = onAddEntity)
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy((5).dp)
        ) {
            stats.forEach {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    StatCard(it)
                }
            }
        }
    }
}

/**
 * Card of a statistic, with name, value, category, and pop-out description
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatCard(stat: Stat) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = when(expanded) { true -> expandedCardModifier else -> defaultCardModifier }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            //stat name
            Text(
                text = stat.name,
                fontSize = 6.em,
                fontWeight = FontWeight.Bold
            )
            Row {
                //stat category
                Text(
                    text = stat.category,
                    fontSize = 4.em,
                )
                Column(
                    modifier = Modifier.wrapContentSize(Alignment.Center).fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    //Stat value
                    Text(
                        text = stat.value,
                        textAlign = TextAlign.Right,
                        fontSize = 12.em,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        //Description for if card is expanded
        if (expanded) {
            Card(modifier = defaultDescriptionModifier.wrapContentSize(Alignment.Center)) {
                Text(
                    text = stat.description,
                    textAlign = TextAlign.Center,
                    fontSize = 4.em
                )
            }
        }
    }
}

/**
 * Header for the stat viewing menu
 */
@Composable
fun Header(entity: Entity, onAddEntity: (Entity) -> Unit, formattedName: Pair<String, String> = entity.formattedName, secondaryInformation: String, onGetPlayers: () -> Unit, modifier : Modifier = Modifier) {
    val width = min(LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            imageCircle(id = entity.imageID, isPlayer = entity is Player)

            if(entity is Team) {
                GetPlayersButton(onGetPlayers = onGetPlayers)
            }

            OnAddEntityButton(entity = entity, onAddEntity = onAddEntity)
        }
        Spacer(modifier = Modifier.width(15.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy((-5).dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy((-10).dp)
            ) {
                val firstFontSize = calculateHeaderFontSize(
                    formattedName.first,
                    maxFont = 9.0,
                    maxScore = 16.0,
                    screenWidth = width
                ) { it.length.toDouble() }

                //First name OR Team city
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedName.first,
                    textAlign = TextAlign.Left,
                    fontSize = firstFontSize.em, //size of font for team names
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                )

                //THANKS DAN SNYDER, for making me write something to change the font size
                val secondFontSize : Double = calculateHeaderFontSize(
                    name = formattedName.second,
                    maxFont = 13.0,
                    maxScore = 7.0,
                    screenWidth = width
                ) { it.length.toDouble() }

                //Team nickname
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedName.second,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Left,
                    fontSize = secondFontSize.em, //size of font for team names
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }

            //Secondary Information
            Text(
                text = secondaryInformation,
                textAlign = TextAlign.Left,
                fontSize = 4.em, //size of font for team names
                fontWeight = FontWeight.ExtraLight,
                fontStyle = FontStyle.Italic, maxLines = 1
            )
        }
    }
}

@Composable
fun Lefter(entity: Entity, onAddEntity: (Entity) -> Unit, formattedName: Pair<String, String> = entity.formattedName, secondaryInformation: String, onGetPlayers: () -> Unit, modifier: Modifier = Modifier) {
    val width = min(LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp)

    Column(
        modifier = modifier
            .width((width * 3 / 4).dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageCircle(id = entity.imageID, isPlayer = entity is Player)

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy((-5).dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy((-10).dp)
            ) {
                val firstFontSize = calculateHeaderFontSize(
                    formattedName.first,
                    maxFont = 9.0,
                    maxScore = 256.0,
                    screenWidth = width
                ) { it.length.toDouble() * it.length.toDouble() }

                //First name OR Team city
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedName.first,
                    textAlign = TextAlign.Center,
                    fontSize = firstFontSize.em, //size of font for team names
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                )

                //THANKS DAN SNYDER, for making me write something to change the font size
                val secondFontSize : Double = calculateHeaderFontSize(
                    name = formattedName.second,
                    maxFont = 19.0,
                    maxScore = 49.0,
                    screenWidth = width
                ) { it.length.toDouble() * it.length.toDouble() }

                //Team nickname
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedName.second,
                    textAlign = TextAlign.Center,
                    fontSize = secondFontSize.em, //size of font for team names
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            //Secondary Information
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = secondaryInformation,
                textAlign = TextAlign.Center,
                fontSize = 4.em, //size of font for team names
                fontWeight = FontWeight.ExtraLight,
                fontStyle = FontStyle.Italic, maxLines = 1
            )
        }

        if(entity is Team) {
            GetPlayersButton(onGetPlayers = onGetPlayers)
        }

        OnAddEntityButton(entity, onAddEntity)
    }
}
@RequiresApi(Build.VERSION_CODES.R)
@Composable
@Preview(showBackground = true)
fun VerticalStatViewMenuPreview() {
    val x = Team(Teams.WSH)
    StatViewMenu(uiState = UIState(currTeam = x, currTeamStats = sampleStats, currTeamStatus = Status.SUCCESS), onAddEntity = {}, onGetPlayers = {})
}

@Composable
@Preview(device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape", showBackground = true)
@RequiresApi(Build.VERSION_CODES.R)
fun HorizontalStatViewMenuPreview() {
    val x = Team(Teams.WSH)
    StatViewMenu(uiState = UIState(currTeam = x, currTeamStats = sampleStats, currTeamStatus = Status.SUCCESS), onAddEntity = {}, onGetPlayers = {})
}

@Composable
@Preview(showBackground = true)
fun HeaderPreview() {
    Column {
        Header(Team(Teams.DAL), secondaryInformation = "FILLER", onGetPlayers = {}, onAddEntity = {})
        Header(Player("Patrick", "Mahomes", 3139477, teamImageMap[Teams.KC]!!), secondaryInformation = "FILLER", onGetPlayers = {}, onAddEntity = {})
    }
}

@Composable
@Preview
fun StatCardPreview() {
    StatCard(Stat("Passing Yards", value = "4000", description = "Percentage of attempted passes completed", category = "Offense"))
}

private fun calculateHeaderFontSize(name: String, maxFont: Double, maxScore: Double, screenWidth: Int, scoreCalculator: (String) -> Double) : Double {
    val score = scoreCalculator(name)
    return when {
        scoreCalculator(name) > maxScore ->
            maxFont * (maxScore/score)
        else -> maxFont
    } * screenWidth / 400
}
private fun calculateStatCardBaseFontSize(name : String, value : String, maxFont : Double, maxLengthScore : Double, multiplier : Double, screenWidth: Int) : Double {
    val lengthScore = name.length + (multiplier * 1.3) * value.length
    return when {
        lengthScore < maxLengthScore -> maxFont
        else -> maxFont * (maxLengthScore/lengthScore)
    } * screenWidth / 460
}