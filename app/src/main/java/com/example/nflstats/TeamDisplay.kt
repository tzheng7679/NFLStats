package com.example.nflstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//maps city abb. to team logos
val teamImageMap = mapOf(
    "ari" to R.drawable.ari,
    "atl" to R.drawable.atl,
    "bal" to R.drawable.bal,
    "buf" to R.drawable.buf,
    "car" to R.drawable.car,
    "chi" to R.drawable.chi,
    "cin" to R.drawable.cin,
    "cle" to R.drawable.cle,
    "dal" to R.drawable.dal,
    "den" to R.drawable.den,
    "det" to R.drawable.det,
    "gb" to R.drawable.gb,
    "hou" to R.drawable.hou,
    "ind" to R.drawable.ind,
    "jax" to R.drawable.jax,
    "kc" to R.drawable.kc,
    "lac" to R.drawable.lac,
    "lar" to R.drawable.lar,
    "lv" to R.drawable.lv,
    "mia" to R.drawable.mia,
    "min" to R.drawable.min,
    "ne" to R.drawable.ne,
    "no" to R.drawable.no,
    "nyg" to R.drawable.nyg,
    "nyj" to R.drawable.nyj,
    "phi" to R.drawable.phi,
    "pit" to R.drawable.pit,
    "sea" to R.drawable.sea,
    "sf" to R.drawable.sf,
    "tb" to R.drawable.tb,
    "ten" to R.drawable.ten,
    "was" to R.drawable.was,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                TeamDisplayApp()
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
                team = teamImageMap.values.random()

            }) {
                Text("switch team")
            }

        }

    }
}