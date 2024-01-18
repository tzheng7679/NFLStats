package com.example.nflstats

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nflstats.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ////////////////////////////
                }
            }
        }
    }
}

@Composable
@Preview
fun LogoMaker(modifier : Modifier = Modifier) {
    val ari = painterResource(R.drawable.ari)

    Box {
        Image(painter = ari, contentDescription = null)
        TeamText(modifier = Modifier.fillMaxSize().padding(8.dp))
    }
}

@Composable
@Preview
fun TeamText(modifier : Modifier = Modifier) {
    Text("Arizona Cardinals", modifier = modifier, fontSize = 50.sp, color = MaterialTheme.colorScheme.primary)
}