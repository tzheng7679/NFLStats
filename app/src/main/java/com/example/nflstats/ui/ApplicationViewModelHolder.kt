package com.example.nflstats.ui

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nflstats.data.database.AppDataContainer

object ApplicationViewModelHolder {
    val Factory = viewModelFactory {
        initializer {
            StatViewModel()
        }

        initializer {
            val appDataContainer = AppDataContainer(this)
            StatSettingsViewModel(teamsRepo = appDataContainer.teamsRepo, playersRepo = appDataContainer.playersRepo)
        }
    }
}

fun CreationExtras.inventoryApplication(): A =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)