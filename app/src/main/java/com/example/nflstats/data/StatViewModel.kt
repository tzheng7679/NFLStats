package com.example.nflstats.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Stat
import com.example.nflstats.model.Team
import com.example.nflstats.model.json.EntityStats
import com.example.nflstats.network.ESPNApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
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
        _uiState.update { it.copy(currEntity = entity) }
        fetchAndSetStatValues()
    }

    private fun fetchAndSetStatValues() {
        val currEntity = _uiState.value.currEntity ?: Team(Teams.WSH)
        setStatus(status = Status.LOADING)
        val stats = mutableListOf<Stat>()

        //request and add stats to [stats]
        viewModelScope.launch {
            try {
                //Get JSON
                val fetched: String = ESPNApi.servicer.fetchStatValues(
                    season = Entity.getSeason(),
                    id = currEntity.id,
                    type = currEntity.getType()
                )

                //parse JSON with kotlin deserializer, with unknown keys excluded because we don't need them
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                val result = json.decodeFromString<EntityStats>(fetched)

                //Duplicate checker -- adds a ([CATEGORY]) at the end if there's a another stat that shares the same name
                val duplicateChecker = mutableMapOf<String, Stat>()

                result.splits.categories.forEach {category ->
                    val cat = category.name
                    category.stats.forEach {stat ->
                        val statAsObject = Stat(name = stat.displayName, value = stat.displayValue, description = stat.description, category = cat)
                        stats.add(statAsObject)
                    }
                }



                setStatus(status = Status.SUCCESS)
            } catch (e: Exception) {
                setStatus(status = Status.FAILURE)
                Log.d("HelpMe", e.stackTraceToString())
            }
        }

        setStats(stats)
    }

    private fun setStats(values : List<Stat>) {
        _uiState.update { it.copy(currStats = values) }
    }

    private fun setStatus(status : Status) {
        _uiState.update { it.copy(status = status) }
    }
}