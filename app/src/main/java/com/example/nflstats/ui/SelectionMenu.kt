@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nflstats.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.nflstats.data.Teams
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Team
import com.example.nflstats.ui.theme.defaultCardModifier
import com.example.nflstats.ui.theme.defaultImageModifier


@Composable
fun SelectionMenu(entities : List<Entity>, onCardClick : (Entity) -> Unit, imageModifier : Modifier, cardModifier : Modifier) {
    LazyColumn {
        val roundedCorner = 40.dp
        val cardMod = Modifier
            .border(3.dp, Color.Black, RoundedCornerShape(roundedCorner))
            .clip(RoundedCornerShape(roundedCorner))

        entities.forEach {
            item {
                Row(modifier = Modifier.padding(3.dp)) {
                    EntityCard(entity = it, imageModifier = imageModifier, cardMod = cardModifier, onCardClick = onCardClick)
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
@Preview
fun SelectionPreview() {
    SelectionMenu(Teams.entries.map { Team(it) }, {}, defaultImageModifier, defaultCardModifier)
}
@Composable
fun EntityCard(entity : Entity, onCardClick : (Entity) -> Unit, imageModifier : Modifier, cardMod : Modifier) {
    Card(
        modifier = cardMod.clickable{ onCardClick(entity) },
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(20.dp))
            Image(
                modifier = imageModifier,
                painter = painterResource(id = entity.imageID),
                contentDescription = ""
            )

            val formattedName: Pair<String, String> = entity.getFormattedName()
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
    EntityCard(entity = Team(Teams.BUF), onCardClick = {}, imageModifier = defaultImageModifier, cardMod = defaultCardModifier)
}