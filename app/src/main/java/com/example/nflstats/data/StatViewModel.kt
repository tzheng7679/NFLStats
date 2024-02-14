package com.example.nflstats.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Team
import com.example.nflstats.network.ESPNApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

/**
 * ViewModel for the app; based mainly around doing every by itself after the Entity is set
 */
class StatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UIState(null, null, Status.LOADING))
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    /**
     * Updates currEntity to entity, and sets the statistical values to those of the entity
     */
    fun setEntity(entity : Entity) {
        _uiState.update { it.copy(currEntity = entity,) }
        fetchAndSetStatValues()
    }

    private fun fetchAndSetStatValues() {
        val currEntity = _uiState.value.currEntity ?: Team(Teams.WSH)
        setStatus(status = Status.LOADING)

//        viewModelScope.launch {
//            try {
//                val result = ESPNApi.servicer.fetchStatValues(
//                    season = Entity.getSeason(),
//                    id = currEntity.id,
//                    type = currEntity.getType()
//                )
//                setStats(values = result)
//                setStatus(status = Status.SUCCESS)
//            } catch (e: Exception) {
//                setStats(values = emptyMap<String, Pair<Double, String>>())
//                setStatus(status = Status.FAILURE)
//            }
//        }

        runBlocking {
            delay(500)
        }

        setStats(values = mapOf<String, Pair<Double, String>>(
            "Completion Percentage" to Pair(67.3, "The percent of passes completed"),
            "Passing Yards" to Pair(4183.0, "The amount of yards passing"),
            "Passing Touchdowns" to Pair(27.0, "The amount of touchdowns the player threw"),
            "Interceptions" to Pair(14.0, "The amount of interceptions thrown"),
            "QBR" to Pair(63.0, "The QBR of a quarterback"),
            "Passer Rating" to Pair(92.6, "Passer rating"),
            "Passing LNG" to Pair(67.0, "Longest passing play"),
            "Sacks" to Pair(27.0, "Amount of sacks taken")
        ))

        setStatus(status = Status.SUCCESS)
    }

    private fun setStats(values : Map<String, Pair<Double, String>>) {
        _uiState.update { it.copy(currStats = values) }
    }

    private fun setStatus(status : Status) {
        _uiState.update { it.copy(status = status) }
    }
}