package com.example.nflstats.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Loading menu for when loading stuff
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

        Spacer(Modifier.height(40.dp))

        Text(text = "This may take up to 30 seconds...")
    }
}

/**
 * Menu for when it fails to retrieve stuff
 */
@Composable
@Preview(showBackground = true)
fun FailureMenu() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Sorry, but stats could not retreived at this time\nPlease try again later", textAlign = TextAlign.Center)
    }
}