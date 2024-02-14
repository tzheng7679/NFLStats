package com.example.nflstats.ui.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.nflstats.data.Status
import com.example.nflstats.data.Teams
import com.example.nflstats.data.UIState
import com.example.nflstats.data.teamImageMap
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Team
import com.example.nflstats.ui.components.imageCircle
import com.example.nflstats.ui.theme.defaultCardModifier


/**
 * Creates menu for viewing stats for [player], with stats [stats.keys] of values [stats.values.first] with description [stats.values.second]
 */
@Composable
fun StatViewMenu(uiState: UIState, modifier: Modifier = Modifier) = when(uiState.status) {
        Status.SUCCESS -> SuccessMenu(uiState = uiState)
        Status.LOADING -> LoadingMenu()
        Status.FAILURE -> FailureMenu()
    }

@Composable
fun SuccessMenu(uiState: UIState, modifier: Modifier = Modifier) {
    val entity = uiState.currEntity ?: Player("Error", "Player could not be found", -1, teamImageMap[Teams.WSH]!!)
    val stats = uiState.currStats ?: mapOf<String, Pair<Double, String>>()

    Scaffold(
        topBar = @Composable { Header(entity = entity, secondaryInformation = entity.secondaryInformation) },
        modifier = modifier
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            stats.forEach {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    StatCard(name = it.key, value = it.value.first, description = it.value.second)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingMenu() {
    Text(text = "Loading...")
}

@Composable
@Preview(showBackground = true)
fun FailureMenu() {
    Text(text = "Sorry, but stats could not retreived at this time")
}

@Composable
@Preview
fun StatViewMenuPreview() {
    val sampleStats = mapOf<String, Pair<Double, String>>(
        "Completion Percentage" to Pair(67.3, "The percent of passes completed"),
        "Passing Yards" to Pair(4183.0, "The amount of yards passing"),
        "Passing Touchdowns" to Pair(27.0, "The amount of touchdowns the player threw"),
        "Interceptions" to Pair(14.0, "The amount of interceptions thrown"),
        "QBR" to Pair(63.0, "The QBR of a quarterback"),
        "Passer Rating" to Pair(92.6, "Passer rating"),
        "Passing LNG" to Pair(67.0, "Longest passing play"),
        "Sacks" to Pair(27.0, "Amount of sacks taken")
    )
    val x = Team(Teams.WSH)
    StatViewMenu(uiState = UIState(currEntity = x, currStats = sampleStats, status = Status.SUCCESS))
}

@Composable
fun Header(entity: Entity, formattedName: Pair<String, String> = entity.formattedName, secondaryInformation: String, modifier : Modifier = Modifier) {
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
                    maxScore = 16.0
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
                    maxScore = 7.0
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
@Preview(showBackground = true)
fun HeaderPreview() {
    Column {
        Header(Team(Teams.DAL), secondaryInformation = "FILLER")
        Header(Player("Patrick", "Mahomes", 3139477, teamImageMap[Teams.KC]!!), secondaryInformation = "FILLER")
    }
}

@Composable
fun StatCard(name : String, value : Double, description : String) {
    val useWhole = value % 1 == 0.0
    val valueText = (if(useWhole) value.toInt() else value).toString()

    val baseFont = calculateStatCardBaseFontSize(name = name, value = valueText, maxFont = 5.2, maxLengthScore = 30.0, multiplier = 2.7)

    Card(modifier = defaultCardModifier) {
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
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                //INSERT BAR HERE
            }

            Text(
                text = valueText,
                fontSize = (baseFont*2.7).em,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1
            )
        }
    }
}
@Composable
@Preview
fun StatCardPreview() {
    StatCard(name = "Passing Yards", value = 4000.0, description = "Percentage of attempted passes completed")
}

private fun calculateHeaderFontSize(name: String, maxFont: Double, maxScore: Double, scoreCalculator: (String) -> Double) : Double {
    val score = scoreCalculator(name)
    return when {
        scoreCalculator(name) > maxScore ->
            maxFont * (maxScore/score)
        else -> maxFont
    }
}
private fun calculateStatCardBaseFontSize(name : String, value : String, maxFont : Double, maxLengthScore : Double, multiplier : Double) : Double {
    val lengthScore = name.length + (multiplier * 1.1) * value.length
    return when {
        lengthScore < maxLengthScore -> maxFont
        else -> maxFont * (maxLengthScore/lengthScore)
    }
}