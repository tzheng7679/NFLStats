package com.example.nflstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nflstats.model.Maps
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
                val bengals = Team(city = "cin", name = "bengals")
                bengals.addLocalStat("3rd Down %")
                Text(text = bengals.fetchStatValues().toString())
            }
        }
    }

    /*
    @Composable
    @Preview(showBackground = true)
    fun LogoMaker(modifier : Modifier = Modifier, city : String = "ari") {
        val ari = painterResource(teamImageMap[city]!!)

        Row(modifier = modifier) {
            Image(painter = ari, contentDescription = null)
            TeamText(city = city, modifier = Modifier
                .padding(8.dp)
                .fillMaxSize())
        }
    }

    @Composable
    fun TeamText(city : String, modifier : Modifier = Modifier) {
        Text(text = getString(getResources().getIdentifier("city", "drawable", null)),
            modifier = modifier,
            fontSize = 50.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
    */

    @Composable
    @Preview(showBackground = true)
    fun TeamDisplayApp() {
        TeamWithButton(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }

    @Composable
    fun TeamWithButton(modifier : Modifier = Modifier) {
        var team by remember {mutableStateOf(R.drawable.ari)}
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(painter = painterResource(team), "", modifier = Modifier.size(150.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                team = Maps.teamImageMap.values.random()

            }) {
                Text("switch team")
            }
        }

    }

    @Composable
    // @Preview(showBackground = true)
    fun Select() {
        var selectedOption by remember { mutableStateOf(Maps.abbr[0]) }
        LazyColumn(modifier = Modifier.selectableGroup()) {
            Maps.abbr.forEach { a : String ->
                item {
                    Row() {
                        RadioButton(
                            selected = (selectedOption == a),
                            onClick = { selectedOption = a }
                        )

                        Text(text = Maps.teamNameMap[a]!!)
                    }
                }
            }

        }
    }
}