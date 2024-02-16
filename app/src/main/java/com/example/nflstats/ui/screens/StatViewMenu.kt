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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import ch.qos.logback.core.util.Loader.getResources
import com.example.nflstats.data.Status
import com.example.nflstats.data.Teams
import com.example.nflstats.data.UIState
import com.example.nflstats.data.sampleStats
import com.example.nflstats.data.teamImageMap
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import com.example.nflstats.ui.components.imageCircle
import com.example.nflstats.ui.theme.defaultCardModifier
import com.example.nflstats.ui.theme.defaultDescriptionModifier
import com.example.nflstats.ui.theme.expandedCardModifier
import kotlin.math.min


/**
 * Feeder function to view stats, load, or failure screen
 */
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun StatViewMenu(uiState: UIState, modifier: Modifier = Modifier) = when(uiState.status) {
        Status.SUCCESS -> SuccessMenu(uiState = uiState, orientation = LocalConfiguration.current.orientation, modifier = modifier)
        Status.LOADING -> LoadingMenu()
        Status.FAILURE -> FailureMenu()
    }

/**
 * Function for displaying header and stats to screen
 */
@Composable
fun SuccessMenu(uiState: UIState, orientation: Int, modifier: Modifier = Modifier) {
    when(orientation) {
        Configuration.ORIENTATION_PORTRAIT -> PortraitSuccessMenu(uiState = uiState)
        else -> LandscapeSuccessMenu(uiState = uiState)
    }
}

/**
 * Loading menu for when loading stats
 */
@Composable
@Preview(showBackground = true)
fun LoadingMenu() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Loading...")

        Spacer(Modifier.height(20.dp))

        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

/**
 * Menu for when it fails to retrieve stats
 */
@Composable
@Preview(showBackground = true)
fun FailureMenu() {
    Text(text = "Sorry, but stats could not retreived at this time")
}

/**
 * Portrait version of viewing stats
 */
@Composable
private fun PortraitSuccessMenu(uiState: UIState, modifier: Modifier = Modifier) {
    val entity = uiState.currEntity ?: Player("Error", "Player could not be found", -1, teamImageMap[Teams.WSH]!!)
    val stats = uiState.currStats ?: emptyList<Stat>()

    Scaffold(
        topBar = @Composable { Header(entity = entity, secondaryInformation = entity.secondaryInformation) },
        modifier = modifier
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
private fun LandscapeSuccessMenu(uiState: UIState, modifier: Modifier = Modifier) {
    val entity = uiState.currEntity ?: Player("Error", "Player could not be found", -1, teamImageMap[Teams.WSH]!!)
    val stats = uiState.currStats ?: emptyList<Stat>()

    val it = 5.dp
    Row(verticalAlignment = Alignment.CenterVertically) {
        Lefter(entity = entity, secondaryInformation = "DSLKFJLSDJFLDSJF")
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
    val width = min(LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp)
    val name = stat.name
    val value = stat.value
    val description = stat.description

    var expanded by remember { mutableStateOf(false) }

    val baseFont = calculateStatCardBaseFontSize(name = name, value = value, maxFont = 7.0, maxLengthScore = 30.0, multiplier = 2.7, screenWidth = width)
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Column {
                Text(
                    text = name,
                    fontSize = baseFont.em,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Text(
                    text = stat.category,
                    fontSize = (baseFont * 0.6).em
                )
            }

            Text(
                text = value,
                fontSize = (baseFont * 2.6).em,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1
            )
        }

        //Description for if card is expanded
        if (expanded) {
            Card(modifier = defaultDescriptionModifier) {
                Text(
                    text = description,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Header for the stat viewing menu
 */
@Composable
fun Header(entity: Entity, formattedName: Pair<String, String> = entity.formattedName, secondaryInformation: String, modifier : Modifier = Modifier) {
    val width = min(LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
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
fun Lefter(entity: Entity, formattedName: Pair<String, String> = entity.formattedName, secondaryInformation: String, modifier: Modifier = Modifier) {
    val width = min(LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp)

    Column(
        modifier = modifier.width((width*3/4).dp)
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
                    maxScore = 16.0,
                    screenWidth = width
                ) { it.length.toDouble() }

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
                    maxFont = 13.0,
                    maxScore = 7.0,
                    screenWidth = width
                ) { it.length.toDouble() }

                //Team nickname
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedName.second,
                    textAlign = TextAlign.Center,
                    fontSize = secondFontSize.em, //size of font for team names
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
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
    }
}
@RequiresApi(Build.VERSION_CODES.R)
@Composable
@Preview
fun VerticalStatViewMenuPreview() {
    val x = Team(Teams.WSH)
    SuccessMenu(uiState = UIState(currEntity = x, currStats = sampleStats, status = Status.SUCCESS), orientation = android.view.Surface.ROTATION_0)
}

@Composable
@Preview(device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape", showBackground = true)
@RequiresApi(Build.VERSION_CODES.R)
fun HorizontalStatViewMenuPreview() {
    val x = Team(Teams.WSH)
    StatViewMenu(uiState = UIState(currEntity = x, currStats = sampleStats, status = Status.SUCCESS))
}

@Composable
@Preview(showBackground = true)
fun HeaderPreview() {
    Column {
        Header(Team(Teams.DAL), secondaryInformation = "FILLER")
        Header(Player("Patrick", "Mahomes", 3139477, teamImageMap[Teams.KC]!!), secondaryInformation = "FILLER")
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
    val lengthScore = name.length + (multiplier * 1.1) * value.length
    return when {
        lengthScore < maxLengthScore -> maxFont
        else -> maxFont * (maxLengthScore/lengthScore)
    } * screenWidth / 460
}