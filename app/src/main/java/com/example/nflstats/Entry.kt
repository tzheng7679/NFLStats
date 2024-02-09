package com.example.nflstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.nflstats.data.UIState
import com.example.nflstats.ui.theme.NFLStatsTheme
import com.example.nflstats.ui.SelectionMenu
import kotlinx.coroutines.flow.MutableStateFlow

class Entry : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFLStatsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState = MutableStateFlow(UIState(currEntity = null))
                    NFLStatsScreen(uiState = uiState)
                }
            }
        }
    }
}