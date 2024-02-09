package com.example.nflstats.model
import androidx.lifecycle.ViewModel
import com.example.nflstats.R
import com.example.nflstats.data.TeamUIState
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.p
import it.skrape.selects.html5.tbody
import it.skrape.selects.html5.td
import it.skrape.selects.text
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Represents a Team located in "city" (must be in lowercase short abbreviation)
 */
class TeamStatViewModel(val team : Team) : ViewModel() {
    val _uiState = MutableStateFlow(TeamUIState(team))
    val uiState = _uiState.asStateFlow()


}