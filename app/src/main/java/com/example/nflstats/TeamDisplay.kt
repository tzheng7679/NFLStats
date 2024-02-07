package com.example.nflstats

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.nflstats.model.Maps
import com.example.nflstats.model.Player
import com.example.nflstats.model.Team

//maps city abb. to team logos

val Maps = Maps()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                //Selection()
                val bengals = Team("cin", "bengals")
                bengals.fetchStatValues(LocalContext.current)
            }
        }
    }

    @Composable
    @Preview
    fun testy() {
    }

    @Composable
    //@Preview
    fun Selection() {
        LazyColumn {
            val imageMod = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(BorderStroke(4.dp, Color.Gray), CircleShape)

            val roundedCorner = 40.dp
            val cardMod = Modifier
                .border(3.dp, Color.Black, RoundedCornerShape(roundedCorner))
                .clip(RoundedCornerShape(roundedCorner))

            Maps.teamNameMap.keys.forEach {
                item {
                    Row(modifier = Modifier.padding(3.dp)) {
                        TeamCard(team = it, imageMod = imageMod, cardMod = cardMod)
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
    @Composable
    //@Preview(showBackground = true)
    fun TeamCard(team : String = "ari", cardMod : Modifier = Modifier
        .border(3.dp, Color.Black, RoundedCornerShape(40.dp))
        .clip(RoundedCornerShape(40.dp)), imageMod : Modifier = Modifier
        .size(100.dp)
        .clip(CircleShape)
        .border(BorderStroke(4.dp, Color.Gray), CircleShape)) {
        Card(modifier = cardMod) {
            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(20.dp))
                Image(
                    modifier = imageMod,
                    painter = painterResource(id = Maps.teamImageMap[team]!!),
                    contentDescription = ""
                )

                val x = Maps.teamNameMap[team]!!
                val z = x.lastIndexOf(" ")
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 8.em, //size of font for team names
                    text = x.substring(0, z) + "\n" + x.substring(z + 1),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    @Composable
    fun Requester() {
        //
    }
}