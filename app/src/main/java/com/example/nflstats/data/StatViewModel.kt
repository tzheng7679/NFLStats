package com.example.nflstats.data

import androidx.lifecycle.ViewModel
import com.example.nflstats.model.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UIState(null, null))
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun setEntity(entity : Entity) {
        _uiState.update { it.copy(currEntity = entity) }
    }

    fun setStats(values : Map<String, Pair<Double, String>>) {
        _uiState.update { it.copy(currStats = values) }
    }
}