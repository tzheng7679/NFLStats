@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nflstats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.runBlocking

/**
 * Displays menu of options of type [E] (should only be either [Player] or [Team]).
 */
@Composable
fun <E> SelectionMenu(status: Status, entities : List<E>, onCardClick : (E) -> Unit, onClear: (() -> Unit)? = null, imageModifier : Modifier, cardModifier : Modifier = defaultCardModifier, modifier: Modifier = Modifier) {
    when(status) {
        Status.SUCCESS -> SuccessSelectionMenu<E>(entities = entities, onCardClick = onCardClick, imageModifier = imageModifier, onClear = onClear, cardModifier = cardModifier)
        Status.LOADING -> LoadingMenu()
        Status.FAILURE -> FailureMenu()
    }
}

@Composable
fun <E> SuccessSelectionMenu(entities: List<E>, onCardClick: (E) -> Unit, onClear: (() -> Unit)?, imageModifier: Modifier, cardModifier: Modifier) {
    LazyColumn {
        entities.forEach {
            item {
                Row(modifier = Modifier.padding(3.dp)) {
                    EntityCard(entity = it, imageModifier = imageModifier, cardMod = cardModifier, onCardClick = onCardClick)
                }
                Spacer(Modifier.height(8.dp))
            }
        }
        if(onClear != null) item { FloatingActionButton(onClick = onClear, modifier = Modifier.fillMaxWidth().padding(15.dp)) { Text(text = "Clear all entities from storage") } }
    }
}

@Composable
@Preview
fun SelectionPreview() {
    SelectionMenu(status = Status.SUCCESS, Teams.entries.map { Team(it) }, {}, imageModifier = defaultTeamImageModifier, cardModifier = defaultCardModifier)
}
@Composable
fun <E> EntityCard(entity : E, onCardClick : (E) -> Unit, imageModifier : Modifier, cardMod : Modifier) {
    //Check to make sure it is a [Team] or [Player] (and for type safety, as properties of entity are accessed)
    if(entity is Entity) Card(
        modifier = cardMod.clickable{ onCardClick(entity) },
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(20.dp))

            imageCircle(id = entity.imageID, imageModifier = defaultTeamImageModifier
                .border(
                    BorderStroke(4.dp, Color(128,128,128)), CircleShape
                )
            )

            val formattedName: Pair<String, String> = entity.formattedName
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedName.first,
                    textAlign = TextAlign.Center,
                    fontSize = 6.em, //size of font for team names
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedName.second,
                    textAlign = TextAlign.Center,
                    fontSize = 9.4.em, //size of font for team names
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
@Preview
fun EntityCardPreview() {
    EntityCard(entity = Team(Teams.BUF), onCardClick = {}, imageModifier = defaultTeamImageModifier, cardMod = defaultCardModifier)
}